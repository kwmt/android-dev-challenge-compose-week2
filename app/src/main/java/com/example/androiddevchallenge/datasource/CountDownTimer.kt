package com.example.androiddevchallenge.datasource

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class CountDownTimer(
    private val coroutineScope: CoroutineScope = GlobalScope,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    var startTime: Long = 10 * SECOND
    val startTimeSecond: Int
        get() = (startTime / SECOND).toInt()
    private var job: Job? = null
    fun start(time: Int, updateTimer: (Int) -> Unit, onCompleted: () -> Unit) {
        this.startTime = time.toLong() * SECOND
        job = coroutineScope.launch(
            dispatcher
        ) {
            val result = withTimeoutOrNull(startTime + OFFSET) {
                var i = (startTime / SECOND).toInt()

                repeat((startTime / SECOND).toInt()) {
                    delay(SECOND)
                    val update = --i
                    updateTimer(update)
                }
                "Done"
            }
            Log.d("tag", "$result")
            onCompleted()
        }
    }

    fun reset() {
        job?.cancel()
    }

    companion object {
        private const val OFFSET: Long = 500
        const val SECOND: Long = 1000
    }
}