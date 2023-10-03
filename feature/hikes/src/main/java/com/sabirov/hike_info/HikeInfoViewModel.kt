package com.sabirov.hike_info

import androidx.lifecycle.ViewModel
import com.sabirov.core_api.interactors.HikesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HikeInfoViewModel @Inject constructor(
    private val hikesInteractor: HikesInteractor
) : ViewModel() {

}