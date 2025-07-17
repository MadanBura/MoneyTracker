package com.neo.moneytracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun BackgroundSupportItem(){
    Box(modifier = Modifier
        .fillMaxSize()) {

        Card(modifier = Modifier
            .height(500.dp)
            .fillMaxWidth()
            .background(Color(0xFFFFCA28), shape = RoundedCornerShape(20.dp))) {
        }
    }
}