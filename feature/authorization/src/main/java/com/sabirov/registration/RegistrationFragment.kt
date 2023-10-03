package com.sabirov.registration

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sabirov.utils.isValidEmptyWithAnother
import com.sabirov.core_api.entities.auth.RegisterBody
import com.sabirov.core_impl.network.Error
import com.sabirov.core_impl.network.Loading
import com.sabirov.core_impl.network.Success
import com.sabirov.AuthNavigation
import com.sabirov.resources.R
import com.sabirov.authorization.databinding.FrRegistrationBinding
import com.sabirov.utils.registerOnBackPressedCallback
import com.sabirov.utils.setShowProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private var _binding: FrRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistrationViewModel by viewModels()

    @Inject
    lateinit var navigation: AuthNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FrRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            registerOnBackPressedCallback {
                navigation.onBackPressed(false)
            }
            toolbar.setNavigationOnClickListener {
                navigation.onBackPressed(false)
            }

            etEmail.doOnTextChanged { text, _, _, _ ->

                if (!Patterns.EMAIL_ADDRESS.matcher(text.toString())
                        .matches() && text.toString().isNotEmpty()
                ) {
                    tilEmail.error = getString(com.sabirov.resources.R.string.wrong_email)
                } else {
                    tilEmail.isErrorEnabled = false
                }
                btnRegister.isEnabled = etEmail.isValidEmptyWithAnother(etPassword, etRePassword)
            }

            etPassword.doOnTextChanged { _, _, _, _ ->
                btnRegister.isEnabled = etPassword.isValidEmptyWithAnother(etEmail, etRePassword)
            }

            etRePassword.doOnTextChanged { _, _, _, _ ->
                btnRegister.isEnabled = etRePassword.isValidEmptyWithAnother(etEmail, etPassword)
            }

            btnRegister.setOnClickListener {
                viewModel.register(
                    RegisterBody(
                        etEmail.text.toString(),
                        etPassword.text.toString(), etRePassword.text.toString()
                    )
                )
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.requestState.collect {
                        when (it) {
                            is Error -> {
                                btnRegister.setShowProgress(false)
                                btnRegister.text = getString(R.string.title_registration)
                                Toast.makeText(requireContext(), it.text, Toast.LENGTH_SHORT).show()
                            }

                            is Success -> {
                                btnRegister.setShowProgress(false)
                                btnRegister.text = getString(R.string.title_registration)
                                navigation.navigateToMain()
                            }

                            Loading -> {
                                btnRegister.setShowProgress(true)
                                btnRegister.text = ""
                            }

                            null -> {}
                        }
                    }
                }
            }
        }
    }
}