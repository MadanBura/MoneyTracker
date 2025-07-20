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
            val newId = transactionRepository.addTransaction(transaction.toDomainModel())
            Log.d("TransactionViewModel", "Inserted transaction with id: $newId")
        }
    }

    val incomeTotalAmount: StateFlow<Int> = _transactions.map { transactions ->
        transactions
            .map { it.toDomainModel() }
            .filter { it.type == TransactionType.INCOME.name }
            .sumOf { it.amount.toIntOrNull() ?: 0 }
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    val expensesTotalAmount: StateFlow<Int> = _transactions.map { transactions ->
        transactions
            .map { it.toDomainModel() }
            .filter { it.type == TransactionType.EXPENSES.name }
            .sumOf { it.amount.toIntOrNull() ?: 0 }
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    //    fun calculateTotalsForDate(date: String): Pair<Int, Int> {
//        val filtered = transactions.value.filter { it.date == date }
//
//        val incomeTotal = filtered
//            .filter { it.type == TransactionType.INCOME.name }
//            .mapNotNull { it.amount.toIntOrNull() }.sum()
//
//        val expenseTotal = filtered
//            .filter { it.type == TransactionType.EXPENSES.name }
//            .mapNotNull { it.amount.toIntOrNull() }
//            .sum()
//
//        return Pair(incomeTotal, expenseTotal)
//    }

    val dateWiseTotals: StateFlow<Map<String, Pair<Int, Int>>> = _transactions
        .map { transactions ->
            transactions
                .groupBy { it.date }
                .mapValues { (_, list) ->
                    val income = list.filter { it.type == TransactionType.INCOME.name }
                        .sumOf { it.amount.toIntOrNull() ?: 0 }
                    val expense = list.filter { it.type == TransactionType.EXPENSES.name }
                        .sumOf { it.amount.toIntOrNull() ?: 0 }
                    Pair(income, expense)
                }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyMap()
        )


    fun deleteTransaction(id: Int) {
        if (id == 0) {
            Log.w("TransactionViewModel", "Attempted to delete transaction with id=0 — skipping")
            return
        }

        viewModelScope.launch {
            Log.d("TransactionViewModel", "Deleting transaction: $id")
            transactionRepository.deleteTransaction(id)
        }





    }


    fun updateTransaction(transaction: TransactionEntity){
        viewModelScope.launch {
            transactionRepository.updateTransaction(transaction.toDomainModel())
        }
    }

}