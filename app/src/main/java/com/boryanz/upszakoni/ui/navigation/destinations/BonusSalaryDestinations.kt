package com.boryanz.upszakoni.ui.navigation.destinations

import kotlinx.serialization.Serializable

@Serializable
data object ParametersDestination

@Serializable
data object BonusSalaryDashboardDestination

@Serializable
data class OvertimeInputDestination(val month: String)

@Serializable
data class NonWorkingDaysInfoDestination(val nonWorkingDays: String)

@Serializable
data object MigrationProposalDestination

@Serializable
data class NewOvertimeInputDestination(val monthId: String, val dayOfTheMonthId: String)

@Serializable
data class OvertimeMonthlyCalendarDestination(val monthId: String)