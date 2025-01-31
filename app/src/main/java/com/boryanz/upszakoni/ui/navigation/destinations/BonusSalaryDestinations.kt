package com.boryanz.upszakoni.ui.navigation.destinations

import kotlinx.serialization.Serializable

@Serializable
data object ParametersDestination

@Serializable
data object BonusSalaryDashboardDestination

@Serializable
data class OvertimeInputDestination(val month: String)