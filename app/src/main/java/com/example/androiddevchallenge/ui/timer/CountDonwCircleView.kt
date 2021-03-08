package com.example.androiddevchallenge.ui.timer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.TimerViewModel
import com.example.androiddevchallenge.datasource.CountDownTimer.Companion.SECOND
import com.example.androiddevchallenge.ui.timer.CountDownCircle.Padding

object CountDownCircle {
    val Padding = 16.dp
    val CircleSize = 300.dp
}

@Composable
fun CountDownCircle(viewModel: TimerViewModel, modifier: Modifier, circleSize: Dp) {
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
        Canvas(modifier = modifier
            .padding(Padding)
            .fillMaxWidth(), onDraw = {
            drawArc(
                Color.Red,
                -90f,
                degree,
                useCenter = false,
                size = Size(
                    circleSize.toPx(),
                    circleSize.toPx()
                ),
//            topLeft = Offset(60f, 60f),
                style = Stroke(10f),
            )
        })
    }
}

@Preview
@Composable
fun PreviewMyCircle() {
//    CountDownCircle()
}

