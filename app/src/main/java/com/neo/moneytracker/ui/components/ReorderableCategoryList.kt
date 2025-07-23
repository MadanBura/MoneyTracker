package com.neo.moneytracker.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReorderableSubCategoryList(
    subcategories: List<Pair<String, Int>>,
    onDelete: (Pair<String, Int>) -> Unit,
    onEdit: (Pair<String, Int>) -> Unit,
    onReorder: (Int, Int) -> Unit
) {
    val state = rememberLazyListState()
    val draggedItemIndex = remember { mutableStateOf<Int?>(null) }
    val dragOffsetY = remember { mutableStateOf(0f) }

    val itemHeightDp = 70.dp
    val itemHeightPx = with(LocalDensity.current) { itemHeightDp.toPx() }

    LazyColumn(
        state = state,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 0.dp)
    ) {
        itemsIndexed(subcategories, key = { _, item -> item.first }) { index, (name, iconRes) ->
            val isDragging = draggedItemIndex.value == index

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
//                    .animateItemPlacement()
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
                        imageVector = Icons.Filled.RemoveCircle,
                        contentDescription = "Delete",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onDelete(Pair(name, iconRes)) }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    GenerateBackgroundIconColor(iconRes)

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )


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
                                        val toIndex = (fromIndex + moveBy).coerceIn(0, subcategories.lastIndex)

                                        if (fromIndex != toIndex) {
                                            onReorder(fromIndex, toIndex)
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
