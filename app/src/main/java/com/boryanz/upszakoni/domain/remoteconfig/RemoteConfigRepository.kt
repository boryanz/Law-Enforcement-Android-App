package com.boryanz.upszakoni.domain.remoteconfig

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val IS_APP_UPDATE_AVAILABLE = "is_app_update_available"

class RemoteConfigRepository {

    private val remoteConfig = Firebase.remoteConfig

    private val _remoteConfigState: MutableStateFlow<RemoteConfig> =
        MutableStateFlow(RemoteConfig())
    val remoteConfigState = _remoteConfigState.asStateFlow()

    fun initRemoteConfigs() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetch().addOnSuccessListener {
            remoteConfig.activate().addOnSuccessListener {
                _remoteConfigState.update {
                    RemoteConfig(
                        isAppUpdateAvailable = remoteConfig.getBoolean(IS_APP_UPDATE_AVAILABLE)
                    )
                }
            }
        }
    }
}

data class RemoteConfig(
    val isAppUpdateAvailable: Boolean = false
)