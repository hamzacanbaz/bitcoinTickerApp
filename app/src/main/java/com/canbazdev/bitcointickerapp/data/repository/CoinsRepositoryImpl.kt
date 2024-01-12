package com.canbazdev.bitcointickerapp.data.repository

import androidx.room.withTransaction
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.data.mappers.toCoinDetail
import com.canbazdev.bitcointickerapp.data.mappers.toCoinListEntity
import com.canbazdev.bitcointickerapp.data.mappers.toCoinList
import com.canbazdev.bitcointickerapp.data.mappers.toCoinMarketsEntity
import com.canbazdev.bitcointickerapp.data.mappers.toCoinMarkets
import com.canbazdev.bitcointickerapp.data.source.local.room.CoinsRoomDB
import com.canbazdev.bitcointickerapp.domain.model.CoinDetail
import com.canbazdev.bitcointickerapp.domain.model.CoinList
import com.canbazdev.bitcointickerapp.domain.model.CoinMarkets
import com.canbazdev.bitcointickerapp.domain.repository.CoinsRepository
import com.canbazdev.bitcointickerapp.domain.source.LocalDataSource
import com.canbazdev.bitcointickerapp.domain.source.RemoteDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Named
import kotlin.time.Duration

class CoinsRepositoryImpl constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val coinsRoomDB: CoinsRoomDB,
    @Named("Default") private val coroutineContextDefault: CoroutineDispatcher
) : CoinsRepository {

    private var job: Job? = null


    override fun coinsMarkets(): Flow<Resource<List<CoinMarkets>>> = flow {
        emit(Resource.Loading)
        val cache = localDataSource.getCoinMarkets()
        if (cache.isNotEmpty()) {
            emit(Resource.Success(cache.toCoinMarkets()))
        }
        val response = try {
            remoteDataSource.getCoinMarkets()
        } catch (throwable: Throwable) {
            emit(Resource.Error(throwable))
            null
        }
        response?.let { data ->
            coinsRoomDB.withTransaction {
                localDataSource.deleteCoinMarkets()
                localDataSource.insertCoinMarketsList(data.toCoinMarketsEntity())
            }
            emit(Resource.Success(localDataSource.getCoinMarkets().toCoinMarkets()))
        }
    }

    override fun coinList(): Flow<Resource<List<CoinList>>> = flow {
        emit(Resource.Loading)
        val cache = localDataSource.getCoinList()
        if (cache.isNotEmpty()) {
            emit(Resource.Success(cache.toCoinList()))
        }
        val response = try {
            remoteDataSource.getCoinList()
        } catch (throwable: Throwable) {
            Resource.Error(throwable)
            null
        }
        response?.let { data ->
            localDataSource.deleteCoinList()
            localDataSource.insertCoinList(data.toCoinListEntity())
        }
        emit(Resource.Success(localDataSource.getCoinList().toCoinList()))
    }

    override fun coinById(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading)
            emit(Resource.Success(remoteDataSource.getCoinById(coinId).toCoinDetail()))
        } catch (e: Exception) {
            emit(Resource.Error(e.cause ?: Throwable()))

        }
    }


    override fun searchCoin(searchQuery: String): Flow<Resource<List<CoinList>>> = flow {
        emit(Resource.Loading)
        emit(Resource.Success(localDataSource.searchCoin(searchQuery).toCoinList()))
    }.catch {
        emit(Resource.Error(it))
    }


    override fun currentPriceById(period: Duration, coinId: String): Flow<Resource<Double>> =
        channelFlow {
            job?.cancel()
            job = CoroutineScope(coroutineContextDefault).launch {
                while (true) {
                    send(Resource.Loading)
                    val data = remoteDataSource.getCoinById(coinId).toCoinDetail()
                    data.currentPrice?.let {
                        send(Resource.Success(it))
                    }
                    delay(period)
                }
            }
            awaitClose {
                channel.close()
                job?.cancel()
            }
        }.catch {
            emit(Resource.Error(it))
        }
}


