package com.neo.moneytracker.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScrollableRow(allItems: List<String>) {
    var visibleCount by remember { mutableStateOf(10) }

    val visibleItems = allItems.take(visibleCount)
    val reversedItems = visibleItems.reversed()

    val listState = rememberLazyListState()
    var hasScrolledInitially by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasScrolledInitially) {
            listState.scrollToItem(reversedItems.lastIndex)
            hasScrolledInitially = true
        }
    }
    var selectedIndex by remember { mutableStateOf(-1) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = listState
    ) {
        if (visibleCount < allItems.size) {
            item {
                Box(
                    modifier = Modifier
                        .clickable {
                            visibleCount = minOf(visibleCount + 6, allItems.size)
                        }
                        .padding(end = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Load More",
                        tint = Color.Gray,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }

        itemsIndexed(reversedItems) { index, label ->
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clickable { selectedIndex = index }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            )
        }
    }
}
