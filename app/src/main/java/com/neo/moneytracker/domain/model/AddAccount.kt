package com.neo.moneytracker.domain.model

data class AddAccount(
    val accountName: String,
    val type: String,
    val currency: String,
    val amount: Long,
    val icon: Int,
    val liabilities: Boolean,
    val note: String
)