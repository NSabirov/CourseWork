package com.sabirov.camps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sabirov.core_api.entities.auth.User
import com.sabirov.core_api.entities.camping.Camping
import com.sabirov.core_api.interactors.CampingsInteractor
import com.sabirov.core_api.interactors.CommonInteractor
import com.sabirov.core_impl.SessionKeeper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CampsViewModel @Inject constructor(
    private val sessionKeeper: SessionKeeper,
    private val commonInteractor: CommonInteractor,
    private val campingsInteractor: CampingsInteractor
) : ViewModel() {

    private val _user = MutableLiveData(sessionKeeper.userAccount)
    val user: LiveData<User?> = _user

    private val _loadingState = MutableStateFlow(true)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _campings = MutableLiveData<List<Camping>>()
    val campings: LiveData<List<Camping>> = _campings

    fun refresh() {
        viewModelScope.launch {
            getProfile()
            getCampings()
        }
    }

    private suspend fun getProfile() {
        user.value?.profileId?.let {
            commonInteractor.getProfile(it)
                .onStart {
                    //_loadingState.value = true
                }
                .catch {
                    //_loadingState.value = false
                }
                .collect { user ->
                    _user.value = user
                    //_loadingState.value = false
                }
        }
    }

    private suspend fun getCampings() {
        campingsInteractor.getCampings()
            .onStart {
                _loadingState.value = true
            }
            .catch {
                _loadingState.value = false
            }
            .collect { campings ->
                _campings.value = campings
                _loadingState.value = false
            }

    }
}