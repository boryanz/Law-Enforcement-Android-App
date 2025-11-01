package com.boryanz.upszakoni.domain.remoteconfig

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val SHOULD_BACKPORT_OVERTIME_TRACKING = "should_backport_overtime_tracking"
private const val IS_APP_UPDATE_AVAILABLE = "is_app_update_available"
private const val GREETING_MESSAGE_STRING_KEY = "greeting_message"
private const val USEFUL_INFORMATIONS = "useful_informations"
private const val NON_WORKING_DAYS = "non_working_days"

class RemoteConfigRepository {

  private val remoteConfig = Firebase.remoteConfig

  private val _remoteConfigState: MutableStateFlow<RemoteConfig> =
    MutableStateFlow(RemoteConfig())
  val remoteConfigState = _remoteConfigState.asStateFlow()

  fun initRemoteConfigs() {
    val configSettings = remoteConfigSettings {
      FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(3600)
    }
    remoteConfig.setConfigSettingsAsync(configSettings)
    remoteConfig.fetch().addOnSuccessListener {
      remoteConfig.activate().addOnSuccessListener {
        _remoteConfigState.update {
          RemoteConfig(
            shouldBackportOvertimeTracking = remoteConfig.getBoolean(
              SHOULD_BACKPORT_OVERTIME_TRACKING
            ),
            isAppUpdateAvailable = remoteConfig.getBoolean(IS_APP_UPDATE_AVAILABLE),
            greetingMessage = remoteConfig.getString(GREETING_MESSAGE_STRING_KEY),
            usefulInformations = remoteConfig.getString(USEFUL_INFORMATIONS),
            nonWorkingDays = remoteConfig.getString(NON_WORKING_DAYS)
          )
        }
      }
    }
  }
}

data class RemoteConfig(
  val isAppUpdateAvailable: Boolean = false,
  val shouldBackportOvertimeTracking: Boolean = false,
  val greetingMessage: String = "",
  val usefulInformations: String = "",
  val nonWorkingDays: String = "",
)