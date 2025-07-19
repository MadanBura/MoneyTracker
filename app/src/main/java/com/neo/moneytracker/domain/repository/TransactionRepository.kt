package com.neo.moneytracker.domain.repository

import com.neo.moneytracker.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getAllTransactions() : Flow<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction) : Long
    suspend fun deleteTransaction(id: Int)
    suspend fun updateTransaction(transaction: Transaction)
}