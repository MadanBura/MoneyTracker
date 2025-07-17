package com.neo.moneytracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.moneytracker.domain.model.AddAccount
import com.neo.moneytracker.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
}