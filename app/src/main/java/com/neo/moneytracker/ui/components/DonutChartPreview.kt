package com.neo.moneytracker.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.utils.listOfColors

@Composable
fun DonutChartPreview(data: List<TransactionEntity>) {

    val color = listOfColors()
    var total = 0

    val pair = mutableListOf<Pair<String, Int>>()
    for (i in data){
        total += i.amount.toInt()
        pair.add(Pair(i.type, i.amount.toInt()))

    }
    Row {

        Log.d("HELLO_DATA_CHART_pre", pair.toString())

        DonutChartWithTotal(
            data1 = pair,
            modifier = Modifier
                .size(200.dp)
                .padding(1.dp)
        )


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
                        item.category,
                        modifier = Modifier.weight(1f)
                            .padding(start = 5.dp)
                    )

                    Text(
                        text = String.format("%.2f", (item.amount.toInt() * 100f) / total) + " %"
                    )
                }
            }
        }

    }

    HorizontalDivider()

    Column(modifier = Modifier.padding(16.dp)) {
        for ((index, item) in data.withIndex()) {
            ExpenseItem(
                item.category,
                item.amount.toInt(),
                (item.amount.toInt() * 100f) / total,
                color.get(index),
                Icons.Default.ShoppingCart
            )
        }
    }

}


@Composable
fun ExpenseItem(title: String, amount: Int, percent: Float, color: Color, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = title,
            tint = color,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title)
            LinearProgressIndicator(
                progress = percent / 100f,
                color = Color(0xFFFFD700),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Text("$amount")
    }

    HorizontalDivider(
        modifier = Modifier.padding(vertical = 8.dp)
    )
}