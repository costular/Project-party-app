package com.costular.projectpartyqr.presentation.generate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.costular.common.util.Event
import com.costular.projectpartyqr.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewQrViewModel(
    private val usersRepository: UserRepository
) : ViewModel() {

    val qrValue: MutableLiveData<String> = MutableLiveData()
    val shareAction: MutableLiveData<Event<String>> = MutableLiveData()

    fun loadQr(userId: String) {
        viewModelScope.launch {
            val value = withContext(Dispatchers.IO) { usersRepository.getUser(userId) }
            qrValue.value = value.qr
        }
    }

    fun shareQr() {
        shareAction.value = Event(qrValue.value!!)
    }

}