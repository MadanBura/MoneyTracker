package com.neo.moneytracker.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.TextField
import androidx.compose.material3.TextField as NewTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material3.TextFieldDefaults as NewTextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.neo.moneytracker.R
import com.neo.moneytracker.data.localDb.entities.AddAccountEntity
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.data.mapper.toDataEntity
import com.neo.moneytracker.domain.model.Transaction
import com.neo.moneytracker.ui.theme.IconBackGroundColor
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.theme.YellowOrange
import com.neo.moneytracker.ui.viewmodel.AccountsViewModel
import com.neo.moneytracker.utils.TransactionType
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AmountInputDialog(
    iconRes: Int,
    selectedCategory: String,
    selectedSubCategory : String,
    accountViewModel: AccountsViewModel,
    onTransactionAdded: (TransactionEntity) -> Unit,
    onDismiss : ()->Unit,
    initialAmount: String = "",
    initialNote: String = "",
    initialDate: String = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
) {
    var note by rememberSaveable { mutableStateOf(initialNote) }
    var amount by rememberSaveable { mutableStateOf(initialAmount) }
    var showCalendar by rememberSaveable{ mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    var selectedDate by remember {
        mutableStateOf(SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(calendar.time))
    }

    val expensesLabel = stringResource(R.string.expenses)
    val incomeLabel = stringResource(R.string.income)
    val dateLabel = stringResource(R.string.date)
    val tickLabel = stringResource(R.string.tick)
    val backSpaceLabel = stringResource(R.string.backspace)


    var bottomNavBool by remember{
        mutableStateOf(false)
    }

    val keypadButtons = listOf(
        listOf("7", "8", "9", "Date"),
        listOf("4", "5", "6", "+"),
        listOf("1", "2", "3", "-"),
        listOf(".", "0", "⌫", "✔")
    )

    Box(
        modifier = Modifier
            .background(IconBackGroundColor)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                TextField(
                    value = amount,
                    onValueChange = { newAmount ->
                        if (newAmount.isEmpty() || newAmount.matches("^[0-9]*\\.?[0-9]*$".toRegex())) {
                            amount = newAmount
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 4.dp)
                        .background(Color.Transparent).testTag("AmountField"),
                    textStyle = TextStyle(
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.End
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.checklist),
                            contentDescription = "Settings Icon",
                            modifier = Modifier.size(30.dp).clickable {
                                bottomNavBool = true
                            }
                        )
                    }
                )
            }

//            LaunchedEffect(Unit) {
//                accountViewModel.getAccounts()
//            }

            val accountList =  accountViewModel.accounts.collectAsState().value

            Log.d("HELLO_ACCOUNT_Amount", accountList.toString())

            if(bottomNavBool){
                AccountBottomSheet(
                   accountList,
                    bottomNavBool,
                    onDismiss = {
                        bottomNavBool = false
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Note :",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(end = 8.dp)
                )

                NewTextField(
                    value = note,
                    onValueChange = { note = it },
                    placeholder = { Text("Enter a note", color = Color.Gray) },
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically).testTag("NoteField"),
                    singleLine = true,
                    colors = NewTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = { /* handle camera click */ }) {
                    Image(
                        painter = painterResource(id = R.drawable.camera),
                        contentDescription = "Camera Icon",
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            keypadButtons.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    row.forEach { label ->
                        Button(
                            onClick = {
                                when (label) {
                                    backSpaceLabel -> { // Backspace logic
                                        amount = if (amount.length > 1) amount.dropLast(1) else "0"
                                    }
                                    tickLabel -> { // Confirm transaction
//                                        val currentDate =
//                                            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())

                                        val transaction = when(selectedCategory){
                                            expensesLabel ->{
                                                Transaction(
                       F                             id =0,
                                                    iconRes = iconRes,
                                                    amount = amount,
                                                    note = note,
                                                    date = selectedDate,
                                                    category = selectedSubCategory,
                                                    type = TransactionType.EXPENSES.name
                                                )
                                            }
                                            incomeLabel ->{
                                                Transaction(
                                                    id =0,
                                                    iconRes = iconRes,
                                                    amount = amount,
                                                    note = note,
                                                    date = selectedDate,
                                                    category = selectedSubCategory,
                                                    type = TransactionType.INCOME.name
                                                )
                                            }
                                            else-> null
                                        }
                                        transaction?.let { onTransactionAdded(it.toDataEntity()) }
                                        amount = "0"
                                        note = ""
                                        onDismiss()
                                    }
                                    dateLabel -> {
                                        showCalendar = true
                                    }
                                    "." -> if (!amount.contains(".")) amount += "."
                                    else -> {
                                        amount = if (amount == "0") label else amount + label
                                    }
                                }
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .weight(1f)
                                .padding(horizontal = 1.dp, vertical = 3.dp),
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = when (label) {
                                    "✔" -> LemonSecondary
                                    else -> Color.White
                                }
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            when (label) {
                                "Date" -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.calendar),
                                        contentDescription = "Calendar Icon",
                                        tint = Color(0xFFFFC107),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = selectedDate,
                                        fontSize = 12.sp,
                                        color = Color(0xFFFFC107),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                "✔" -> {
                                    Icon(
                                        painter = painterResource(id = R.drawable.check_),
                                        contentDescription = "Check Icon",
                                        tint = Color.Black,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                else -> {
                                    Text(
                                        text = label,
                                        fontSize = 20.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if (showCalendar) {
        Dialog(onDismissRequest = { showCalendar = false }) {
            CustomCalendarDialog(
                onDismiss = { showCalendar = false },
                onDateSelected = { selected ->
                    selectedDate = selected
                },
                isCompact = true
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountBottomSheet(
    accountList: List<AddAccountEntity>,
    showBottomSheet: Boolean,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )


    Log.d("HELLO_ACCOUNT", accountList.toString())
    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }

    ModalBottomSheet(

        sheetState = sheetState,
        onDismissRequest = { onDismiss() },
        dragHandle = null
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
                Text(
                    text = "Accounts",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            accountList.forEach { account ->
                Log.d("HELLO_ACCOUNT", account.toString())
                AccountItem(
                    account = account
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(5.dp)
            ){


                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFFFD54F), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.Badge, contentDescription = null, tint = Color.Black)
                }

                Spacer(modifier = Modifier.width(12.dp))


                Text(
                    "Not associated with any account",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    Icons.Filled.Check,
                    contentDescription = "",
                    tint = Color.Yellow
                )


            }

            Spacer(modifier = Modifier.height(80.dp))

            // Toggle Switch
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text("Automatically pop up every time")
////                Switch(checked = showAutoPopup, onCheckedChange = onAutoPopupToggle)
//            }
        }
    }
}


@Composable
fun AccountItem(
    account: AddAccountEntity,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {  }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFFFD54F), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(painterResource(R.drawable.notfound), contentDescription = null, tint = Color.Black)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(account.accountName, style = MaterialTheme.typography.bodyLarge)
            if (account.note.isNotBlank()) {
                Text(account.note, style = MaterialTheme.typography.bodySmall)
            }
        }

        Text(
            text = if (account.amount < 0) "( I owe ) ${account.amount}"
            else account.amount.toString(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
