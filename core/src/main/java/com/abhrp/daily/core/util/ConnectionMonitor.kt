package com.abhrp.daily.core.util

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import javax.inject.Inject

/**
 * A custom LiveData class to monitor the network status of the device. Uses NetworkCallback for Lollipop and above
 */
class ConnectionMonitor @Inject constructor(private val context: Context): LiveData<Boolean>() {
    companion object {
        const val CONNECTION_ACTION = "com.abhrp.daily.NETWORK_ACTION"
    }

    private lateinit var networkReceiver: NetworkReceiver
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private var connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkCallback = NetworkCallback(this)
        } else {
            networkReceiver = NetworkReceiver()
        }
    }

    override fun onActive() {
        super.onActive()
        updateConnection()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val networkRequest = NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).addTransportType(
                NetworkCapabilities.TRANSPORT_WIFI).build()
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        } else {
            context.registerReceiver(networkReceiver, IntentFilter(CONNECTION_ACTION))
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } else {
            context.unregisterReceiver(networkReceiver)
        }
    }

    private fun updateConnection() {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting) {
            postValue(true)
        } else {
            postValue(false)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private inner class NetworkCallback(private val connectionMonitor: ConnectionMonitor): ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            connectionMonitor.postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            connectionMonitor.postValue(false)
        }
    }

    private inner class NetworkReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                val action = intent.action
                if (action != null && action == CONNECTION_ACTION) {
                    updateConnection()
                }
            }
        }
    }
}