package com.boryanz.upszakoni.ui.navigation.destinations

import com.boryanz.upszakoni.data.local.database.model.owneditem.ItemCategory
import kotlinx.serialization.Serializable

@Serializable
data object LawsDestination

@Serializable
data object CrimesDestination

@Serializable
data object OffensesDestination

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
data object ArchivedLawsDestination

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
  val category: ItemCategory
)
