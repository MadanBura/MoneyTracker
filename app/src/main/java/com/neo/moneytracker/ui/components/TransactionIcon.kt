package com.neo.moneytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.neo.moneytracker.domain.model.Transaction
import kotlin.random.Random
import com.neo.moneytracker.R

@Composable
fun TransactionIcon(transaction: Transaction) {
    val randomColor = Color(
        Random.nextFloat(),
        Random.nextFloat(),
        Random.nextFloat()
    )

    val lightColor = randomColor.copy(
        red = randomColor.red + 0.2f,
        green = randomColor.green + 0.2f,
        blue = randomColor.blue + 0.2f
    ).takeIf { it.red <= 1f && it.green <= 1f && it.blue <= 1f } ?: randomColor


    Box(
        modifier = Modifier
            .size(40.dp)
            .background(lightColor, CircleShape)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = if (transaction.iconRes != null) painterResource(transaction.iconRes) else painterResource(R.drawable.notfound),
            contentDescription = transaction.category,
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
    }
}
