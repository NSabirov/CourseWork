package com.sabirov.core_api.interactors

import com.sabirov.core_api.entities.camping.Camping
import kotlinx.coroutines.flow.Flow

interface CampingsInteractor {

    suspend fun getCampings(): Flow<List<Camping>>

    suspend fun getCampingById(campingId: Number): Flow<Camping>
}