package com.neo.moneytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.neo.moneytracker.utils.listOfColors

@Composable
fun DonutChartPreview(data: List<Pair<String, Int>>) {

    Row {
        DonutChartWithTotal(
            data = data,
            colors = listOfColors(),
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        )
        var total = 0
        for (i in data){
            total += i.second
        }

        Column {
            for ((index, item) in data.withIndex()) {
                Row(
                    modifier = Modifier.padding(3.dp)
                ) {
                    Box(
                        modifier = Modifier.size(20.dp)
                            .clip(CircleShape)
                            .background(color = listOfColors()[index])
                    ) {  }
                    Text(
                        item.first,
                        modifier = Modifier.weight(1f)
                            .padding(start = 5.dp)
                    )

                    Text(
                        text = String.format("%.2f", (item.second * 100f) / total) + " %"
                    )
                }
            }
        }

    }
}