package com.example.core_api.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.core_api.dto.Camping

@Dao
interface CampingsDao {

    @Insert
    suspend fun createCamping(camping: Camping)

    @Query("SELECT * FROM CAMPINGS")
    fun getAllCampings(): LiveData<List<Camping>>

    @Delete
    suspend fun deleteCamping(camping: Camping)
}