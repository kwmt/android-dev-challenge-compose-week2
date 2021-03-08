package com.example.androiddevchallenge.ui.timer

import android.util.Log
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.androiddevchallenge.TimerViewModel

@Composable
fun TimerSetView(viewModel: TimerViewModel, modifier: Modifier = Modifier) {
    viewModel.clearTime()

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        AndroidView({ context ->
            NumberPicker(context).apply {
                this.minValue = 0
                this.maxValue = 59
                this.wrapSelectorWheel = false
                this.setOnValueChangedListener { picker, oldVal, newVal ->
                    Log.d("tag", "$oldVal -> $newVal" )
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
                    Log.d("tag", "$oldVal -> $newVal" )
                    viewModel.seconds = newVal
                }
            }
        })
        Text("秒")

    }
}