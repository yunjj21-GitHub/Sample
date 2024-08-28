package com.yunjung.sample.util

import android.util.Log
import java.util.Locale
import com.yunjung.sample.BuildConfig

object Logger {
    const val TAG = "sample"
    fun d(message: Any?) {
        if (BuildConfig.DEBUG) Log.d(TAG, convertMessage(convertToString(message)))
    }

    fun i(message: Any?) {
        if (BuildConfig.DEBUG) Log.i(TAG, convertMessage(convertToString(message)))
    }

    fun w(message: Any?) {
        if (BuildConfig.DEBUG) Log.w(TAG, convertMessage(convertToString(message)))
    }

    fun e(message: Any?) {
        if (BuildConfig.DEBUG) Log.e(TAG, convertMessage(convertToString(message)))
    }

    private fun convertMessage(message: String): String {
        val element = Thread.currentThread().stackTrace[4]
        val fileName = element.fileName
        return String.format(
            Locale.getDefault(),
            "%s::%s() #%d] %s",
            fileName.substring(0, fileName.indexOf(".")),
            element.methodName,
            element.lineNumber,
            message
        )
    }

    private fun convertToString(obj: Any?): String = when (obj) {
        null -> "NULL"
        is String -> obj.toString()
        else -> obj.runCatching { toString() }.getOrNull() ?: ""
    }
}