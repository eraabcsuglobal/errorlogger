package com.example.errorlogger

import android.app.Application
import android.util.Log
import timber.log.Timber

class TestApp: Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Timber based on the build type
        if (BuildConfig.DEBUG) {
            // Custom DebugTree that includes line number in the log
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return "${super.createStackElementTag(element)}:${element.lineNumber}"
                }
            })
        } else {
            // Plant a tree for release logging
            Timber.plant(ReleaseTree())
        }
    }
}


// custom tree for release logging
class ReleaseTree : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return false;
        }
        return true;
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == android.util.Log.ERROR || priority == android.util.Log.WARN) {
            // Logs errors and warnings to a remote server
        }
    }
}