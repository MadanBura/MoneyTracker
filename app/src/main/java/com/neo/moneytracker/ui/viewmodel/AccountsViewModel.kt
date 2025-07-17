package com.neo.moneytracker.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.moneytracker.data.localDb.entities.AddAccountEntity
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.domain.model.AddAccount
import com.neo.moneytracker.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val repo: AccountRepository
): ViewModel() {
    fun addAccount(account: AddAccount){
        viewModelScope.launch{
            repo.addAccount(account)
        }
    }

    init {
        getAccounts()
    }
    private val _accounts = MutableStateFlow<List<AddAccountEntity>>(emptyList())
    val accounts: StateFlow<List<AddAccountEntity>> = _accounts

    private fun getAccounts() {
        viewModelScope.launch {
            repo.getAccount().collect { list ->
                _accounts.value = list
            }
        }
    }

    fun delAccount(account: AddAccountEntity){
        viewModelScope.launch{
            repo.deleteAccount(id = account.id)
        }
    }

}