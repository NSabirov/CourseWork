package com.sabirov.logIn

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
import com.sabirov.core_api.entities.auth.LogInBody
import com.sabirov.core_impl.network.Error
import com.sabirov.core_impl.network.Loading
import com.sabirov.core_impl.network.Success
import com.sabirov.AuthNavigation
import com.sabirov.resources.R
import com.sabirov.authorization.databinding.FrLogInBinding
import com.sabirov.utils.registerOnBackPressedCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.sabirov.utils.setShowProgress
import kotlinx.coroutines.delay

@AndroidEntryPoint
class LogInFragment : Fragment() {
    private var _binding: FrLogInBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LogInViewModel by viewModels()

    @Inject
    lateinit var navigation: AuthNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FrLogInBinding.inflate(inflater, container, false)
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
                btnLogIn.isEnabled = etEmail.isValidEmptyWithAnother(etPassword)
            }

            etPassword.doOnTextChanged { _, _, _, _ ->
                btnLogIn.isEnabled = etPassword.isValidEmptyWithAnother(etEmail)
            }

            btnLogIn.setOnClickListener {
                viewModel.login(LogInBody(etEmail.text.toString(), etPassword.text.toString()))
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.requestState.collect {
                        when (it) {
                            is Error -> {
                                btnLogIn.setShowProgress(false)
                                btnLogIn.text = getString(R.string.title_log_in)
                                Toast.makeText(requireContext(), it.text, Toast.LENGTH_SHORT).show()
                            }

                            is Success -> {
                                btnLogIn.setShowProgress(false)
                                btnLogIn.text = getString(R.string.title_log_in)
                                navigation.navigateToMain()
                            }

                            is Loading -> {
                                btnLogIn.setShowProgress(true)
                                btnLogIn.text = ""
                            }

                            null -> {}
                        }
                    }
                }
            }
        }
    }
}