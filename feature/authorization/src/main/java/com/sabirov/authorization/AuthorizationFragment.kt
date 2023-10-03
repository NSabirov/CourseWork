package com.sabirov.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sabirov.AuthNavigation
import com.sabirov.authorization.databinding.FrAuthorizationBinding
import com.sabirov.core_impl.SessionKeeper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthorizationFragment : Fragment() {
    private var _binding: FrAuthorizationBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var navigation: AuthNavigation

    @Inject
    lateinit var sessionKeeper: SessionKeeper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FrAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (sessionKeeper.userAccount != null) {
            navigation.navigateToMain()
        }
        binding.apply {
            btnLogIn.setOnClickListener {
                navigation.navigateToLogIn()
            }
            btnRegistration.setOnClickListener {
                navigation.navigateToRegistration()
            }
        }
    }
}