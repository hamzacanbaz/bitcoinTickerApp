package com.canbazdev.bitcointickerapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.domain.model.CoinDetail
import com.canbazdev.bitcointickerapp.domain.usecase.coins.GetCoinByIDUseCase
import com.canbazdev.bitcointickerapp.domain.usecase.coins.GetCurrentPriceByIdUseCase
import com.canbazdev.bitcointickerapp.domain.usecase.favorite.AddToFavouritesUseCase
import com.canbazdev.bitcointickerapp.domain.usecase.favorite.DeleteFromFavoritesUseCase
import com.canbazdev.bitcointickerapp.domain.usecase.favorite.GetFavoriteCoinByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCoinByIdUseCase: GetCoinByIDUseCase,
    private val currentPriceByIdUseCase: GetCurrentPriceByIdUseCase,
    private val addToFavouritesUseCase: AddToFavouritesUseCase,
    private val deleteFromFavouritesUseCase: DeleteFromFavoritesUseCase,
    private val getFavoriteCoinByIdUseCase: GetFavoriteCoinByIdUseCase,
    ) : ViewModel() {

    val refreshTimeIntervals = listOf(Pair(10000,"10 sec"), Pair(30000,"30 sec"),Pair(60000,"60 sec"),Pair(90000,"10 sec"))

    private var coinDetailUI: CoinDetail? = null

    private val _coinDetailFlow = MutableStateFlow<Resource<CoinDetail>>(Resource.Loading)
    val coinDetailFlow = _coinDetailFlow.asStateFlow()

    private val _currentPriceFlow = MutableStateFlow(0.0)
    val currentPriceFlow = _currentPriceFlow.asStateFlow()

    private val _isFavFlow = MutableStateFlow(false)
    val isFavFlow = _isFavFlow.asStateFlow()


    init {
        savedStateHandle.get<String>("coinId")?.let {
            getCoinById(it)
            getCoinIsFav(it)
        }

    }


    private fun getCoinById(coinId: String) = viewModelScope.launch {
        getCoinByIdUseCase.invoke(coinId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    coinDetailUI = result.data
                    _coinDetailFlow.emit(Resource.Success(result.data))
                }

                is Resource.Loading -> _coinDetailFlow.emit(Resource.Loading)
                is Resource.Error -> {
                    println(result.throwable.localizedMessage)
                    _coinDetailFlow.emit(Resource.Error(result.throwable))
                }
            }
        }
    }

    private fun getCoinIsFav(coinId: String) = viewModelScope.launch {
        getFavoriteCoinByIdUseCase.invoke(coinId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _isFavFlow.emit(result.data)
                }

                is Resource.Loading -> _coinDetailFlow.emit(Resource.Loading)
                is Resource.Error -> {
                    println(result.throwable.localizedMessage)
                    _coinDetailFlow.emit(Resource.Error(result.throwable))
                }
            }
        }
    }


    fun refreshCurrentCoinPeriodically(period: Duration) = viewModelScope.launch {
        savedStateHandle.get<String>("coinId")?.let {
            currentPriceByIdUseCase.invoke(period, it).collect { result ->
                when (result) {
                    Resource.Loading -> {}
                    is Resource.Success -> _currentPriceFlow.emit(result.data)
                    is Resource.Error -> {}
                }
            }
        }
    }

    fun addToFavourites() = viewModelScope.launch {
        coinDetailUI?.let {
            addToFavouritesUseCase.invoke(it).collect { result ->
                when (result) {
                    Resource.Loading -> {}
                    is Resource.Success -> {
                        println("added to fav")
                    }

                    is Resource.Error -> {

                        println("error fav")
                    }
                }
            }
        }
    }


}

