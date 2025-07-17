package com.neo.moneytracker.domain.repository

import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getAllTransaction() : Flow<List<TransactionEntity>>
    suspend fun addTransaction(transactionEntity: TransactionEntity)
}