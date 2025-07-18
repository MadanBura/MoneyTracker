package com.neo.moneytracker.di

import com.neo.moneytracker.data.localDb.dbHelper.AppDatabase
import android.app.Application
import androidx.room.Room
import com.neo.moneytracker.data.localDb.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "moneyTrackerDbV1.3"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideExpensesTransactionDao(database: AppDatabase): TransactionDao {
        return database.getTransactionDao()
    }
}
