package com.neo.moneytracker.domain.model

import com.neo.moneytracker.utils.TransactionType

data class Transaction(
    val iconRes: Int,
    val amount: String,
    val note: String,
    val date: String,
    val category: String,
    val id: Int=0,
    val type: String
)

