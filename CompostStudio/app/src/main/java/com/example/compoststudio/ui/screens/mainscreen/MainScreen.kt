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
import androidx.navigation.NavController
import com.example.compoststudio.R
import com.example.compoststudio.ui.components.GameRecordsList
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.input.pointer.pointerInput

private const val ANIMATION_DURATION_MS  = 800

@Composable
fun MainScreen(
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        // control show records and hide game pic
        var showRecords by remember { mutableStateOf(false) }

        // control how fast records and game pic visibility toggling


        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(showRecords) {
                    detectTapGestures {
                        // can hide records list and show the game pic
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


                    ToggleableGamePic(showRecords,ANIMATION_DURATION_MS)


                Spacer(modifier = Modifier.weight(0.2f))

                    MainControlButtons({showRecords = it},navController)

                Spacer(modifier = Modifier.weight(0.2f))


                    ToggleableGameRecordList(showRecords,ANIMATION_DURATION_MS,navController)

                val weightAnim by animateFloatAsState(targetValue = if (!showRecords) 1f else 0.2f)
                Spacer(modifier = Modifier.weight(weightAnim))
            }
        }


    }
}

@Composable
fun ToggleableGameRecordList(
    showRecords: Boolean,
    animationDuration: Int,
    navController: NavController
){
    AnimatedVisibility(
        visible = showRecords,
        enter = expandVertically(animationSpec = tween(durationMillis = animationDuration)),
        exit = shrinkVertically(animationSpec = tween(durationMillis = animationDuration))
    ) {
        GameRecordsList(navController = navController)
    }
}

@Composable
fun MainControlButtons(
    onToggleShowRecords :(Boolean) -> Unit,
    navController: NavController
){
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
        onClick = { onToggleShowRecords(true)},
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text("Load a Game")
    }
}

@Composable
fun ToggleableGamePic(
    showRecords: Boolean,
    animationDuration: Int
){
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
}