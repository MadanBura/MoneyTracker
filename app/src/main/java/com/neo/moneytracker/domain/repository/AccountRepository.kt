package com.neo.moneytracker.domain.repository

import com.neo.moneytracker.domain.model.AddAccount

interface AccountRepository {
    suspend fun addAccount(account: AddAccount)
}