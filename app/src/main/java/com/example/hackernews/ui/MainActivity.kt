package com.example.hackernews.ui

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.hackernews.databinding.ActivityMainBinding
import com.example.hackernews.network.RxBus
import com.example.hackernews.network.rxmessages.ReloadTopStories
import com.example.hackernews.utils.connectivity.NetworkCallback
import com.example.hackernews.utils.connectivity.NetworkConnection

class MainActivity : AppCompatActivity() {

    private val networkConnection = NetworkConnection
    lateinit var views: ActivityMainBinding

    private val networkCallback = object : NetworkCallback(networkConnection) {
        override fun onAvailable(network: Network) {
            runOnUiThread {
                views.viewNoInternet.isVisible = false
            }
            RxBus.publish(ReloadTopStories(true))
            super.onAvailable(network)
        }

        override fun onLost(network: Network) {
            runOnUiThread {
                views.viewNoInternet.isVisible = true
            }
            super.onLost(network)
        }
    }

    private fun registerNetworkCallback() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            networkCallback
        )
    }

    private fun unregisterNetworkCallback() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        views = ActivityMainBinding.inflate(layoutInflater)
        setContentView(views.root)
        registerNetworkCallback()
    }

    override fun onDestroy() {
        unregisterNetworkCallback()
        super.onDestroy()
    }
}