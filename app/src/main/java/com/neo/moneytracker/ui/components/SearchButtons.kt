package com.neo.moneytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Preview(showSystemUi = true)
@Composable
fun SearchButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { },
            modifier = Modifier
                .weight(1f),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            )
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh Button"
            )
        }

        Button(
            onClick = { },
            modifier = Modifier
                .weight(1f),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFDFD96),
                contentColor = Color.Black
            )

        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Refresh Button"
            )
        }
    }

}