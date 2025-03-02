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
data class NewOvertimeInputDestination(
    val monthId: Int,
    val monthName: String,
    val dayNumber: Int,
    val isSickDay: Boolean,
    val isPaidLeave: Boolean,
    val totalOvertimeHours: String
)

@Serializable
data class OvertimeMonthlyCalendarDestination(val monthName: String)