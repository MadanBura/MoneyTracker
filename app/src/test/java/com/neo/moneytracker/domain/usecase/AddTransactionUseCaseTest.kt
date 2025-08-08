package com.neo.moneytracker.domain.usecase

import com.neo.moneytracker.domain.model.Transaction
import com.neo.moneytracker.domain.repository.TransactionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddTransactionUseCaseTest {

    private val repository = mockk<TransactionRepository>()
    private lateinit var addTransactionUseCase: AddTransactionUseCase

    @Before
    fun setup() {
        addTransactionUseCase = AddTransactionUseCase(repository)
    }

    @Test
    fun `adds transaction and calls repository`(): Unit = runTest {
        val transaction = Transaction(
            iconRes = 1,
            amount = "100",
            note = "Groceries",
            date = "2024-07-16",
            category = "Food",
            type = "EXPENSE"
        )

        coEvery { repository.addTransaction(transaction) } returns 1L

        addTransactionUseCase(transaction)

        coVerify { repository.addTransaction(transaction) }
    }
}

