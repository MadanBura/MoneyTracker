package com.neo.moneytracker.data.repoImpl

import com.neo.moneytracker.data.localDb.dao.AddAccountDao
import com.neo.moneytracker.data.localDb.entities.AddAccountEntity
import com.neo.moneytracker.domain.model.AddAccount
import com.neo.moneytracker.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val addAccount: AddAccountDao
): AccountRepository {
    override suspend fun addAccount(account: AddAccount) {
         addAccount.insertAccount(account = AddAccountEntity(
             accountName = account.accountName,
             type = account.type,
             currency = account.currency,
             amount = account.amount,
             icon = account.icon,
             liabilities = account.liabilities,
             note = account.note
         ))
    }

    override suspend fun getAccount(): Flow<List<AddAccountEntity>> {
        return addAccount.getAccountDetails()
    }

    override suspend fun deleteAccount(id: Int) {
        addAccount.deleteAccount(id)
    }
}