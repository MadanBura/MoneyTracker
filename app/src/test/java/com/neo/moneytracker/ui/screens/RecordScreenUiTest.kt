package com.neo.moneytracker.ui.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.neo.moneytracker.base.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [34])
@RunWith(RobolectricTestRunner::class)
class RecordScreenUiTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun recordScreen_displaysTopBarAndStickyHeader() {
        composeRule.onNodeWithText("Money Tracker")
            .assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Search")
            .assertIsDisplayed()
    }

    @Test
    fun recordScreen_addAndDeleteTransaction() {
        // Open Add Transaction screen
        composeRule.onNodeWithContentDescription("Add").performClick()

        // Select Expense
        composeRule.onNodeWithText("expenses")
            .assertIsDisplayed()
            .performClick()

        // Select Category
        composeRule.onNodeWithContentDescription("Groceries")
            .assertIsDisplayed()
            .performClick()

        // Verify subcategory selection
        composeRule.onNodeWithTag("subcategoryGroceries")
            .assertIsSelected()

        // Enter Amount
        composeRule.onNodeWithTag("AmountField")
            .assertExists()
            .performTextInput("500")

        composeRule.onNodeWithText("500")
            .assertIsDisplayed()
            .assertTextEquals("500")

        // Enter Note
        composeRule.onNodeWithTag("NoteField")
            .assertExists()
            .performTextInput("Monthly groceries")

        composeRule.onNodeWithText("Monthly groceries")
            .assertIsDisplayed()

        // Submit Transaction
        composeRule.onNodeWithContentDescription("Check Icon")
            .assertExists()
            .assertIsDisplayed()
            .performClick()

        // Wait for balance to update
        composeRule.waitUntil(timeoutMillis = 4_000) {
            composeRule.onAllNodesWithTag("BalanceField").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify balance
        composeRule.onNodeWithTag("BalanceField")
            .assertIsDisplayed()
            .assertTextEquals("-500")

        // Swipe transaction item to reveal delete
        composeRule.onNodeWithTag("TransactionItem_Groceries_500")
            .assertExists()
            .performTouchInput {
                swipeLeft()
            }

        // Click delete button
        composeRule.onNodeWithTag("DeleteButton_Groceries_500")
            .assertIsDisplayed()
            .performClick()

        // Confirm delete dialog shows
        composeRule.onNodeWithTag("DeleteDialog")
            .assertExists()

        // Click Confirm (You must make sure this button has tag "ConfirmButton" in your UI)
        composeRule.onNodeWithTag("ConfirmButton")
            .assertIsDisplayed()
            .performClick()

        composeRule.waitUntil(5_000){
            composeRule.onAllNodesWithTag("TransactionItem_Groceries_500").fetchSemanticsNodes().isEmpty()
        }

        composeRule.onNodeWithTag("TransactionItem_Groceries_500").assertDoesNotExist()

        composeRule.onNodeWithTag("BalanceField").assertIsDisplayed().assertTextEquals("0")

        // Optionally: Check item is removed (requires test data revalidation)
        // composeRule.onNodeWithTag("TransactionItem_Groceries_500").assertDoesNotExist()
    }
}
