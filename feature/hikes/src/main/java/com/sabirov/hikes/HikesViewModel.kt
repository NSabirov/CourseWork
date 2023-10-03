package com.sabirov.hikes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sabirov.core_api.entities.hike.Hike
import com.sabirov.core_api.interactors.HikesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HikesViewModel @Inject constructor(
    private val hikesInteractor: HikesInteractor
) : ViewModel() {

    private val _loadingState = MutableStateFlow(true)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _hikes = MutableLiveData<List<Hike>>()
    val hikes: LiveData<List<Hike>> = _hikes

    fun refresh() {
        viewModelScope.launch {
            hikesInteractor.getHikes()
                .onStart {
                    _loadingState.value = true
                }
                .catch {
                    _loadingState.value = false
                }
                .collect {
                    _loadingState.value = false
                    _hikes.value = it
                }
        }
    }
}