package com.neo.moneytracker.domain.usecase

import com.neo.moneytracker.domain.model.AddAccount
import com.neo.moneytracker.domain.repository.AccountRepository
import javax.inject.Inject

class AccountDataUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend operator fun invoke(account: AddAccount){
        repository.addAccount(account)
    }

}