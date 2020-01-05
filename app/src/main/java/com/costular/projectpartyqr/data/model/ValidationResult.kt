package com.costular.projectpartyqr.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDateTime


sealed class ValidationResult : Parcelable

@Parcelize
data class Valid(
    val name: String
) : ValidationResult(), Parcelable

@Parcelize
data class Invalid(
    val exists: Boolean,
    val hasJoined: Boolean,
    val name: String?,
    val joinedAt: LocalDateTime?
): ValidationResult(), Parcelable
