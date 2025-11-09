package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.domain.remoteconfig.FirebaseRemoteConfig
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeRemoteConfigRepository(private val remoteConfigData: RemoteConfig? = null) :
  FirebaseRemoteConfig {

  override val remoteConfigState: StateFlow<RemoteConfig>
    get() = MutableStateFlow(
      remoteConfigData ?: RemoteConfig(
        isAppUpdateAvailable = false,
        shouldBackportOvertimeTracking = false,
        greetingMessage = "",
        usefulInformations = "",
        nonWorkingDays = ""
      )
    )

  override fun init() {
    TODO("Not yet implemented")
  }
}