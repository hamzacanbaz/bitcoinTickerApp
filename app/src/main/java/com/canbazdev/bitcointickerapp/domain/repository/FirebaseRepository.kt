package com.canbazdev.bitcointickerapp.domain.repository

import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.domain.model.CoinDetail
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {


    fun loginWithEmailAndPassword(email: String, password: String): Flow<Resource<AuthResult>>

    fun registerWithEmailAndPassword(email: String, password: String): Flow<Resource<AuthResult>>

    fun signOut()

    fun getFirebaseUserUid(): Flow<String>

    fun isCurrentUserExist(): Flow<Boolean>

    fun getCurrentUser(): Flow<Resource<FirebaseUser>>

    fun addToFavourites(coinDetail: CoinDetail): Flow<Resource<Task<Void>>>

    fun getFavourites(): Flow<Resource<List<CoinDetail>>>

    fun deleteFromFavourites(coin: CoinDetail): Flow<Resource<Task<Void>>>


}