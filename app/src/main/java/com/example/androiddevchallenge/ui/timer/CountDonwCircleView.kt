package com.example.androiddevchallenge.ui.timer

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.TimerViewModel
import com.example.androiddevchallenge.datasource.CountDownTimer.Companion.SECOND
import com.example.androiddevchallenge.ui.timer.CountDownCircle.Padding
import com.example.androiddevchallenge.ui.timer.CountDownCircle.STROKE

object CountDownCircle {
    val Padding = 16.dp
    val CircleSize = 300.dp
    const val STROKE = 20f
}

@Composable
fun CountDownCircle(viewModel: TimerViewModel, modifier: Modifier, circleSize: Dp) {
    val currentAngleDegree by viewModel.currentAngleDegree.collectAsState()
    val transition = updateTransition(targetState = currentAngleDegree)
    val degree by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = SECOND.toInt(), easing = LinearEasing)
        }
    ) {
        Log.d("tag", "currentAngleDegree ${currentAngleDegree.toFloat()}")
        currentAngleDegree.toFloat()
    }
    Box {
        Canvas(modifier = modifier
            .padding(Padding)
//            .background(Color.Blue)
            .fillMaxWidth(), onDraw = {
            drawCircle(
                color = Color(0x8028252C),
                center = Offset(x = circleSize.toPx() / 2, y = circleSize.toPx() / 2),
                radius = size.minDimension / 2,
                style = Stroke(STROKE)
            )
            drawArc(
                Color(0xFFF2A337),
                -90f,
                degree,
                useCenter = false,
                size = Size(
                    circleSize.toPx(),
                    circleSize.toPx()
                ),
//            topLeft = Offset(60f, 60f),
                style = Stroke(STROKE),
//                blendMode = BlendMode.SrcOver
            )
        })
    }
}

@Preview
@Composable
fun PreviewMyCircle() {
//    CountDownCircle()
}

