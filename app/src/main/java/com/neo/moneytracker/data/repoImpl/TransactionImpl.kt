package com.neo.moneytracker.data.repoImpl

import androidx.compose.runtime.traceEventStart
import com.neo.moneytracker.data.localDb.dao.TransactionDao
import com.neo.moneytracker.data.mapper.toDataEntity
import com.neo.moneytracker.data.mapper.toDomainModel
import com.neo.moneytracker.domain.model.Transaction
import com.neo.moneytracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionImpl @Inject constructor(
    private val transactionDao: TransactionDao
) :TransactionRepository {

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions().map { transactionEntities ->
            transactionEntities.map { it.toDomainModel() }
        }
    }

    override suspend fun addTransaction(transaction: Transaction): Long {
        return transactionDao.insertTransaction(transaction.toDataEntity())
    }

    override suspend fun deleteTransaction(id: Int) {
        return transactionDao.deleteTransaction(id)
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        return transactionDao.updateTransaction(transaction.toDataEntity())
    }
}