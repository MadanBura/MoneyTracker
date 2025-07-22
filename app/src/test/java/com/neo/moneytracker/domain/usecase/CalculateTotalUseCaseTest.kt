package com.neo.moneytracker.domain.usecase

import com.neo.moneytracker.domain.model.Transaction
import com.neo.moneytracker.utils.TransactionType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculateTotalUseCaseTest {

    private lateinit var calculateTotalUseCase: CalculateTotalUseCase

    @Before
    fun setup() {
        calculateTotalUseCase = CalculateTotalUseCase()
    }

    @Test
    fun `calculate totals correctly for valid transactions`() {
        val transactions = listOf(
            Transaction(id = 1, amount = "100", note = "Salary", date = "2024-01-01", category = "Job", type = TransactionType.INCOME.name, iconRes = 1),
            Transaction(id = 2, amount = "50", note = "Groceries", date = "2024-01-02", category = "Food", type = TransactionType.EXPENSES.name, iconRes = 1),
            Transaction(id = 3, amount = "200", note = "Freelance", date = "2024-01-03", category = "Job", type = TransactionType.INCOME.name, iconRes = 2),
            Transaction(id = 4, amount = "70", note = "Transport", date = "2024-01-04", category = "Travel", type = TransactionType.EXPENSES.name, iconRes = 2),
        )

        val (income, expense) = calculateTotalUseCase(transactions)

        assertEquals(300, income)
        assertEquals(120, expense)
    }

    @Test
    fun `calculate totals with invalid amount values`() {
        val transactions = listOf(
            Transaction(id = 1, amount = "100", note = "Salary", date = "2024-01-01", category = "Job", type = TransactionType.INCOME.name, iconRes = 1),
            Transaction(id = 2, amount = "50", note = "Groceries", date = "2024-01-02", category = "Food", type = TransactionType.EXPENSES.name, iconRes = 1),
        )

        val (income, expense) = calculateTotalUseCase(transactions)

        assertEquals(0, income)
        assertEquals(100, expense)
    }

    @Test
    fun `returns zero totals for empty list`() {
        val transactions = emptyList<Transaction>()

        val (income, expense) = calculateTotalUseCase(transactions)

        assertEquals(0, income)
        assertEquals(0, expense)
    }
}
