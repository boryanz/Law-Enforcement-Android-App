package com.boryanz.upszakoni.ui.navigation.destinations

import kotlinx.serialization.Serializable

@Serializable
data object LawsDestination

@Serializable
data object CrimesDestination

@Serializable
data object OffensesOverviewDestination

@Serializable
data class OffensesDetailsDestination(val lawId: String, val title: String)

@Serializable
data object PoliceAuthoritiesDestination

@Serializable
data object GoldenCrimeQuestionsDestination

@Serializable
data object PhoneNumbersDestination

@Serializable
data object PrivacyPolicyDestination

@Serializable
data object PrivacyPolicyAcceptanceDestination

@Serializable
data object InformationScreenDestination

@Serializable
data object PartnersDestination

@Serializable
data object OwnedItemsListScreenDestination

@Serializable
data class OwnedItemScreenDestination(
  val itemId: Int,
  val itemName: String,
  val volume: Int,
  val category: String
)
