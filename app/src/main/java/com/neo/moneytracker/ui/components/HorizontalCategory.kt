package com.neo.moneytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showSystemUi = true)
@Composable
fun HorizontalCategory(){
    val categories = listOf("All","+")

    Row(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)

        ) {
            Column {
                Row {
                    Text(
                        text = "Category",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            items(categories) { category ->
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .background(
                            color = Color(0xFFFFE44C),
                            shape = RoundedCornerShape(32.dp)
                        )
                        .padding(10.dp)
                        .clickable {  }
                )
                {
                    Column {
                        Text(
                            text = category,
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}