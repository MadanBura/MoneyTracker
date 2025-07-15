package com.neo.moneytracker.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.moneytracker.ui.theme.YellowOrange

@Composable
fun PremiumBenefit(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.padding(12.dp)
    ) {
        Icon(
            Icons.Outlined.CheckCircleOutline,
            contentDescription = "",
            tint = YellowOrange,
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = text,
            modifier = Modifier.padding(5.dp),
            fontSize = 16.sp
        )
    }
}