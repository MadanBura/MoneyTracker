package com.neo.moneytracker.domain.usecase

import com.neo.moneytracker.domain.model.Transaction
import com.neo.moneytracker.utils.TransactionType
import javax.inject.Inject

class CalculateTotalUseCase @Inject constructor() {
    operator fun invoke(transactions: List<Transaction>): Pair<Int, Int> {
        val income = transactions.filter { it.type == TransactionType.INCOME.name }
            .sumOf { it.amount.toIntOrNull() ?: 0 }
        val expense = transactions.filter { it.type == TransactionType.EXPENSES.name }
            .sumOf { it.amount.toIntOrNull() ?: 0 }
        return Pair(income, expense)
    }
}
