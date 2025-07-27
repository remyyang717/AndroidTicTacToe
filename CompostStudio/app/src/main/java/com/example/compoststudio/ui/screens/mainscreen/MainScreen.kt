package com.example.compoststudio.ui.screens.mainscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compoststudio.R
import com.example.compoststudio.ui.components.GameRecordsList
import com.example.compoststudio.ui.screens.tictactoe.TicTacToeViewModel
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: TicTacToeViewModel = hiltViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        var showRecords by remember { mutableStateOf(false) }

        val animationDuration : Int = 800

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(showRecords) {
                    detectTapGestures {
                        if (showRecords) {
                            showRecords = false
                        }
                    }
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text("Tic Tac Toe", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.weight(0.2f))


                AnimatedVisibility(
                    visible = !showRecords,
                    enter = expandVertically(animationSpec = tween(durationMillis = animationDuration)),
                    exit = shrinkVertically(animationSpec = tween(durationMillis = animationDuration))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.game_pic),
                        contentDescription = "Game Logo",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .scale(1.4f)
                    )
                }



                Spacer(modifier = Modifier.weight(0.2f))

                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = { navController.navigate("new_game") },
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp)

                ) {
                    Text("Start a New Game")
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick =
                        {
                            showRecords = true
                        },
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text("Load a Game")
                }
                Spacer(modifier = Modifier.height(16.dp))


                AnimatedVisibility(
                    visible = showRecords,
                    enter = expandVertically(animationSpec = tween(durationMillis = animationDuration)),
                    exit = shrinkVertically(animationSpec = tween(durationMillis = animationDuration))
                ) {
                    GameRecordsList(navController = navController)
                }

                val weightAnim by animateFloatAsState(targetValue = if (!showRecords) 1f else 0.2f)
                Spacer(modifier = Modifier.weight(weightAnim))
            }
        }


    }
}