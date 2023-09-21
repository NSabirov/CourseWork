package com.sabirov.camps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core_api.dto.Camping
import com.sabirov.CampingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CampsViewModel @Inject constructor(
    private val repository: CampingsRepository
) : ViewModel() {
    val campings: LiveData<List<Camping>> = repository.getCampings()

    init {
    }

    fun addCamping(camping: Camping) {
        viewModelScope.launch {
            repository.addCamping(camping)
            delay(2000)
            repository.addCamping(Camping(0, "qwrqwr"))
        }
    }
}