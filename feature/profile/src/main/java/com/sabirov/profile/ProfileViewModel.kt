package com.sabirov.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sabirov.core_api.entities.auth.User
import com.sabirov.core_api.interactors.CommonInteractor
import com.sabirov.core_impl.network.Error
import com.sabirov.core_impl.network.Loading
import com.sabirov.core_impl.network.RequestState
import com.sabirov.core_impl.network.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val interactor: CommonInteractor
): ViewModel() {

    private val _loadingState = MutableStateFlow<RequestState?>(null)
    val loadingState: StateFlow<RequestState?> = _loadingState

    private val _isUploaded = MutableStateFlow(false)
    val isUploaded: StateFlow<Boolean> = _isUploaded


    fun getProfile(userId: Number){
        viewModelScope.launch {
            interactor.getProfile(userId)
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

    fun updateProfile(profileId: Number, user: User){
        viewModelScope.launch {
            interactor.updateProfile(profileId, user)
                .catch {
                    _loadingState.value = Error(it.message)
                }
                .collect{
                    println()
                }
        }
    }

    fun uploadAvatar(profileId: Number, file: File){
        viewModelScope.launch {
            interactor.uploadPhoto(profileId, file)
                .catch {
                }
                .collect{
                    _isUploaded.value = true
                }
        }
    }
}