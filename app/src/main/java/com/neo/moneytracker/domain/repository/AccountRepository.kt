package com.neo.moneytracker.domain.repository

import com.neo.moneytracker.data.localDb.entities.AddAccountEntity
import com.neo.moneytracker.domain.model.AddAccount
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun addAccount(account: AddAccount)

    suspend fun getAccount(): Flow<List<AddAccountEntity>>

    suspend fun deleteAccount(id: Int)

    suspend fun updateAccount(account: AddAccount)

    suspend fun getAccountById(id: Int): AddAccountEntity
}