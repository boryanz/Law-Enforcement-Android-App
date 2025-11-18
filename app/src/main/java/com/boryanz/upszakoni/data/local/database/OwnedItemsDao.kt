package com.boryanz.upszakoni.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.boryanz.upszakoni.data.local.database.model.owneditem.OwnedItem
import kotlinx.coroutines.flow.Flow

@Dao
interface OwnedItemsDao {

  @Transaction
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertOwnedItem(item: OwnedItem)

  @Query("SELECT * FROM owned_item")
  fun getAllOwnedItems(): Flow<List<OwnedItem>>

  @Query("DELETE FROM owned_item WHERE name = :itemName")
  fun deleteOwnedItem(itemName: String)

}