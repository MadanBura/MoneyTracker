package com.neo.moneytracker.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neo.moneytracker.utils.TransactionType

@Entity
data class Transaction(
    val iconRes: Int,
    val amount: String,
    val note: String,
    val date: String,
    val category: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String
)

