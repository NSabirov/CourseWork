package com.sabirov.core_api.interactors

import com.sabirov.core_api.entities.camping.Camping
import com.sabirov.core_api.entities.hike.Hike
import kotlinx.coroutines.flow.Flow

interface HikesInteractor {
    suspend fun getHikes(): Flow<List<Hike>>

    suspend fun getHikeById(hikeId: Number): Flow<Hike>
}