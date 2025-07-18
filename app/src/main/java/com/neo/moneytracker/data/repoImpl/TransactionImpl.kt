package com.neo.moneytracker.data.repoImpl

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

    override suspend fun addTransaction(transaction: Transaction) {
        return transactionDao.insertTransaction(transaction.toDataEntity())
    }
}