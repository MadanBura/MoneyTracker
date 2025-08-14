package com.neo.moneytracker

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.ui.screens.RecordScreen
import com.neo.moneytracker.ui.viewmodel.AccountsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RecordScreenUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeTransactionList = listOf(
        TransactionEntity(
            1,
            R.drawable.airplane_mode,
            "100",
            "abc",
            "21 July 2025",
            "Cash",
            "Expense"
        ),
        TransactionEntity(
            2,
            R.drawable.test,
            "5000",
            "xyz",
            "21 July 2025",
            "Bank",
            "Income"
        )
    )

    private lateinit var fakeTransactionViewModel: AccountsViewModel

    @Before
    fun setUp() {
        fakeTransactionViewModel = mockk(relaxed = true)
        every { fakeTransactionViewModel.transactions } returns MutableStateFlow(fakeTransactionList)
        every { fakeTransactionViewModel.incomeTotalAmount } returns MutableStateFlow(5000)
        every { fakeTransactionViewModel.expensesTotalAmount } returns MutableStateFlow(100)
    }

    @Test
    fun whenChartsIsClicked_thenAnalyticsIsDisplayed() {
        composeTestRule.setContent {
            val navController = rememberNavController()

            RecordScreen(
                navController = navController,
                transactionViewModel = fakeTransactionViewModel
            )
        }

        composeTestRule.onNodeWithText("Charts").performClick()
        composeTestRule.onNodeWithText("Analytics").assertIsDisplayed()
    }

}
