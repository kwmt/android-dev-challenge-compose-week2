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

import android.graphics.pdf.PdfDocument
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.TimerViewModel
import com.example.androiddevchallenge.TimerViewState
import com.example.androiddevchallenge.ui.timer.ButtonsView.CIRCLE_SIZE
import com.example.androiddevchallenge.ui.timer.CountDownCircle.Padding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@Composable
fun TimerScreen(viewModel: TimerViewModel) {
    val timerViewState by viewModel.timerScreenViewState.collectAsState()
    val isCompleted by viewModel.isCompleted.collectAsState(initial = false)

    if (isCompleted) {
        Toast.makeText(LocalContext.current, stringResource(R.string.finsih), Toast.LENGTH_SHORT)
            .show()
    }

    BoxWithConstraints(modifier = Modifier.statusBarsPadding()) {
        val boxWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
        val boxHeight = with(LocalDensity.current) { constraints.maxHeight.toDp() / 2 }
        val modifier = Modifier.size(boxWidth + CIRCLE_SIZE / 2)
        Box(modifier = modifier) {
            Crossfade(targetState = timerViewState) { timerViewState ->
                when (timerViewState) {
                    TimerViewState.TimerSet -> TimerSetView(viewModel, modifier)
                    is TimerViewState.CountDownTimer -> {
                        CountDownTimerView(viewModel, modifier.offset(y = - (Padding * 2)))
                        CountDownCircle(viewModel, modifier, (boxWidth - Padding * 2))
                    }
                }
            }
        }
        ButtonsView(
            viewModel = viewModel,
            modifier = Modifier.offset(y = boxWidth - CIRCLE_SIZE / 2)
        )
    }
}
