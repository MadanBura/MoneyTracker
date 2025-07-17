package com.neo.moneytracker.data.localDb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val iconRes: Int,
    val amount: String,
    val note: String,
    val date: String,
    val category: String
)
