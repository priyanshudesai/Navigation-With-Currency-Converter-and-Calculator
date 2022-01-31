package com.pd.currencyconverter.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket


sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}

class NetworkStatusHelper(context: Context) {

    val validateNetworkConnections: ArrayList<Network> = ArrayList()
    var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback
    private var networkState: MutableLiveData<NetworkStatus> = MutableLiveData()

    fun announceStatus() {
        if (validateNetworkConnections.isNotEmpty()) {
            networkState.postValue(NetworkStatus.Available)
        } else {
            networkState.postValue(NetworkStatus.Unavailable)
        }
    }

    fun registerNetworkCallBack(): MutableLiveData<NetworkStatus> {
        connectivityManagerCallback = getConnectivityManagerCallback()
        val networkRequest = NetworkRequest
            .Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)
        announceStatus()
        return networkState
    }

    private fun getConnectivityManagerCallback() =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                val networkCapability = connectivityManager.getNetworkCapabilities(network)
                val hasNetworkConnection =
                    networkCapability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        ?: false
                if (hasNetworkConnection) {
                    determineInternetAccess(network)
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                validateNetworkConnections.clear()
                announceStatus()
            }

//            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
//                super.onCapabilitiesChanged(network, networkCapabilities)
//                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
//                    determineInternetAccess(network)
//                } else {
//                    valideNetworkConnections.clear()
//                }
//                announceStatus()
//            }
        }


    private fun determineInternetAccess(network: Network) {
        CoroutineScope(Dispatchers.IO).launch {
            if (InternetAvailability.check()) {
                withContext(Dispatchers.Main) {
                    validateNetworkConnections.add(network)
                    announceStatus()
                }
            }
        }
    }

//    override fun onActive() {
//        super.onActive()
//        Log.e("TAG", "onActive:")
//        connectivityManagerCallback = getConnectivityManagerCallback()
//        val networkRequest = NetworkRequest
//            .Builder()
//            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//            .build()
//        connectivityManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)
//
//    }
//
//    override fun onInactive() {
//        Log.e("TAG", "onInactive:")
//        var connectivityManager: ConnectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
//
//
//        super.onInactive()
//    }

}

object InternetAvailability {

    fun check(): Boolean {
        return try {
            val socket = Socket()
            socket.connect(InetSocketAddress("8.8.8.8", 53))
            socket.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}