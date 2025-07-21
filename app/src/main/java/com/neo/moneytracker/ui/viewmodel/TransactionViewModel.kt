package com.neo.moneytracker.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.data.mapper.toDataEntity
import com.neo.moneytracker.data.mapper.toDomainModel
import com.neo.moneytracker.domain.repository.TransactionRepository
import com.neo.moneytracker.domain.usecase.CalculateTotalUseCase
import com.neo.moneytracker.domain.usecase.DateWiseTotalUseCase
import com.neo.moneytracker.domain.usecase.GetTransactionsUseCase
import com.neo.moneytracker.utils.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val calculateTotalsUseCase: CalculateTotalUseCase,
    private val dateWiseTotalUseCase: DateWiseTotalUseCase,
    private val repository: TransactionRepository
) : ViewModel() {

    private val _transactions = getTransactionsUseCase()
        .map { list -> list.map { it.toDataEntity() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val transactions: StateFlow<List<TransactionEntity>> = _transactions

    val incomeTotalAmount = _transactions.map {
        calculateTotalsUseCase(it.map { t -> t.toDomainModel() }).first
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    val expensesTotalAmount = _transactions.map {
        calculateTotalsUseCase(it.map { t -> t.toDomainModel() }).second
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    val dateWiseTotals = _transactions.map {
        dateWiseTotalUseCase(it.map { t -> t.toDomainModel() })
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    fun addTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            repository.addTransaction(transaction.toDomainModel())
        }
    }

    fun updateTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            repository.updateTransaction(transaction.toDomainModel())
        }
    }

    fun deleteTransaction(transactionId: Int) {
        viewModelScope.launch {
            repository.deleteTransaction(transactionId)
        }
    }
}
