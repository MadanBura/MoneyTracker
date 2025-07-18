package com.neo.moneytracker.data.localDb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.neo.moneytracker.data.localDb.entities.AddAccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AddAccountDao {

    @Insert
    suspend fun insertAccount(account: AddAccountEntity)

    @Query("Select * from AddAccount")
    fun getAccountDetails(): Flow<List<AddAccountEntity>>

    @Query("Delete from AddAccount where id=:id")
    suspend fun deleteAccount(id: Int)

    @Update
    suspend fun updateAccount(account: AddAccountEntity)

    @Query("Select * from AddAccount where id=:id")
    suspend fun getAccountById(id: Int): AddAccountEntity
}