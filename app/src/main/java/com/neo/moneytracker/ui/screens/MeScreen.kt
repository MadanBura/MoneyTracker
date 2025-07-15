package com.neo.moneytracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.moneytracker.ui.components.RatingDialogComp

@Composable
fun MeScreen() {

    Box{
        Card(Modifier
                .fillMaxWidth()
                .height(130.dp)
            ,
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = 20.dp,
                bottomEnd = 20.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFCA28)
                )
        ) { }
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {
            TopSection()
            OptionList()
        }
    }
}


@Composable
fun TopSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                tint = Color.Gray,
                modifier = Modifier
                    .size(64.dp)
                    .background(color = Color(0xFFFFFFFF), shape = CircleShape) // Light gray background

            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("Sign In", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Sign in, more exciting!", fontSize = 14.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .clickable {},
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Premium",
                tint = Color(0xFFFFC107),
                modifier = Modifier.padding(16.dp)
            )

            Text(
                "Premium Member",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Forward",
                tint = Color(0xFF8D8D8D),
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}

@Composable
fun OptionList() {
    val options = listOf(
        "Recommend to friends" to Icons.Outlined.ThumbUp,
        "Rate the app" to Icons.Outlined.Edit,
        "Block Ads" to Icons.Outlined.Block,
        "Settings" to Icons.Outlined.Settings
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
    ) {
        var showDialog by remember{
            mutableStateOf(false)
        }
        options.forEachIndexed {index, (label, icon) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        when(index){
                            1 -> showDialog = true
                            2 -> {}
                            3 -> {}
                            4 -> {}
                        }
                    }
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, contentDescription = label, tint = Color(0xFFFFC107))
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = label, fontSize = 16.sp)

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Forward",
                    tint = Color(0xFF8D8D8D),
                    modifier = Modifier.padding(end = 5.dp)
                )
            }

            if (index != options.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier
                        .width(200.dp)
                        .background(Color(0xFFD7D5D5))
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        if(showDialog){
            RatingDialogComp(onDismiss = {
                showDialog = false
            })
        }
    }
}