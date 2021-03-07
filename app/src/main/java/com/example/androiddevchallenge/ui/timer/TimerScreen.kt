package com.example.androiddevchallenge.ui.timer

import androidx.compose.runtime.Composable
import com.example.androiddevchallenge.TimerViewModel

@Composable
fun TimerScreen(viewModel: TimerViewModel) {
    CountDownCircle(viewModel) {
        CountDownTimerView(viewModel)
    }
}

