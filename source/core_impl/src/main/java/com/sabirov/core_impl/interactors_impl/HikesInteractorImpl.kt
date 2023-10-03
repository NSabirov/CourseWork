package com.sabirov.core_impl.interactors_impl

import com.sabirov.core_api.entities.hike.Hike
import com.sabirov.core_api.interactors.HikesInteractor
import com.sabirov.core_api.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HikesInteractorImpl(private val api: Api) : HikesInteractor {
    override suspend fun getHikes(): Flow<List<Hike>> {
        return flow { emit(api.getHikes()) }.flowOn(Dispatchers.IO)
    }

    override suspend fun getHikeById(hikeId: Number): Flow<Hike> {
        return flow { emit(api.getHikeById(hikeId)) }.flowOn(Dispatchers.IO)
    }
}