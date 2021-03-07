package com.example.androiddevchallenge.ui.timer

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.updateTransition
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.TimerViewModel

@Composable
fun CountDownTimerView(viewModel: TimerViewModel) {
    val currentTime by viewModel.currentTime.collectAsState()
    /*val transition = updateTransition(targetState = currentTime)
    val time by transition.animateInt{
        currentTime
    }
*/
    Crossfade(targetState = currentTime) {
        Text(
            text = it.toString(),
            fontSize = 32.sp
        )

    }
}