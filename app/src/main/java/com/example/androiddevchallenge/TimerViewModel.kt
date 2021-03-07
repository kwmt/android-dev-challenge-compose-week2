package com.example.androiddevchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.ui.timer.CountDownTimer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.PI

class TimerViewModel : ViewModel() {
    private val timer: CountDownTimer = CountDownTimer(coroutineScope = viewModelScope)
    private val _timerState = MutableStateFlow(TimerState.Stop)
    val timerState: StateFlow<TimerState> = _timerState
    private val _currentTime = MutableStateFlow<Long>(timer.startTimeSecond)
    val currentTime: StateFlow<Long> = _currentTime
    private val _currentAngleDegree =
        MutableStateFlow(currentAngleDegree(_currentTime.value))
    val currentAngleDegree: StateFlow<Double> = _currentAngleDegree

    /**
     * start
     */
    fun start() {
        _timerState.value = TimerState.Start
        timer.start(updateTimer = { currentTime ->
            val angle = currentAngleDegree(currentTime)
            Log.d("tag", "angle = $angle $currentTime, ${timer.startTimeSecond}")
            _currentAngleDegree.value = angle
            _currentTime.value = currentTime
        }) {
            _currentTime.value = timer.startTimeSecond
            _timerState.value = TimerState.Stop
        }
    }

    /**
     * reset
     */
    fun reset() {
        _timerState.value = TimerState.Stop
        timer.reset()
    }

    private fun currentAngleDegree(currentTime: Long): Double {
        return Math.toDegrees(((currentTime.toDouble() / timer.startTimeSecond.toDouble()) * 2 * PI))
    }
}

enum class TimerState {
    Start,
    Stop
}