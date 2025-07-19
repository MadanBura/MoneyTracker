package com.neo.moneytracker.data.mapper

import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.domain.model.Transaction
import com.neo.moneytracker.utils.TransactionType

//
//import com.neo.moneytracker.data.localDb.entities.ExpenseTransactionEntity
//import com.neo.moneytracker.domain.model.ExpenseTransaction
//
//fun ExpenseTransactionEntity.toExpenseTransaction(): ExpenseTransaction {
//    return ExpenseTransaction(
//        id = this.id,
//        iconRes = this.iconRes,
//        amount = this.amount,
//        note = this.note,
//        date = this.date,
//        category = this.category,
//        type = this.type
//    )
//}
//
//
//fun IncomeTransactionEntity.toIncomeTransaction(): IncomeTransaction {
//    return IncomeTransaction(
//        id = this.id,
//        iconRes = this.iconRes,
//        amount = this.amount,
//        note = this.note,
//        date = this.date,
//        category = this.category,
//        type = this
//            .type
//    )
//}
//
//
//fun ExpenseTransaction.toExpenseTransactionEntity(): ExpenseTransactionEntity {
//    return ExpenseTransactionEntity(
//        id = this.id,
//        iconRes = this.iconRes,
//        amount = this.amount,
//        note = this.note,
//        date = this.date,
//        category = this.category,
//        type = this.type
//    )
//}
//
//
//fun IncomeTransaction.toIncomeTransactionEntity(): IncomeTransactionEntity {
//    return IncomeTransactionEntity(
//        id = this.id,
//        iconRes = this.iconRes,
//        amount = this.amount,
//        note = this.note,
//        date = this.date,
//        category = this.category,
//        type = this.type
//    )
//}

fun TransactionEntity.toDomainModel(): Transaction {
    return Transaction(
        id = id,
        iconRes = this.iconRes,
        amount = this.amount,
        note = this.note,
        date = this.date,
        category = this.category,
        type = this.type
    )
}

fun Transaction.toDataEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        iconRes = this.iconRes,
        amount = this.amount,
        note = this.note,
        date = this.date,
        category = this.category,
        type = this.type
    )
}