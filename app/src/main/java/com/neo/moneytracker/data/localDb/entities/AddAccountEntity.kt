package com.neo.moneytracker.data.localDb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AddAccount")
data class AddAccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val accountName: String,
    val type: String,
    val currency: String,
    val amount: Long,
    val icon: Int,
    val liabilities: Boolean,
    val note: String
)