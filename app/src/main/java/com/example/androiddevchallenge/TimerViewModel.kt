package com.example.androiddevchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.datasource.CountDownTimer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.threeten.bp.LocalTime
import kotlin.math.PI

class TimerViewModel : ViewModel() {
    var minutes: Int = 0
    var seconds: Int = 0
    private val timer: CountDownTimer = CountDownTimer(coroutineScope = viewModelScope)
    private val _timerState = MutableStateFlow(TimerState.Stop)
    val timerState: StateFlow<TimerState> = _timerState
    private val _currentTime = MutableStateFlow<Int>(timer.startTimeSecond)
    val currentTime: StateFlow<Int> = _currentTime
    private val _currentAngleDegree =
        MutableStateFlow(currentAngleDegree(_currentTime.value))
    val currentAngleDegree: StateFlow<Double> = _currentAngleDegree
    private val _timerViewState = MutableStateFlow<TimerViewState>(TimerViewState.TimerSet)
    val timerViewState: StateFlow<TimerViewState> = _timerViewState

    /**
     * start
     */
    fun start() {
        val c = TimerViewState.CountDownTimer(0, minutes, seconds)
        _timerViewState.value = c
        _timerState.value = TimerState.Start
        timer.start(
            time = c.time,
            updateTimer = { currentTime ->
            val angle = currentAngleDegree(currentTime)
            Log.d("tag", "angle = $angle $currentTime, ${timer.startTimeSecond}")
            _currentAngleDegree.value = angle
            _currentTime.value = currentTime
        }) {
            val angle = currentAngleDegree(timer.startTimeSecond)
            _currentAngleDegree.value = angle
            _currentTime.value = timer.startTimeSecond
            _timerState.value = TimerState.Stop
            _timerViewState.value = TimerViewState.TimerSet
        }
    }

    /**
     * reset
     */
    fun reset() {
        _timerState.value = TimerState.Stop
        _timerViewState.value = TimerViewState.TimerSet
        timer.reset()
    }

    fun clearTime() {
        this.minutes = 0
        this.seconds = 0
    }

    private fun currentAngleDegree(currentTime: Int): Double {
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