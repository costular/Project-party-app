package com.costular.projectpartyqr.data

import com.costular.projectpartyqr.data.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kiwimob.firestore.coroutines.addAwait
import com.kiwimob.firestore.coroutines.await
import com.kiwimob.firestore.coroutines.updateAwait

interface UserRepository {

    suspend fun addUser(name: String)
    suspend fun getUser(userId: String): User
    suspend fun getUsers(): List<User>
    suspend fun updateConfirmation(id: String, confirmed: Boolean)
    suspend fun updatePayed(id: String, payed: Boolean)
    suspend fun updateQr(id: String, qr: String)
    suspend fun getUserByQr(qr: String): User?
    suspend fun updateJoined(userId: String)

}

class UserRepositoryDefault(
    val fireStore: FirebaseFirestore,
    val userMapper: UserMapper
) : UserRepository {

    override suspend fun addUser(name: String) {
        fireStore.collection(COLLECTION).addAwait(
            mapOf(
                "name" to name,
                "confirmed" to false,
                "payed" to false,
                "qr" to "",
                "joined_party_at" to null,
                "joined_party" to false
            )
        )
    }

    override suspend fun getUsers(): List<User> {
            return fireStore
                .collection(COLLECTION)
                .orderBy("name")
                .await { userMapper.map(it) }
    }

    override suspend fun getUser(userId: String): User {
        return fireStore.collection(COLLECTION).document(userId).await { userMapper.map(it) }
    }

    override suspend fun updateConfirmation(id: String, confirmed: Boolean) {
        fireStore.collection(COLLECTION).document(id)
            .updateAwait(mapOf(
                "confirmed" to confirmed
            ))
    }

    override suspend fun updatePayed(id: String, payed: Boolean) {
        fireStore.collection(COLLECTION).document(id)
            .updateAwait(mapOf(
                "payed" to payed
            ))
    }

    override suspend fun updateQr(id: String, qr: String) {
        fireStore.collection(COLLECTION).document(id)
            .updateAwait(
                mapOf(
                    "qr" to qr
                )
            )
    }

    override suspend fun getUserByQr(qr: String): User? {
        return fireStore.collection(COLLECTION)
            .whereEqualTo("qr", qr)
            .await { userMapper.map(it) }
            .firstOrNull()
    }

    override suspend fun updateJoined(userId: String) {
        fireStore.collection(COLLECTION).document(userId)
            .updateAwait(
                mapOf(
                    "joined_party" to true,
                    "joined_party_at" to FieldValue.serverTimestamp()
                )
            )
    }

    private companion object {
        private const val COLLECTION = "users"
    }

}

