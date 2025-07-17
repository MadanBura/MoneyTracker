package com.neo.moneytracker.di

import android.content.Context
import com.neo.moneytracker.data.localDb.dao.TransactionDao
import com.neo.moneytracker.data.repoImpl.CategoryRepoImpl
import com.neo.moneytracker.domain.repository.CategoryRepository
import com.neo.moneytracker.domain.usecase.CategoryDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(
        @ApplicationContext context: Context
    ): CategoryRepository {
        return CategoryRepoImpl(context)
    }

    @Provides
    @Singleton
    fun provideCategoryDataUseCase(
        repository: CategoryRepository
    ): CategoryDataUseCase {
        return CategoryDataUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesTransactionRepository(transactionDao: TransactionDao):AddressRepository{
        return AddressRepoImpl(addressDao)
    }



}