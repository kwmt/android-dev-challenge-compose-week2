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
    fun start(time: Int, updateTimer: (Int) -> Unit, onCompleted: (String?) -> Unit) {
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
                RESULT_DONE
            }
            Log.d("tag", "$result")
            onCompleted(result)
        }
    }

    fun reset() {
        job?.cancel()
    }

    companion object {
        private const val OFFSET: Long = 500
        const val SECOND: Long = 1000
        const val RESULT_DONE = "Done"
    }
}
