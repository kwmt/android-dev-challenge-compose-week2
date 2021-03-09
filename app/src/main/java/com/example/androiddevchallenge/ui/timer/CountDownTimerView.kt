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

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.TimerViewModel
import java.util.concurrent.TimeUnit

@Composable
fun CountDownTimerView(viewModel: TimerViewModel, modifier: Modifier) {
    val currentTime by viewModel.currentTime.collectAsState()

    Box(
        modifier = modifier
    ) {
        Crossfade(targetState = currentTime, modifier = Modifier.align(Alignment.Center)) {
            Column {
                Text(
                    text = it.fromMinutesToHHmm(),
                    fontSize = 32.sp
                )
            }
        }
    }
}

private fun Int.fromMinutesToHHmm(): String {
    val hours = TimeUnit.MINUTES.toHours(toLong())
    val remainMinutes = this - TimeUnit.HOURS.toMinutes(hours)
    return String.format("%02d:%02d", hours, remainMinutes)
}
