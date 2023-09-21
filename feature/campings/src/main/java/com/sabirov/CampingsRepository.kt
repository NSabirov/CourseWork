package com.sabirov

import com.example.core_api.database.CampingsDao
import com.example.core_api.dto.Camping
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class CampingsRepository @Inject constructor(
    private val campingsDao: CampingsDao
) {


    fun getCampings() = campingsDao.getAllCampings()

    suspend fun addCamping(camping: Camping) = campingsDao.createCamping(camping)
}