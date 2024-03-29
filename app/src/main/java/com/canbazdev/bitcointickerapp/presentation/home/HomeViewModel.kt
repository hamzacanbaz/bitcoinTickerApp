package com.canbazdev.bitcointickerapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.domain.model.CoinMarkets
import com.canbazdev.bitcointickerapp.domain.usecase.LogoutUseCase
import com.canbazdev.bitcointickerapp.domain.repository.WorkerProvider
import com.canbazdev.bitcointickerapp.domain.usecase.WorkerUseCase
import com.canbazdev.bitcointickerapp.domain.usecase.coins.GetCoinMarketUseCase
import com.canbazdev.bitcointickerapp.domain.usecase.coins.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoinMarketsUseCase: GetCoinMarketUseCase,
    private val logoutUseCase: LogoutUseCase,
    getUserUseCase: GetUserUseCase,
    workerUseCase: WorkerUseCase,
    workerProvider: WorkerProvider

) : ViewModel() {

    private val _coinMarketsFlow = MutableStateFlow<Resource<List<CoinMarkets>>>(Resource.Loading)
    val coinMarketsFlow = _coinMarketsFlow.asStateFlow()
    val refreshCoinWork = workerUseCase.invoke()
    val currentUser = getUserUseCase.invoke()


    init {
        workerProvider.createWork()
        getCoinMarkets()
    }


    fun getCoinMarkets() = viewModelScope.launch {
        getCoinMarketsUseCase.invoke().collect { result ->
            _coinMarketsFlow.emit(result)
        }
    }

    fun signOut() = logoutUseCase.invoke()

}