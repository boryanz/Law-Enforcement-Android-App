package com.boryanz.upszakoni.data.local.database.model.owneditem

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "owned_item")
data class OwnedItem(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  @ColumnInfo("name") val name: String,
  @ColumnInfo("volume") val volume: Int,
  @ColumnInfo("category") val category: ItemCategory,
  @ColumnInfo("date") val date: String,
)

enum class ItemCategory() {
  FIREARM,
  AMMUNITION,
  OTHER
}
