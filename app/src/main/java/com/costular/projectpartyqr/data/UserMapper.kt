package com.costular.projectpartyqr.data

import com.costular.projectpartyqr.data.model.User
import com.google.firebase.firestore.DocumentSnapshot
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class UserMapper {

    fun map(document: DocumentSnapshot): User =
        User(
            document.id,
            document.getString("name")!!,
            document.getBoolean("confirmed")!!,
            document.getBoolean("joined_party")!!,
            document.getString("qr")!!,
            document.getBoolean("payed")!!,
            longToDateTime(document.getTimestamp("joined_party_at")?.toDate()?.time)
        )

    private fun longToDateTime(long: Long?): LocalDateTime? = if (long != null) {
        LocalDateTime.ofInstant(Instant.ofEpochMilli(long), ZoneId.systemDefault())
    } else {
        null
    }

}