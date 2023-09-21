package com.sabirov.camps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.example.core.visible
import com.example.core_api.dto.Camping
import com.sabirov.CampingsNavigation
import com.sabirov.home.databinding.FrCampsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class CampsFragment : Fragment() {

    private var _binding: FrCampsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CampsViewModel by viewModels()

    @Inject
    lateinit var navigation: CampingsNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FrCampsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnNext.setOnClickListener {
                navigation.navigateToCampInfo()
            }
            appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                appBarLayout?.totalScrollRange?.let {
                    val alpha = verticalOffset.toFloat() / it + 1f
                    searchView.alpha = alpha
                    searchView.visible(alpha > 0f)
                    ivAvatar.alpha = alpha
                    ivAvatar.visible(alpha > 0)
                }
                colToolbar.title = if (
                    (appBarLayout?.totalScrollRange?.let { abs(verticalOffset).minus(it) }) != 0
                ) {
                    "Hello!\nSurname Name"
                } else {
                    "Surname Name"
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.apply {
            addCamping(Camping(0, "fasrewfs"))
            viewModelScope.launch {
                campings.observe(viewLifecycleOwner){
                    println()
                }
            }
        }
    }
}