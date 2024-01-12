package com.canbazdev.bitcointicker.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.domain.model.CoinDetail
import com.canbazdev.bitcointickerapp.domain.usecase.favorite.DeleteFromFavoritesUseCase
import com.canbazdev.bitcointickerapp.domain.usecase.favorite.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavoritesUseCase,
    private val deleteFromFavouritesUseCase: DeleteFromFavoritesUseCase,
//    private val stringResourceProvider: StringResourceProvider
) : ViewModel() {

//    private val _deleteFromFavouritesFlow = MutableSharedFlow<UIScreen>()
//    val deleteFromFavouritesFlow = _deleteFromFavouritesFlow.asSharedFlow()
//
    private val _favouritesFlow = MutableStateFlow<Resource<List<CoinDetail>>>(Resource.Loading)
    val favouritesFlow = _favouritesFlow.asStateFlow()

    init {
        getFavourites()
    }



    private fun getFavourites() = viewModelScope.launch {
        getFavouritesUseCase.invoke().collect { result ->
            _favouritesFlow.emit(result)
        }
    }

    fun deleteFromFavourites(coinDetail: CoinDetail) = viewModelScope.launch {
        deleteFromFavouritesUseCase.invoke(coinDetail).collect { result ->
            when (result) {
                Resource.Loading -> {}
                is Resource.Success -> {
//                    _deleteFromFavouritesFlow.emit(
//                        UIScreen.SnackBar(stringResourceProvider.getString(R.string.delete_from_favourite))
//                    )
                    getFavourites()
                }
                is Resource.Error -> {}
            }
        }
    }


}
