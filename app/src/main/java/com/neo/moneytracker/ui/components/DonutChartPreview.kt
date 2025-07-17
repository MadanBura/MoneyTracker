package com.neo.moneytracker.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    }

}