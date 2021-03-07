package com.example.androiddevchallenge.ui.timer

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

    val startTimeSecond: Long
        get() = startTime / SECOND
    private var job: Job? = null

    fun start(updateTimer: (Long) -> Unit, onCompleted: () -> Unit) {

        job = coroutineScope.launch(
             dispatcher
        ) {
            val result = withTimeoutOrNull(startTime + OFFSET) {
                var i = startTime / SECOND

                repeat((startTime / SECOND).toInt()) {
                    val update = --i
                    updateTimer(update)
                    Log.d("tag", "I'm sleeping  $update")
                    delay(SECOND)
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