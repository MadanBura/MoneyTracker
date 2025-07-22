package com.neo.moneytracker.domain.usecase

import com.neo.moneytracker.domain.model.Transaction
import com.neo.moneytracker.domain.repository.TransactionRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(transaction:Transaction) : Long{
        return transactionRepository.addTransaction(transaction)
    }
}