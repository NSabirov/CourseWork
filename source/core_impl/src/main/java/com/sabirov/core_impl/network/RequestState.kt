package com.sabirov.core_impl.network

sealed interface RequestState

data class Success(val item: Any): RequestState
data class Error(val text: String?): RequestState
object Loading: RequestState
