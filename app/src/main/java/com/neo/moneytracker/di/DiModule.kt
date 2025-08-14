package com.neo.moneytracker.di

import android.content.Context
import com.neo.moneytracker.data.localDb.dao.AddAccountDao
import com.neo.moneytracker.data.localDb.dao.TransactionDao
import com.neo.moneytracker.data.repoImpl.AccountRepositoryImpl
import com.neo.moneytracker.data.repoImpl.CategoryRepoImpl
import com.neo.moneytracker.data.repoImpl.TransactionImpl
import com.neo.moneytracker.domain.repository.AccountRepository
import com.neo.moneytracker.domain.repository.CategoryRepository
import com.neo.moneytracker.domain.repository.TransactionRepository
import com.neo.moneytracker.domain.usecase.CategoryDataUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DiModule {
    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        impl: TransactionImpl
    ): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindAccountRepository(
        impl: AccountRepositoryImpl
    ): AccountRepository


    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        impl: CategoryRepoImpl
    ): CategoryRepository
}