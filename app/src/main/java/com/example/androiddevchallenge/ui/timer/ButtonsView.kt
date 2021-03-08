package com.example.androiddevchallenge.ui.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.TimerState
import com.example.androiddevchallenge.TimerViewModel

@Composable
fun ButtonsView(viewModel: TimerViewModel, modifier: Modifier = Modifier) {
    val timerState by viewModel.timerState.collectAsState()

    Row(
        modifier = modifier.fillMaxWidth().padding(start = CountDownCircle.Padding, end = CountDownCircle.Padding, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                when (timerState) {
                    TimerState.Start -> viewModel.reset()
                    TimerState.Stop -> viewModel.start()
                }
            }) {
            Text(
                when (timerState) {
                    TimerState.Start -> "ストップ"
                    TimerState.Stop -> "キャンセル"
                }
            )
        }

        Button(

            onClick = {
                when (timerState) {
                    TimerState.Start -> viewModel.reset()
                    TimerState.Stop -> viewModel.start()
                }
            }) {
            Text(
                when (timerState) {
                    TimerState.Start -> "ストップ"
                    TimerState.Stop -> "開始"
                }
            )
        }
    }
}