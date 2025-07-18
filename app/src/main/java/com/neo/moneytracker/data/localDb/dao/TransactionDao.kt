package com.neo.moneytracker.data.localDb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("Delete from transactions where id = :id")
    suspend fun deleteTransaction(id: Int)}