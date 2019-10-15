package com.abhrp.daily.ui.base

import android.os.Bundle
import androidx.lifecycle.Observer
import com.abhrp.daily.core.util.ConnectionMonitor
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity: DaggerAppCompatActivity() {

    @Inject
    lateinit var connectionMonitor: ConnectionMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observerConnectionStatus()
    }

    private fun observerConnectionStatus() {
        connectionMonitor.observe(this, Observer {
            if (it) {
                online()
            } else {
                offline()
            }
        })
    }

    abstract fun online()
    abstract fun offline()

}