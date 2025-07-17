package com.neo.moneytracker.data.localDb.dao

import androidx.room.Dao
import androidx.room.Insert
import com.neo.moneytracker.data.localDb.entities.AddAccountEntity

@Dao
interface AddAccountDao {

    @Insert
    suspend fun insertAccount(account: AddAccountEntity)
}