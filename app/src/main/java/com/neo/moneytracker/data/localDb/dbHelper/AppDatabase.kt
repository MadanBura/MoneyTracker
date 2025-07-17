package com.neo.moneytracker.data.localDb.dbHelper

import androidx.room.Database
import androidx.room.RoomDatabase
import com.neo.moneytracker.data.localDb.dao.AddAccountDao
import com.neo.moneytracker.data.localDb.dao.TransactionDao
import com.neo.moneytracker.data.localDb.entities.AddAccountEntity
import com.neo.moneytracker.data.localDb.entities.TransactionEntity

@Database(entities = [TransactionEntity::class, AddAccountEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun addAccountDao(): AddAccountDao
}
