package com.example.androiddevchallenge.ui.timer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.TimerState
import com.example.androiddevchallenge.TimerViewModel
import com.example.androiddevchallenge.ui.timer.CountDownCircle.CircleSize
import com.example.androiddevchallenge.ui.timer.CountDownCircle.Padding
import com.example.androiddevchallenge.ui.timer.CountDownTimer.Companion.SECOND

object CountDownCircle {
    val Padding = 16.dp
    val CircleSize = 300.dp
}

@Composable
fun CountDownCircle(viewModel: TimerViewModel) {
    val timerState by viewModel.timerState.collectAsState()
    val currentTimerState by viewModel.currentTime.collectAsState()
    val currentAngleDegree by viewModel.currentAngleDegree.collectAsState()
    val transition = updateTransition(targetState = currentAngleDegree)
    val degree by transition.animateFloat(
        transitionSpec = {
            tween(SECOND.toInt(), easing = LinearEasing)
        }
    ) {
        currentAngleDegree.toFloat()
    }

    Column {
        Canvas(modifier = Modifier
            .padding(Padding)
            .size(CircleSize), onDraw = {
            drawArc(
                Color.Red,
                -90f,
                degree,
                useCenter = false,
                size = Size(CircleSize.toPx(), CircleSize.toPx()),
//            topLeft = Offset(60f, 60f),
                style = Stroke(10f),
            )
        })

        Button(onClick = {

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

@Preview
@Composable
fun PreviewMyCircle() {
//    CountDownCircle()
}