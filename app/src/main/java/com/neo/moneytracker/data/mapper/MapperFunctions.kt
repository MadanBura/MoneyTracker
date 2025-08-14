package com.neo.moneytracker.data.mapper

import com.neo.moneytracker.data.localDb.entities.AddAccountEntity
import com.neo.moneytracker.data.localDb.entities.CategoryDataEntity
import com.neo.moneytracker.data.localDb.entities.CategoryEntity
import com.neo.moneytracker.data.localDb.entities.SubCategoryEntity
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.domain.model.AddAccount
import com.neo.moneytracker.domain.model.Category
import com.neo.moneytracker.domain.model.CategoryData
import com.neo.moneytracker.domain.model.SubCategory
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


fun AddAccountEntity.toDomain(): AddAccount {
    return AddAccount(
        accountName = this.accountName,
        type = this.type,
        currency = this.currency,
        amount = this.amount,
        icon = this.icon,
        liabilities = this.liabilities,
        note = this.note
    )
}

fun AddAccount.toEntity(id: Int = 0): AddAccountEntity {
    return AddAccountEntity(
        id = id,
        accountName = this.accountName,
        type = this.type,
        currency = this.currency,
        amount = this.amount,
        icon = this.icon,
        liabilities = this.liabilities,
        note = this.note
    )
}



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
fun CategoryDataEntity.toDomain(): CategoryData {
    return CategoryData(
        income = income.map { it.toDomain() },
        expenses = expenses.map { it.toDomain() },
        transfer = transfer.map { it.toDomain() }
    )
}

fun CategoryEntity.toDomain(): Category {
    return Category(
        name = name,
        subcategories = subcategories.map { it.toDomain() }
    )
}

fun SubCategoryEntity.toDomain(): SubCategory {
    return SubCategory(
        name = name,
        icon = icon,
        iconResId = 0
    )
}
