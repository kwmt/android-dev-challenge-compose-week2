package com.example.androiddevchallenge.ui.timer

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.example.androiddevchallenge.TimerViewModel
import com.example.androiddevchallenge.TimerViewState
import com.example.androiddevchallenge.ui.timer.CountDownCircle.Padding

@Composable
fun TimerScreen(viewModel: TimerViewModel) {
    val timerViewState by viewModel.timerViewState.collectAsState()
    Column {
        BoxWithConstraints {
            val boxWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
            val boxHeight = with(LocalDensity.current) { constraints.maxHeight.toDp() / 2 }
            val modifier = Modifier.size(boxWidth)
            Box(modifier = modifier) {
                Crossfade(targetState = timerViewState) { timerViewState ->
                    when (timerViewState) {
                        TimerViewState.TimerSet -> TimerSetView(viewModel, modifier)
                        is TimerViewState.CountDownTimer -> {
                            CountDownTimerView(viewModel, modifier)
                            CountDownCircle(viewModel, modifier, (boxWidth - Padding * 2))
                        }
                    }
                }
            }
        }

        ButtonsView(viewModel = viewModel)
    }
}

