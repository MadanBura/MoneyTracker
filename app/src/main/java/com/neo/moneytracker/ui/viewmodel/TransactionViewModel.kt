package com.neo.moneytracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.data.mapper.toDataEntity
import com.neo.moneytracker.data.mapper.toDomainModel
import com.neo.moneytracker.domain.repository.TransactionRepository
import com.neo.moneytracker.utils.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository) :
    ViewModel() {

    private val _transactions: StateFlow<List<TransactionEntity>> = transactionRepository
        .getAllTransactions()
        .map { transactionEntities ->
            transactionEntities.map { it.toDataEntity() }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Expose StateFlow to the UI
    val transactions: StateFlow<List<TransactionEntity>> get() = _transactions
    fun addTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            transactionRepository.addTransaction(transaction.toDomainModel())
        }
    }

    val incomeTotalAmount: StateFlow<Double> = _transactions.map { transactions ->
        transactions
            .map { it.toDomainModel() }
            .filter { it.type == TransactionType.INCOME.name }
            .sumOf { it.amount.toDouble() }
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    val expensesTotalAmount: StateFlow<Double> = _transactions.map { transactions ->
        transactions
            .map { it.toDomainModel() }
            .filter { it.type == TransactionType.EXPENSES.name }
            .sumOf { it.amount.toDouble() }
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)


    fun calculateTotalsForDate(date: String): Pair<Int, Int> {
        val filtered = transactions.value.filter { it.date == date }
        val incomeTotal = filtered.filter { it.type == TransactionType.INCOME.name }.sumOf { it.amount.toInt() }
        val expenseTotal = filtered.filter { it.type == TransactionType.EXPENSES.name }.sumOf { it.amount.toInt()}
        return Pair(incomeTotal, expenseTotal)
    }

}