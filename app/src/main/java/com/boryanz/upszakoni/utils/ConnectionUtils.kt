package com.boryanz.upszakoni.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import org.koin.core.context.GlobalContext

object ConnectionUtils {

  /**
   * Checks the network connection.
   *
   * @return true if network connection is available, false otherwise.
   */
  fun hasConnection(): Boolean {
    val context = GlobalContext.get().get<Context>()
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    cm?.let {
      cm.getNetworkCapabilities(cm.activeNetwork)?.run {
        return hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            || hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
      }
    }
    return false
  }
}