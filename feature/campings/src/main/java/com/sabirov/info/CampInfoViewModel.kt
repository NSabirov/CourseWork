package com.sabirov.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sabirov.core_api.interactors.CampingsInteractor
import com.sabirov.core_impl.network.Error
import com.sabirov.core_impl.network.Loading
import com.sabirov.core_impl.network.RequestState
import com.sabirov.core_impl.network.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CampInfoViewModel @Inject constructor(private val campingsInteractor: CampingsInteractor) :
    ViewModel() {
    private val _loadingState = MutableStateFlow<RequestState?>(null)
    val loadingState: StateFlow<RequestState?> = _loadingState
    fun refresh(campId: Number) {
        viewModelScope.launch {
            campingsInteractor.getCampingById(campId)
                .onStart {
                    _loadingState.value = Loading
                }
                .catch {
                    _loadingState.value = Error(it.message)
                }
                .collect{
                    _loadingState.value = Success(it)
                }
        }
    }
}