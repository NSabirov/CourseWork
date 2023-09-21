package com.sabirov.hikes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sabirov.temp.databinding.FrHikesBinding

class HikesFragment : Fragment() {

    private var _binding: FrHikesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HikesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FrHikesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}