package com.neo.moneytracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.neo.moneytracker.ui.navigation.Screens
import com.neo.moneytracker.ui.viewmodel.AccountsViewModel

@RequiresApi(Build.VERSION_CODES.Q)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReorderableListScreen(addAccountViewModel: AccountsViewModel, navHostController: NavHostController) {
    val accounts by addAccountViewModel.accounts.collectAsState()
    val state = rememberLazyListState()
    val draggedItemIndex = remember { mutableStateOf<Int?>(null) }
    val dragOffsetY = remember { mutableStateOf(0f) }

    val itemHeightDp = 70.dp
    val itemHeightPx = with(LocalDensity.current) { itemHeightDp.toPx() }

    LazyColumn(
        state = state,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp)
    ) {
        itemsIndexed(
            items = accounts,
            key = { _, item -> item.id }
        ) { index, item ->
            val isDragging = draggedItemIndex.value == index

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .then(
                        if (isDragging) Modifier
                            .shadow(8.dp, shape = MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.surface)
                        else Modifier
                            .background(Color.Transparent)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.DoNotDisturbOn,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.clickable {
                            addAccountViewModel.delAccount(item)
                        }
                    )

                    Icon(
                        imageVector = Icons.Filled.PersonAdd,
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .padding(5.dp)
                    )

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.accountName,
                            modifier = Modifier.padding(start = 10.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = item.note,
                            modifier = Modifier.padding(start = 10.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = null, modifier = Modifier.clickable {
                            val accountId = item.id
                            navHostController.navigate(Screens.editAccount.createRoute(accountId))
                        })
                        Icon(imageVector = Icons.Filled.PushPin, contentDescription = null)
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Drag",
                            modifier = Modifier
                                .pointerInput(index) {
                                    detectDragGesturesAfterLongPress(
                                        onDragStart = {
                                            draggedItemIndex.value = index
                                            dragOffsetY.value = 0f
                                        },
                                        onDragCancel = {
                                            draggedItemIndex.value = null
                                            dragOffsetY.value = 0f
                                        },
                                        onDragEnd = {
                                            draggedItemIndex.value = null
                                            dragOffsetY.value = 0f
                                        },
                                        onDrag = { change, dragAmount ->
                                            change.consume()
                                            dragOffsetY.value += dragAmount.y

                                            val moveBy = (dragOffsetY.value / itemHeightPx).toInt()
                                            val fromIndex = draggedItemIndex.value ?: return@detectDragGesturesAfterLongPress
                                            val toIndex = (fromIndex + moveBy).coerceIn(0, accounts.lastIndex)

                                            if (fromIndex != toIndex) {
                                                addAccountViewModel.reorderAccounts(fromIndex, toIndex)
                                                draggedItemIndex.value = toIndex
                                                dragOffsetY.value -= moveBy * itemHeightPx
                                            }
                                        }
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}
