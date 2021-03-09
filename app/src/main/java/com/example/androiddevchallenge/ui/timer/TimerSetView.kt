package com.example.androiddevchallenge.ui.timer

import android.util.Log
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.androiddevchallenge.TimerViewModel
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
            modifier = modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
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
            Text("分")
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
            Text("秒")
        }
    }
}