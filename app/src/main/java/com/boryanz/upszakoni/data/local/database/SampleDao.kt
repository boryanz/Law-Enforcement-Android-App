package com.boryanz.upszakoni.data.local.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.boryanz.upszakoni.data.local.database.model.SampleModel

interface SampleDao {

    @Insert
    suspend fun insertSample(user: SampleModel): Long

    @Query("SELECT * FROM sample")
    suspend fun getAllUsers(): List<SampleModel>

    @Query("SELECT * FROM sample WHERE id = :userId")
    suspend fun getUserById(userId: Long): SampleModel?

    @Update
    suspend fun updateUser(user: SampleModel): Int

    @Delete
    suspend fun deleteUser(user: SampleModel): Int

}