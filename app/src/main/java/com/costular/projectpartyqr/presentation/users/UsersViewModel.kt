package com.costular.projectpartyqr.presentation.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.costular.common.util.Event
import com.costular.common.util.extensions.call
import com.costular.projectpartyqr.data.UserRepository
import com.costular.projectpartyqr.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class UsersViewModel(
    val usersRepository: UserRepository
) : ViewModel() {

    val users: MutableLiveData<List<User>> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    val openQrAction: MutableLiveData<Event<String>> = MutableLiveData()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            showLoading.value = true
            val loadedUsers = withContext(Dispatchers.IO) { usersRepository.getUsers() }
            users.value = loadedUsers
            showLoading.value = false
        }
    }

    fun togglePay(user: User) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { usersRepository.updatePayed(user.id, !user.payed) }
            load()
        }
    }

    fun toggleConfirm(user: User) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { usersRepository.updateConfirmation(user.id, !user.confirmed) }
            load()
        }
    }

    fun generateQr(user: User) {
        viewModelScope.launch {
            val generatedQr = withContext(Dispatchers.Default) { UUID.randomUUID().toString() }
            withContext(Dispatchers.IO) { usersRepository.updateQr(user.id, generatedQr) }
            load()
        }
    }

    fun openQr(user: User) {
        openQrAction.value = Event(user.id)
    }

    fun addUser(name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { usersRepository.addUser(name) }
            load()
        }
    }

}