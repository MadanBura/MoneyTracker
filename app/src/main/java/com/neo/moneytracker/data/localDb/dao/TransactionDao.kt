package com.neo.moneytracker.data.localDb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY id DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Insert
    suspend fun insertTransaction(transaction: TransactionEntity)
}