package com.example.hackernews.utils.connectivity

interface NetworkConnectivityListener {
    fun onConnected()

    fun onDisconnected()
}