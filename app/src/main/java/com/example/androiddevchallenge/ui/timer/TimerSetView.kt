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

import android.util.Log
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.TimerViewModel
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.timer.TimerSetObject.INITIAL_SECOND

object TimerSetObject {
    const val INITIAL_SECOND = 10
}

@Composable
fun TimerSetView(viewModel: TimerViewModel, modifier: Modifier = Modifier) {
    viewModel.clearTime()
    viewModel.seconds = INITIAL_SECOND
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AndroidView({ context ->
                    NumberPicker(context).apply {
                        this.minValue = 0
                        this.maxValue = 23
                        this.wrapSelectorWheel = false
                        this.setOnValueChangedListener { picker, oldVal, newVal ->
                            Log.d("tag", "$oldVal -> $newVal")
                            viewModel.hours = newVal
                        }
                    }
                })
                Text(stringResource(id = R.string.hours))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                AndroidView({ context ->
                    NumberPicker(context).apply {
                        this.minValue = 0
                        this.maxValue = 59
                        this.wrapSelectorWheel = false
                        this.setOnValueChangedListener { picker, oldVal, newVal ->
                            Log.d("tag", "$oldVal -> $newVal")
                            viewModel.minutes = newVal
                        }
                    }
                })
                Text(stringResource(id = R.string.minute))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                AndroidView({ context ->
                    NumberPicker(context).apply {
                        this.minValue = 0
                        this.maxValue = 59
                        this.wrapSelectorWheel = false
                        this.setOnValueChangedListener { picker, oldVal, newVal ->
                            Log.d("tag", "$oldVal -> $newVal")
                            viewModel.seconds = newVal
                        }
                        this.value = INITIAL_SECOND
                    }
                })
                Text(stringResource(id = R.string.second))
            }
        }
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreviewTimerSetView() {
    MyTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            TimerSetView(TimerViewModel(10), Modifier)
        }
    }
}
