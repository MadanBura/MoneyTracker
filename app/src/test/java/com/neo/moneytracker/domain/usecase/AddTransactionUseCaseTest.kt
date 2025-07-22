package com.neo.moneytracker.domain.usecase

import com.neo.moneytracker.domain.model.Transaction
import com.neo.moneytracker.domain.repository.TransactionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


//To test business logic independently from UI/data
class AddTransactionUseCaseTest {

    private val repository = mockk<TransactionRepository>()
    private lateinit var addTransactionUseCase: AddTransactionUseCase

    @Before
    fun setup() {
        addTransactionUseCase = AddTransactionUseCase(repository)
    }

    //we don't need to call this function it automatically gets called
    //so name does not matter
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

        //
        coEvery { repository.addTransaction(transaction) } returns 1L

        addTransactionUseCase(transaction)

        coVerify { repository.addTransaction(transaction) }
    }
}
//if you are using get Transaction and check what repo is returning
//then use asserEquals(expectedResult, useCaseResult)