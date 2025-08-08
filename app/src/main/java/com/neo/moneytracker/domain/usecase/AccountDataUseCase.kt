package com.neo.moneytracker.domain.usecase

import com.neo.moneytracker.data.localDb.entities.AddAccountEntity
import com.neo.moneytracker.domain.model.AddAccount
import com.neo.moneytracker.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountDataUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun addAccount(account: AddAccount){
        repository.addAccount(account)
    }

    suspend fun update(account: AddAccount){
        repository.updateAccount(account)
    }


    suspend fun getAccount(): Flow<List<AddAccountEntity>>{
        return repository.getAccount()
    }

    suspend fun deleteAccount(id: Int){
        repository.deleteAccount(id)
    }

    suspend fun getAccountById(id: Int): AddAccountEntity{
        return repository.getAccountById(id)
    }
}