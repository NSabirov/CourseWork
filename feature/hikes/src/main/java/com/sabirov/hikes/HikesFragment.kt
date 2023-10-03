package com.sabirov.hikes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.sabirov.HikesNavigation
import com.sabirov.core_api.entities.camping.Camping
import com.sabirov.core_api.entities.hike.Hike
import com.sabirov.core_impl.network.Error
import com.sabirov.core_impl.network.Loading
import com.sabirov.core_impl.network.Success
import com.sabirov.hikes.databinding.FrHikesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HikesFragment : Fragment() {

    private var _binding: FrHikesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HikesViewModel by viewModels()

    @Inject
    lateinit var navigation: HikesNavigation

    private val hikesAdapter by lazy {
        HikesAdapter {
            //navigation.navigateToHikeInfo(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FrHikesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvHikes.apply {
                layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
                adapter = hikesAdapter
            }
            refreshLayout.setOnRefreshListener {
                searchView.setQuery("", true)
                viewModel.refresh()
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    hikesAdapter.setData(viewModel.hikes.value?.filter { hike: Hike ->
                        hike.name?.contains(
                            newText.toString()
                        ) ?: false
                    } ?: mutableListOf())
                    return true
                }

            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadingState.collect {
                binding.refreshLayout.isRefreshing = it
            }
        }
        viewModel.hikes.observe(viewLifecycleOwner) {
            hikesAdapter.setData(it)
        }
    }
}