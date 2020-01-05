package com.costular.projectpartyqr.presentation.validate

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.costular.common.util.Event
import com.costular.projectpartyqr.data.UserRepository
import com.costular.projectpartyqr.data.model.Invalid
import com.costular.projectpartyqr.data.model.Valid
import com.costular.projectpartyqr.data.model.ValidationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.Duration
import org.threeten.bp.Instant

class ValidateViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val resultEvent: MutableLiveData<Event<ValidationResult>> = MutableLiveData()

    private var lastValidation = Instant.now()

    fun validate(qr: String) {
        val seconds = Duration.between(lastValidation, Instant.now()).seconds
        if (seconds <= 3) {
            return
        }

        lastValidation = Instant.now()
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) { userRepository.getUserByQr(qr) }
            val result = if (user != null) {
               if (user.joinedParty.not()) {
                    Valid(user.name)
                } else {
                    Invalid(
                        true,
                        true,
                        user.name,
                        user.joinedPartyAt
                    )
                }

            } else {
                Invalid(
                    false,
                    false,
                    null,
                    null
                )
            }

            if (result is Valid && user != null) {
                withContext(Dispatchers.IO) {
                    userRepository.updateJoined(user.id)
                }
            }

            resultEvent.value = Event(result)
        }


    }

}