package com.neo.moneytracker.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neo.moneytracker.data.localDb.entities.AddAccountEntity
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.domain.model.AddAccount
import com.neo.moneytracker.domain.repository.AccountRepository
import com.neo.moneytracker.domain.usecase.AccountDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val useCase: AccountDataUseCase
): ViewModel() {

    init {
        getAccounts()
    }

    private val _assets = MutableStateFlow(0L)
    val assets: StateFlow<Long> = _assets

    private val _liabilities = MutableStateFlow(0L)
    val liabilities: StateFlow<Long> = _liabilities

    val netWorth: StateFlow<Long> = MutableStateFlow(0L)

    private val _accounts = MutableStateFlow<List<AddAccountEntity>>(emptyList())
    val accounts: StateFlow<List<AddAccountEntity>> = _accounts

    private val _singleAccount = MutableStateFlow<AddAccountEntity?>(null)
    val singleAccount: StateFlow<AddAccountEntity?> = _singleAccount

    fun addAccount(account: AddAccount){
        viewModelScope.launch{
            useCase.addAccount(account)
        }
    }

    fun reorderAccounts(fromIndex: Int, toIndex: Int) {
        val currentList = _accounts.value.toMutableList()
        val movedItem = currentList.removeAt(fromIndex)
        currentList.add(toIndex, movedItem)
        _accounts.value = currentList
    }


    private fun getAccounts() {
        viewModelScope.launch {
            useCase.getAccount().collect { list ->
                _accounts.value = list
                calculateAssetsAndLiabilities(list)
            }
        }
    }

    fun delAccount(account: AddAccountEntity){
        viewModelScope.launch{
            useCase.deleteAccount(id = account.id)
        }
    }


    fun updateAccount(account: AddAccount){
        viewModelScope.launch {
            useCase.update(account)
        }
    }


    private fun calculateAssetsAndLiabilities(list: List<AddAccountEntity>) {
        var assets = 0L
        var liabilities = 0L
        for (i in list) {
            if (i.liabilities) {
                liabilities += i.amount
            } else {
                assets += i.amount
            }
        }
        _assets.value = assets
        _liabilities.value = liabilities
        (netWorth as MutableStateFlow).value = assets - liabilities
    }

    fun getAccountById(id: Int){
        viewModelScope.launch {
            _singleAccount.value = useCase.getAccountById(id)
        }
    }



}