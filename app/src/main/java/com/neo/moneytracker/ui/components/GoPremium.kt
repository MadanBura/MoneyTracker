package com.neo.moneytracker.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.moneytracker.ui.theme.YellowOrange

@Composable
fun GoPremium(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Cancel button at the top
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.WorkspacePremium,
                tint = YellowOrange,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Go Premium",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(10.dp)
            )
        }


        Card(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            colors = CardDefaults.cardColors(Color.White),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            PremiumBenefit("Unlock everything and ad-free")
            PremiumBenefit("More beautiful themes, customizable")
            PremiumBenefit("Make your life better!")
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Price + Continue Button
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "₹499.00/Yearly   Cancel Anytime",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(48.dp)
            ) {
                Text(text = "Continue", fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Resume purchase",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.clickable {  }
            )
        }

        Spacer(modifier = Modifier.weight(1f))



        // Footer
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "By logging in, you agree to the User Agreement and Privacy Policy",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Text(
                    text = "Terms of Use",
                    fontSize = 12.sp,
                    color = Color(0xFFFFA000),
                    modifier = Modifier.clickable { }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Privacy Policy",
                    fontSize = 12.sp,
                    color = Color(0xFFFFA000),
                    modifier = Modifier.clickable { }
                )
            }
        }

    }
}


