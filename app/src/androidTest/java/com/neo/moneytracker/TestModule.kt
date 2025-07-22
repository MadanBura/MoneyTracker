package com.neo.moneytracker

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.neo.moneytracker.ui.viewmodel.AccountsViewModel
import com.neo.moneytracker.ui.viewmodel.TransactionViewModel
import com.neo.moneytracker.ui.viewmodel.UiStateViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mockk.mockk

@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Provides
    fun provideMockTransactionViewModel(): TransactionViewModel {
        return mockk() // Provide a mocked instance here
    }

    @Provides
    fun provideMockUiStateViewModel(): UiStateViewModel {
        return mockk() // Provide a mocked instance of UiStateViewModel here
    }

    @Provides
    fun provideMockAccountsViewModel(): AccountsViewModel {
        return mockk() // Provide a mocked instance of AccountsViewModel here
    }

    @Provides
    fun provideContext(): Context {
        return ApplicationProvider.getApplicationContext() // Use the application context for testing
    }

    @Provides
    fun provideMockNavController(context: Context): NavController {
        return TestNavHostController(context) // Provide TestNavHostController with ApplicationContext
    }
}