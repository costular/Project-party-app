package com.costular.projectpartyqr.data.model

import androidx.annotation.DrawableRes

data class Stat(
    @DrawableRes val icon: Int,
    val key: String,
    val value: String
)