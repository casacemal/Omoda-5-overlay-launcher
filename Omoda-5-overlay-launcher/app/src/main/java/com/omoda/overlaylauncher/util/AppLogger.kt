package com.omoda.overlaylauncher.util

import android.util.Log
import com.omoda.overlaylauncher.BuildConfig

object AppLogger {
    private const val TAG = "OverlayLauncher"

    fun d(message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message)
        }
    }
}