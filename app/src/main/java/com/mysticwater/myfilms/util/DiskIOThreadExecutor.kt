package com.mysticwater.myfilms.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Adapted from Google's Android Architecture project
 * https://github.com/googlesamples/android-architecture
 *
 * Executor that runs a task on a new background thread.
 */
class DiskIOThreadExecutor : Executor {

    private val diskIO = Executors.newSingleThreadExecutor()

    override fun execute(command: Runnable) {
        diskIO.execute(command)
    }
}