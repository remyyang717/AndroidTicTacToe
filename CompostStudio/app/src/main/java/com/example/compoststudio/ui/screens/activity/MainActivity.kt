package com.example.compoststudio.ui.screens.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.compoststudio.ui.theme.CompostStudioTheme
import com.example.compoststudio.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompostStudioTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)
            }
        }
    }
}
