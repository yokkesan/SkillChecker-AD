package com.example.skillcheckerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.skillcheckerapp.network.RetrofitClient
import com.example.skillcheckerapp.ui.dashboard.DashboardScreen
import com.example.skillcheckerapp.ui.login.LoginScreen
import com.example.skillcheckerapp.ui.theme.SkillCheckerAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        RetrofitClient.initialize(
            applicationContext
        )

        enableEdgeToEdge()

        setContent {
            SkillCheckerAppTheme {
                var isLoggedIn by mutableStateOf(
                    RetrofitClient
                        .getTokenManager()
                        .hasToken()
                )

                if (
                    isLoggedIn
                ) {
                    DashboardScreen()
                } else {
                    LoginScreen(
                        onLoginSuccess = {
                            isLoggedIn = true
                        }
                    )
                }
            }
        }
    }
}