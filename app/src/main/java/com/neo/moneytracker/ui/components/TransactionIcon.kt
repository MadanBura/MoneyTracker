package com.neo.moneytracker.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.neo.moneytracker.domain.model.Transaction
import kotlin.random.Random

@Composable
fun TransactionIcon(transaction: Transaction) {
    val randomColor = Color(
        Random.nextFloat(),
        Random.nextFloat(),
        Random.nextFloat()
    )

    Log.d("HELLO", transaction.toString())

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
            painter = painterResource(transaction.iconRes),
            contentDescription = "",
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
    }
}