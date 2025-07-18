package com.neo.moneytracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MonthlyStatsCard(expenseAmount: Double, incomeAmount: Double) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Monthly Statistics", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text("Jul", fontWeight = FontWeight.Bold, modifier = Modifier.width(40.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Row {
                        Text("Expenses", color = Color.Gray, modifier = Modifier.width(80.dp).weight(1f))
                        Text("Income", color = Color.Gray, modifier = Modifier.width(80.dp).weight(1f))
                        Text("Balance", color = Color.Gray, modifier = Modifier.width(80.dp).weight(1f))
                    }
                    Row {
                        Text("$expenseAmount", modifier = Modifier.width(80.dp).weight(1f))
                        Text("$incomeAmount", modifier = Modifier.width(80.dp).weight(1f))
                        Text("${incomeAmount - expenseAmount}", modifier = Modifier.width(80.dp).weight(1f))
                    }
                }
            }
        }
    }
}
