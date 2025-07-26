package com.example.compoststudio.ui.screens.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compoststudio.loginPage.LoginScreen
import com.example.compoststudio.ui.screens.mainscreen.MainScreen
import com.example.compoststudio.ui.screens.tictactoe.TicTacToe
import com.example.compoststudio.ui.theme.CompostStudioTheme
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
