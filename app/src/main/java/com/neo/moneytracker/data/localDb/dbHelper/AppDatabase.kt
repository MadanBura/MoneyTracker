package com.neo.moneytracker.data.localDb.dbHelper

import androidx.room.Database
import androidx.room.RoomDatabase
import com.neo.moneytracker.data.localDb.dao.TransactionDao
import com.neo.moneytracker.data.localDb.entities.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTransactionDao(): TransactionDao
}
