package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.domain.remoteconfig.FirebaseRemoteConfig
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeRemoteConfigRepository : FirebaseRemoteConfig {

  override val remoteConfigState: StateFlow<RemoteConfig>
    get() = MutableStateFlow(

      RemoteConfig(
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