package com.neo.moneytracker.domain.usecase

import com.neo.moneytracker.domain.model.Transaction
import com.neo.moneytracker.domain.repository.TransactionRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class AddTransactionUseCaseTestJunit {

    private lateinit var repository: TransactionRepository
    private lateinit var addTransactionUseCase: AddTransactionUseCase

    @Before
    fun setup() {
        repository = mock(TransactionRepository::class.java)
        addTransactionUseCase = AddTransactionUseCase(repository)
    }

    @Test
    fun `adds transaction and calls repository`() = runTest {
        val transaction = Transaction(
            iconRes = 1,
            amount = "100",
            note = "Groceries",
            date = "2024-07-16",
            category = "Food",
            type = "EXPENSE"
        )

        // Stub repository response
        `when`(repository.addTransaction(transaction)).thenReturn(1L)

        // Act
        addTransactionUseCase(transaction)

        // Assert (verify interaction)
        verify(repository, times(1)).addTransaction(transaction)
    }
}
