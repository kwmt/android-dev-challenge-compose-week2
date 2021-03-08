package com.example.androiddevchallenge.ui.timer

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.androiddevchallenge.TimerViewModel
import com.example.androiddevchallenge.TimerViewState

@Composable
fun TimerScreen(viewModel: TimerViewModel) {
    val timerViewState by viewModel.timerViewState.collectAsState()
    CountDownCircle(viewModel) {
        Crossfade(targetState = timerViewState) { timerViewState ->
            when (timerViewState) {
                TimerViewState.TimerSet -> TimerSetView(viewModel)
                is TimerViewState.CountDownTimer -> CountDownTimerView(viewModel = viewModel)
            }
        }
    }
}

