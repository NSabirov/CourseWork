package com.sabirov.verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sabirov.AuthNavigation
import com.sabirov.authorization.databinding.FrVerificationBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VerificationFragment: Fragment() {
    private var _binding: FrVerificationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VerificationViewModel by viewModels()

    @Inject
    lateinit var navigation: AuthNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FrVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnNext.setOnClickListener {
                navigation.navigateToMain()
            }
        }
    }
}