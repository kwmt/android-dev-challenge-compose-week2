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
    private var countDownTimer: TimerViewState.CountDownTimer? = null
    var minutes: Int = 0
    var seconds: Int = 0
    private val timer: CountDownTimer = CountDownTimer(coroutineScope = viewModelScope)
    private val _timerState = MutableStateFlow(TimerState.Stop)
    val timerState: StateFlow<TimerState> = _timerState
    private val _currentTime = MutableStateFlow<Int>(0)
    val currentTime: StateFlow<Int> = _currentTime
    private val _currentAngleDegree = MutableStateFlow(currentAngleDegree(_currentTime.value))
    val currentAngleDegree: StateFlow<Double> = _currentAngleDegree
    private val _timerScreenViewState = MutableStateFlow<TimerViewState>(TimerViewState.TimerSet)
    val timerScreenViewState: StateFlow<TimerViewState> = _timerScreenViewState

    /**
     * start
     */
    fun start() {
        val countDownTimer = TimerViewState.CountDownTimer(0, minutes, seconds)
        this.countDownTimer = countDownTimer
        _timerScreenViewState.value = countDownTimer
        _timerState.value = TimerState.Start
        _currentAngleDegree.value = currentAngleDegree(countDownTimer.time )
        _currentTime.value = countDownTimer.time
        timer.start(
            time = countDownTimer.time,
            updateTimer = { currentTime ->
                val angle = currentAngleDegree(currentTime - 1)
                Log.d("tag", "angle = $angle $currentTime, ${timer.startTimeSecond}")
                _currentAngleDegree.value = angle
                _currentTime.value = currentTime
            }) {
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