package com.sabirov.authorization

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.sabirov.authorization.databinding.FrAuthHostBinding
import com.sabirov.authorization.databinding.FrAuthorizationBinding

class AuthorizationFragment : Fragment() {

    private var _binding: FrAuthorizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FrAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnAuth.setOnClickListener {
                findNavController().navigate(R.id.action_auth_to_main, bundleOf("MAIN" to "main"))
            }
        }
    }
}