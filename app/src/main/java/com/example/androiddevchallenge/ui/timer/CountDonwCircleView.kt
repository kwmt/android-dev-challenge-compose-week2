package com.example.androiddevchallenge.ui.timer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.TimerState
import com.example.androiddevchallenge.TimerViewModel
import com.example.androiddevchallenge.ui.timer.CountDownCircle.Padding
import com.example.androiddevchallenge.datasource.CountDownTimer.Companion.SECOND

object CountDownCircle {
    val Padding = 16.dp
    val CircleSize = 300.dp
}

@Composable
fun CountDownCircle(viewModel: TimerViewModel, content: @Composable ColumnScope.() -> Unit) {
    val timerState by viewModel.timerState.collectAsState()
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
        BoxWithConstraints {
            val boxWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
            val boxHeight = with(LocalDensity.current) { constraints.maxHeight.toDp() / 2 }
            Canvas(modifier = Modifier
                .size(boxWidth, boxWidth)
                .padding(Padding)
                .fillMaxWidth(), onDraw = {
                drawArc(
                    Color.Red,
                    -90f,
                    degree,
                    useCenter = false,
                    size = Size(
                        (boxWidth - Padding * 2).toPx(),
                        (boxWidth - Padding * 2).toPx()
                    ),
//            topLeft = Offset(60f, 60f),
                    style = Stroke(10f),
                )
            })

            Column(
                modifier = Modifier.size(boxWidth, boxWidth),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ColumnScope.content()
            }
        }


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