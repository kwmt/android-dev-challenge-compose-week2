package com.example.androiddevchallenge.ui.timer

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.TimerViewModel
import java.util.concurrent.TimeUnit

@Composable
fun CountDownTimerView(viewModel: TimerViewModel) {
    val currentTime by viewModel.currentTime.collectAsState()
    /*val transition = updateTransition(targetState = currentTime)
    val time by transition.animateInt{
        currentTime
    }
*/
    Crossfade(targetState = currentTime) {
//        LocalTime.of()
        Text(
            text = it.fromMinutesToHHmm(),
            fontSize = 32.sp
        )
    }
}

private fun Int.fromMinutesToHHmm(): String {
    val hours = TimeUnit.MINUTES.toHours(toLong())
    val remainMinutes = this - TimeUnit.HOURS.toMinutes(hours)
    return String.format("%02d:%02d", hours, remainMinutes)
}