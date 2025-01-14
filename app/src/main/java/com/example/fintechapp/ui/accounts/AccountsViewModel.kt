package com.example.fintechapp.ui.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechapp.data.model.Transaction
import com.example.fintechapp.data.repository.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AccountsViewModel(
    private val repository: AccountRepository
) : ViewModel() {

    private val _currentUserId = MutableStateFlow<String?>(null)

    val accounts = _currentUserId.filterNotNull().flatMapLatest { userId ->
        repository.getAccountsForUser(userId)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun setCurrentUser(userId: String) {
        _currentUserId.value = userId
    }

    fun performTransfer(sourceId: String, destinationId: String, amount: Double) {

    }


}

class AccountViewModel(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _transferResult = MutableStateFlow<Result<Transaction>?>(null)
    val transferResult: StateFlow<Result<Transaction>?> = _transferResult

    fun getAccountsForUser(userId: String) = accountRepository.getAccountsForUser(userId)

    fun performTransfer(sourceId: String, destinationId: String, amount: Double) {
        viewModelScope.launch {
            _transferResult.value =
                accountRepository.performTransfer(sourceId, destinationId, amount)
        }
    }
}

