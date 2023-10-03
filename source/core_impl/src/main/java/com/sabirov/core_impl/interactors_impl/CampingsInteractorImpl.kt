package com.sabirov.core_impl.interactors_impl

import com.sabirov.core_api.entities.camping.Camping
import com.sabirov.core_api.interactors.CampingsInteractor
import com.sabirov.core_api.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CampingsInteractorImpl(private val api: Api) : CampingsInteractor {
    override suspend fun getCampings(): Flow<List<Camping>> {
        return flow { emit(api.getCampings()) }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCampingById(campingId: Number): Flow<Camping> {
        return flow { emit(api.getCampingById(campingId)) }.flowOn(Dispatchers.IO)
    }
}