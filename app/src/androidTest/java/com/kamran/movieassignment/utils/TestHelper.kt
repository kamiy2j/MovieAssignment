package com.kamran.movieassignment.utils

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.test.platform.app.InstrumentationRegistry
import java.util.Timer
import kotlin.concurrent.schedule

fun ComposeContentTestRule.turnWifiOff() {
    InstrumentationRegistry.getInstrumentation().uiAutomation
        .executeShellCommand("svc wifi disable")
    InstrumentationRegistry.getInstrumentation().uiAutomation
        .executeShellCommand("svc data disable")
    waitUntilTimeout(3000)
}

fun ComposeContentTestRule.turnWifiOn() {
    InstrumentationRegistry.getInstrumentation().uiAutomation
        .executeShellCommand("svc wifi enable")
    InstrumentationRegistry.getInstrumentation().uiAutomation
        .executeShellCommand("svc data enable")
    waitUntilTimeout(3000)
}

fun ComposeContentTestRule.waitUntilTimeout(
    timeoutMillis: Long
) {
    AsyncTimer.start(timeoutMillis)
    this.waitUntil(
        condition = { AsyncTimer.expired },
        timeoutMillis = timeoutMillis + 1000
    )
}

object AsyncTimer {
    var expired = false
    fun start(delay: Long = 1000) {
        expired = false
        Timer().schedule(delay) {
            expired = true
        }
    }
}