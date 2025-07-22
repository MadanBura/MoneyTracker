package com.neo.moneytracker.ui.screens

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.navigation.compose.rememberNavController
import com.neo.moneytracker.base.MainActivity
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.domain.repository.TransactionRepository
import com.neo.moneytracker.ui.viewmodel.TransactionViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.neo.moneytracker.R
import com.neo.moneytracker.data.mapper.toDomainModel

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
    fun recordScreen_drawerOpensOnMenuClick() {

        composeRule.onNodeWithContentDescription("Add").performClick()


        composeRule.onNodeWithText("expenses")
            .assertIsDisplayed()

        composeRule.onNodeWithText("expenses").performClick()

        composeRule.onNodeWithContentDescription("Groceries")
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Groceries").performClick()

        composeRule.onNodeWithTag("subcategoryGroceries")
            .assertIsSelected()

        composeRule.onNodeWithTag("AmountField")
            .assertExists()
            .performTextInput("500")

        composeRule.onNodeWithText("500")
            .assertIsDisplayed()

        composeRule.onNodeWithText("500").assertTextEquals("500")

        composeRule.onNodeWithTag("NoteField")
            .assertExists()
            .performTextInput("Monthly groceries")

        composeRule.onNodeWithText("Monthly groceries")
            .assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Check Icon")
            .assertExists()
            .assertIsDisplayed()
            .performClick()

        composeRule.waitUntil(timeoutMillis = 4_000) {
            composeRule.onAllNodesWithTag("BalanceField").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("BalanceField")
            .assertIsDisplayed()
            .assertTextEquals("-500")

        composeRule.onNodeWithTag("TransactionItem_Groceries_500")
            .assertExists()
            .performTouchInput {
                swipeLeft()
            }

/*
        composeRule.onNodeWithText("DeleteButton_Groceries_500")
            .isDisplayed()
*/

        /*composeRule.onNodeWithTag("DeleteButton_Groceries_500")
            .assertExists()
            .performClick()*/

        composeRule.onNodeWithTag("DeleteButton_Groceries_500").assertIsDisplayed()
        val node = composeRule.onNodeWithTag("DeleteButton_Groceries_500")

        node.performClick()

        composeRule.onNodeWithTag("DeleteButton_Groceries_500").isNotDisplayed()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("DeleteDialog").assertExists()

      /*  composeRule.onNodeWithText("Confirm Deletion")
            .assertIsDisplayed()
            //.isDisplayed()*/

      //  composeRule.onNode(hasText("Confirm Deletion") and hasClickAction()).assertExists().performClick()

       /* composeRule.onNodeWithTag("ConfirmButton")
            .isDisplayed()*/

       /* composeRule.onNodeWithTag("ConfirmButton")
            .assertExists()
            .performClick()*/




      /*  composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag("DeleteButton_Groceries_500").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag("DeleteButton_Groceries_500")
            .assertExists()
            .performClick()


        composeRule.onNodeWithTag("DeleteDialog")
            .onChild()
            .assert(hasText("Confirm Deletion"))
            .assertIsDisplayed()

*/





    }


}