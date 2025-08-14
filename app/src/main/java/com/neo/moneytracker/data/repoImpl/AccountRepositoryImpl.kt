package com.neo.moneytracker.data.repoImpl

import com.neo.moneytracker.data.localDb.dao.AddAccountDao
import com.neo.moneytracker.data.localDb.entities.AddAccountEntity
import com.neo.moneytracker.data.mapper.toDomain
import com.neo.moneytracker.domain.model.AddAccount
import com.neo.moneytracker.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun getAccount(): Flow<List<AddAccount>> {
        return addAccount.getAccountDetails()
            .map { it ->
                it.map { it.toDomain() }
            }
    }
    override suspend fun deleteAccount(id: Int) {
        addAccount.deleteAccount(id)
    }

    override suspend fun updateAccount(account: AddAccount) {
        addAccount.updateAccount(account = AddAccountEntity(
            accountName = account.accountName,
            type = account.type,
            currency = account.currency,
            amount = account.amount,
            icon = account.icon,
            liabilities = account.liabilities,
            note = account.note
        ))
    }

    override suspend fun getAccountById(id: Int): AddAccount {
        return addAccount.getAccountById(id).toDomain()
    }
}