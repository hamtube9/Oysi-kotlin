package com.oysi.notifycation

import android.app.Application
import com.pushbots.push.Pushbots

class NotificationPush : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Pushbots Library
        Pushbots.sharedInstance().init(this)
    }
}