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
package com.example.androiddevchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.datasource.CountDownTimer
import com.example.androiddevchallenge.datasource.CountDownTimer.Companion.RESULT_DONE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalTime
import kotlin.math.PI

class TimerViewModel(startTime: Int = 0) : ViewModel() {
    private var countDownTimer: TimerViewState.CountDownTimer? = null
    var hours: Int = 0
    var minutes: Int = 0
    var seconds: Int = 0
    private val timer: CountDownTimer = CountDownTimer(coroutineScope = viewModelScope)
    private val _timerState = MutableStateFlow(TimerState.Stop)
    val timerState: StateFlow<TimerState> = _timerState
    private val _currentTime = MutableStateFlow<Int>(startTime)
    val currentTime: StateFlow<Int> = _currentTime
    private val _currentAngleDegree = MutableStateFlow(currentAngleDegree(_currentTime.value))
    val currentAngleDegree: StateFlow<Double> = _currentAngleDegree
    private val _timerScreenViewState = MutableStateFlow<TimerViewState>(TimerViewState.TimerSet)
    val timerScreenViewState: StateFlow<TimerViewState> = _timerScreenViewState

    private var _result = MutableStateFlow<String?>(null)
    val isCompleted: Flow<Boolean> = _result.map { it == RESULT_DONE }

    /**
     * start
     */
    fun start() {
        val countDownTimer = TimerViewState.CountDownTimer(hours, minutes, seconds)
        this.countDownTimer = countDownTimer
        _timerScreenViewState.value = countDownTimer
        _timerState.value = TimerState.Start
        _currentAngleDegree.value = currentAngleDegree(countDownTimer.time)
        _currentTime.value = countDownTimer.time
        timer.start(
            time = countDownTimer.time,
            updateTimer = { currentTime ->
                val angle = currentAngleDegree(currentTime - 1)
                Log.d("tag", "angle = $angle $currentTime, ${timer.startTimeSecond}")
                _currentAngleDegree.value = angle
                _currentTime.value = currentTime
            }
        ) { result ->
            this._result.value = result
            _timerState.value = TimerState.Stop
            _timerScreenViewState.value = TimerViewState.TimerSet
            val angle = currentAngleDegree(0)
            _currentAngleDegree.value = angle
            _currentTime.value = timer.startTimeSecond
        }
    }

    /**
     * reset
     */
    fun reset() {
        _timerState.value = TimerState.Stop
        _timerScreenViewState.value = TimerViewState.TimerSet
        timer.reset()
    }

    fun clearTime() {
        this.hours = 0
        this.minutes = 0
        this.seconds = 0
    }

    private fun currentAngleDegree(currentTime: Int): Double {
        // 現在の時間 / 開始時間 = 求めたい角度 / 2π
        return Math.toDegrees(((currentTime.toDouble() / timer.startTimeSecond.toDouble()) * 2 * PI))
    }
}

enum class TimerState {
    Start,
    Stop
}

sealed class TimerViewState {
    object TimerSet : TimerViewState()
    data class CountDownTimer(
        private val hours: Int,
        private val minutes: Int,
        private val seconds: Int
    ) : TimerViewState() {
        val time: Int = LocalTime.of(hours, minutes, seconds).toSecondOfDay()
    }
}
