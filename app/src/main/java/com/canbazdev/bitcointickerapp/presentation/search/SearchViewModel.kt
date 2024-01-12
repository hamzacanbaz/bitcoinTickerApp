package com.canbazdev.bitcointicker.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.domain.model.CoinList
import com.canbazdev.bitcointickerapp.domain.usecase.coins.GetCoinListUseCase
import com.canbazdev.bitcointickerapp.domain.usecase.coins.SearchCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    getCoinListUseCase: GetCoinListUseCase,
    private val searchCoinUseCase: SearchCoinUseCase,
) : ViewModel() {

    private val _coinListFlow = MutableStateFlow<Resource<List<CoinList>>>(Resource.Loading)
    val coinListFlow = _coinListFlow.asStateFlow()

//    val coinList = getCoinListUseCase.invoke()

    init {
        viewModelScope.launch {
            getCoinListUseCase.invoke().collect{
                _coinListFlow.emit(it)
            }
        }
    }

    fun searchCoin(searchQuery: String) = viewModelScope.launch {
        searchCoinUseCase.invoke(searchQuery).collect {
            _coinListFlow.emit(it)
        }
    }
}