package com.neo.moneytracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.moneytracker.data.localDb.dao.TransactionDao
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository) : ViewModel() {

//  Sharing is started when the first subscriber appears and never stops.
    //stateIn-> It start on given coroutineScope,

    val transactions: StateFlow<List<TransactionEntity>> = transactionRepository.getAllTransaction()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            transactionRepository.addTransaction(transaction)
        }
    }
}