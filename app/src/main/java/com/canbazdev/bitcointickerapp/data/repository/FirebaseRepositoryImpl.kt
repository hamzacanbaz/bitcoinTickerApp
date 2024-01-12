package com.canbazdev.bitcointickerapp.data.repository

import com.canbazdev.bitcointickerapp.common.Constants.COINS_COLLECTION
import com.canbazdev.bitcointickerapp.common.Constants.FAVORITES_COLLECTION
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.data.mappers.toFavouriteCoin
import com.canbazdev.bitcointickerapp.domain.model.CoinDetail
import com.canbazdev.bitcointickerapp.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth, private val firebaseFirestore: FirebaseFirestore
) : FirebaseRepository {

    override fun loginWithEmailAndPassword(
        email: String, password: String
    ): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading)
        emit(Resource.Success(firebaseAuth.signInWithEmailAndPassword(email, password).await()))
    }.catch { emit(Resource.Error(it)) }

    override fun registerWithEmailAndPassword(
        email: String, password: String
    ): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading)
        emit(Resource.Success(firebaseAuth.createUserWithEmailAndPassword(email, password).await()))
    }.catch {
        emit(Resource.Error(it))
    }

    override fun signOut() = firebaseAuth.signOut()


    override fun getFirebaseUserUid(): Flow<String> = flow {
        firebaseAuth.currentUser?.uid?.let {
            emit(it)
        }
    }

    override fun isCurrentUserExist(): Flow<Boolean> = flow {
        emit(firebaseAuth.currentUser != null)
    }

    override fun getCurrentUser(): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading)
        firebaseAuth.currentUser?.let {
            emit(Resource.Success(it))
        }
    }.catch {
        emit(Resource.Error(it))
    }

    override fun addToFavourites(coinDetail: CoinDetail): Flow<Resource<Task<Void>>> = flow {

        emit(Resource.Loading)
        getFirebaseUserUid().collect {
            val favRef = firebaseFirestore.collection(FAVORITES_COLLECTION).document(it)
                .collection(COINS_COLLECTION).document(coinDetail.toFavouriteCoin().name.orEmpty())
                .set(coinDetail.toFavouriteCoin())
            favRef.await()
            emit(Resource.Success(favRef))
        }

    }.catch {
        emit(Resource.Error(it))
    }

    override fun getFavourites(): Flow<Resource<List<CoinDetail>>> = flow {

        emit(Resource.Loading)
        getFirebaseUserUid().collect {
            val snapshot = firebaseFirestore.collection(FAVORITES_COLLECTION).document(it)
                .collection(COINS_COLLECTION).get().await()

            val data = snapshot.toObjects(CoinDetail::class.java)
            emit(Resource.Success(data))
        }
    }.catch {
        emit(Resource.Error(it))
    }

    override fun getFavouriteCoinById(coinId: String): Flow<Resource<Boolean>> = flow {

        emit(Resource.Loading)
        getFirebaseUserUid().collect {
            val snapshot = firebaseFirestore.collection(FAVORITES_COLLECTION).document(it)
                .collection(COINS_COLLECTION).get().await()

            val data = snapshot.toObjects(CoinDetail::class.java)
            val isCoinExists = data.find { coinDetail ->
                coinDetail.coinId == coinId
            }
            if (isCoinExists == null) {
                emit(Resource.Success(false))
            } else {
                emit(Resource.Success(true))
            }

        }
    }.catch {
        emit(Resource.Error(it))
    }

    override fun deleteFromFavourites(coinDetail: CoinDetail): Flow<Resource<Task<Void>>> = flow {

        emit(Resource.Loading)
        getFirebaseUserUid().collect {

            val favRef = firebaseFirestore.collection(FAVORITES_COLLECTION).document(it)
                .collection(COINS_COLLECTION).document(coinDetail.name.orEmpty()).delete()
            favRef.await()
            emit(Resource.Success(favRef))
        }
    }.catch {
        emit(Resource.Error(it))
    }


}