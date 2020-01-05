package com.costular.projectpartyqr.data.model

import org.threeten.bp.LocalDateTime

data class User(
    val id: String,
    val name: String,
    val confirmed: Boolean,
    val joinedParty: Boolean,
    val qr: String,
    val payed: Boolean,
    val joinedPartyAt: LocalDateTime?
)