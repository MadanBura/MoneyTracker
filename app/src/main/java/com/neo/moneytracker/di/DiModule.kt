package com.neo.moneytracker.di

import com.neo.moneytracker.data.repoImpl.CategoryRepoImpl
import com.neo.moneytracker.domain.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiModule {

    @Provides
    @Singleton
    fun provideUserModule(repo: CategoryRepoImpl): CategoryRepository {
        return repo
    }
}