/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.timer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.TimerState
import com.example.androiddevchallenge.TimerViewModel
import com.example.androiddevchallenge.ui.theme.cancelAvailableBackgroundColor
import com.example.androiddevchallenge.ui.theme.cancelAvailableTextColor
import com.example.androiddevchallenge.ui.theme.cancelBackgroundColor
import com.example.androiddevchallenge.ui.theme.cancelTextColor
import com.example.androiddevchallenge.ui.theme.pauseBackgroundColor
import com.example.androiddevchallenge.ui.theme.pauseTextColor
import com.example.androiddevchallenge.ui.theme.startBackgroundColor
import com.example.androiddevchallenge.ui.theme.startTextColor
import com.example.androiddevchallenge.ui.timer.ButtonsView.CIRCLE_SIZE

object ButtonsView {
    val CIRCLE_SIZE = 90.dp
}

@Composable
fun ButtonsView(viewModel: TimerViewModel, modifier: Modifier = Modifier) {
    val timerState by viewModel.timerState.collectAsState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = CountDownCircle.Padding,
                end = CountDownCircle.Padding,
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        StyledButton(
            stringResource(id = R.string.cancel),
            when (timerState) {
                TimerState.Start -> cancelAvailableBackgroundColor
                TimerState.Stop -> cancelBackgroundColor
            },
            when (timerState) {
                TimerState.Start -> cancelAvailableTextColor
                TimerState.Stop -> cancelTextColor
            },
        ) {
            when (timerState) {
                TimerState.Start -> viewModel.reset()
                TimerState.Stop -> Unit
            }
        }

        StyledButton(
            when (timerState) {
                TimerState.Start -> stringResource(id = R.string.pause)
                TimerState.Stop -> stringResource(id = R.string.start)
            },
            when (timerState) {
                TimerState.Start -> pauseBackgroundColor
                TimerState.Stop -> startBackgroundColor
            },
            when (timerState) {
                TimerState.Start -> pauseTextColor
                TimerState.Stop -> startTextColor
            }
        ) {
            when (timerState) {
                TimerState.Start -> viewModel.reset()
                TimerState.Stop -> viewModel.start()
            }
        }
    }
}

@Composable
fun StyledButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    circleSize: Dp = CIRCLE_SIZE,
    onClick: () -> Unit
) {
    Box(modifier = modifier.size(circleSize, circleSize)) {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .clip(CircleShape)
                .clickable(onClick = onClick),
            onDraw = {
                drawCircle(
                    color = backgroundColor,
                    center = Offset(x = circleSize.toPx() / 2, y = circleSize.toPx() / 2),
                    radius = size.minDimension / 2,
                    style = Stroke(15f)
                )
                drawCircle(
                    color = backgroundColor,
                    center = Offset(x = circleSize.toPx() / 2, y = circleSize.toPx() / 2),
                    radius = (size.minDimension / 2) - 15f,
                    style = Fill
                )
            }
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            color = textColor
        )
    }
}

@Preview
@Composable
fun PreviewStyledButton() {
    Surface(color = MaterialTheme.colors.background) {
        StyledButton("test", Color(0xFF132813), Color(0xFF55A551)) {}
    }
}
