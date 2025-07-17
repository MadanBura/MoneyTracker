package com.neo.moneytracker.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.neo.moneytracker.ui.theme.YellowOrange

@Composable
fun RatingBar() {
    var rating by remember { mutableStateOf(0f) }

    Row {
        for (i in 1..5) {
            val isFilled = i <= rating.toInt()
            IconButton(onClick = { rating = i.toFloat() }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = if (isFilled) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Star",
                    tint = if (isFilled) YellowOrange else Color.Gray
                )
            }
        }
    }
}