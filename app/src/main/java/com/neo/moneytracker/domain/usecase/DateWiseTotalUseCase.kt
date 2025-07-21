package com.neo.moneytracker.domain.usecase

import com.neo.moneytracker.domain.model.Transaction
import com.neo.moneytracker.utils.TransactionType
import javax.inject.Inject

class DateWiseTotalUseCase @Inject constructor() {
    operator fun invoke(transactions: List<Transaction>): Map<String, Pair<Int, Int>> {
        return transactions.groupBy { it.date }
            .mapValues { (_, list) ->
                val income = list.filter { it.type == TransactionType.INCOME.name }
                    .sumOf { it.amount.toIntOrNull() ?: 0 }
                val expense = list.filter { it.type == TransactionType.EXPENSES.name }
                    .sumOf { it.amount.toIntOrNull() ?: 0 }
                income to expense
            }
    }
}
