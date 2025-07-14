package com.neo.moneytracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.moneytracker.ui.theme.LemonBackground
import com.neo.moneytracker.ui.theme.LemonSecondary
import com.neo.moneytracker.ui.theme.YellowOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Reports",
                            color = Color.Black,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.align(Alignment.Center),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LemonSecondary
                )
            )
        }
    ){
        Column(modifier = Modifier.padding(it)) {
            SimpleTabLayout()
        }
    }

}


@Composable
fun SimpleTabLayout() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Analytics", "Accounts")

    Row(
        modifier = Modifier
            .background(color = LemonSecondary)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(color = Color(0xFFFFEB3B))
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
    ) {
        tabs.forEachIndexed { index, tab ->
            val isSelected = index == selectedTab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(if (isSelected) Color.Black else LemonSecondary)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { selectedTab = index }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSelected) Color(0xFFFFEB3B) else Color.Black
                )
            }
        }
    }
}