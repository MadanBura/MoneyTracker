package com.neo.moneytracker.data.repoImpl

import com.neo.moneytracker.data.localDb.dao.TransactionDao
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionImpl @Inject constructor(
    private val transactionDao: TransactionDao
) :TransactionRepository {

    override fun getAllTransaction(): Flow<List<TransactionEntity>> {
        return transactionDao.getAllTransactions()
    }

    override suspend fun addTransaction(transactionEntity: TransactionEntity) {
        return transactionDao.insertTransaction(transactionEntity)
    }
}