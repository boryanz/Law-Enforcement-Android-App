package com.boryanz.upszakoni.domain.remoteconfig

import kotlinx.coroutines.flow.StateFlow

interface FirebaseRemoteConfig {

  val remoteConfigState: StateFlow<RemoteConfig>

  fun init()
}
