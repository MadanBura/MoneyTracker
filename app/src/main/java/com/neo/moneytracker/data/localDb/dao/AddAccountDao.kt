package com.neo.moneytracker.data.localDb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.neo.moneytracker.data.localDb.entities.AddAccountEntity

@Dao
interface AddAccountDao {

    @Insert
    suspend fun insertAccount(account: AddAccountEntity)

    @Query("Select * from AddAccount")
    suspend fun getAccountDetails(): List<AddAccountEntity>

    @Query("Delete from AddAccount where id=:id")
    suspend fun deleteAccount(id: Int)
}