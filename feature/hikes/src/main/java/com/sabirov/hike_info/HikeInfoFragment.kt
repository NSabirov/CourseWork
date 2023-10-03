package com.sabirov.hike_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sabirov.HikesNavigation
import com.sabirov.hikes.databinding.FrHikeInfoBinding
import com.sabirov.hikes.databinding.FrHikesBinding
import com.sabirov.utils.registerOnBackPressedCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HikeInfoFragment : Fragment() {

    private var _binding: FrHikeInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HikeInfoViewModel by viewModels()

    @Inject
    lateinit var navigation: HikesNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FrHikeInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            registerOnBackPressedCallback {
                navigation.onBackPressed(true)
            }
        }
    }
}