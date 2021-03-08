package com.example.androiddevchallenge.ui.timer

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.TimerViewModel
import java.util.concurrent.TimeUnit

@Composable
fun CountDownTimerView(viewModel: TimerViewModel, modifier: Modifier) {
    val currentTime by viewModel.currentTime.collectAsState()

    Box(
        modifier = modifier
    ) {
        Crossfade(targetState = currentTime, modifier = Modifier.align(Alignment.Center)) {
            Column {
                Text(
                    text = it.fromMinutesToHHmm(),
                    fontSize = 32.sp
                )
            }
        }
    }
}

private fun Int.fromMinutesToHHmm(): String {
    val hours = TimeUnit.MINUTES.toHours(toLong())
    val remainMinutes = this - TimeUnit.HOURS.toMinutes(hours)
    return String.format("%02d:%02d", hours, remainMinutes)
}