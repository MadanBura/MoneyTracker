package com.neo.moneytracker.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.neo.moneytracker.ui.theme.MoneyTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
         installSplashScreen()
             .setOnExitAnimationListener{ splashScreenView ->
                 splashScreenView.view.animate()
                     .alpha(0f)
                     .setDuration(3000)
                     .withEndAction {
                         splashScreenView.remove()
                     }
                     .start()
             }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            MoneyTrackerTheme {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.systemBars), // 👈 add this
                content = { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        MoneyTracker()
                    }
                }
            )

//            }
        }
    }
}