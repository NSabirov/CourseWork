package com.sabirov.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sabirov.core_api.entities.auth.LogInBody
import com.sabirov.core_api.entities.auth.RegisterBody
import com.sabirov.core_api.interactors.AuthInteractor
import com.sabirov.core_impl.network.RequestState
import com.sabirov.core_impl.network.Error
import com.sabirov.core_impl.SessionKeeper
import com.sabirov.core_impl.network.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val sessionKeeper: SessionKeeper
) : ViewModel() {

    private val _requestState = MutableStateFlow<RequestState?>(null)

    val requestState: StateFlow<RequestState?> = _requestState

    fun register(registerBody: RegisterBody) {
        viewModelScope.launch {
            authInteractor.register(registerBody)
                .catch {
                    _requestState.value = Error(it.localizedMessage)
                }
                .collect {
                    login(LogInBody(registerBody.email, registerBody.password))
                }
        }
    }

    fun login(logInBody: LogInBody) {
        viewModelScope.launch {
            authInteractor.logIn(logInBody)
                .catch {
                    _requestState.value = Error(it.message)
                }
                .collect {
                    sessionKeeper.setUserAccount(it)
                    _requestState.value = Success(it)
                }
        }
    }
}