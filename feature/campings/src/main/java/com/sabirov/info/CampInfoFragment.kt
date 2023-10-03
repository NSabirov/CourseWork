package com.sabirov.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.sabirov.CampingsNavigation
import com.sabirov.campings.databinding.FrCampInfoBinding
import com.sabirov.core_api.entities.camping.Camping
import com.sabirov.core_impl.network.Error
import com.sabirov.core_impl.network.Loading
import com.sabirov.core_impl.network.Success
import com.sabirov.resources.BundleArguments
import com.sabirov.utils.argument
import com.sabirov.utils.registerOnBackPressedCallback
import com.sabirov.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CampInfoFragment : Fragment() {

    private var _binding: FrCampInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CampInfoViewModel by viewModels()

    @Inject
    lateinit var navigation: CampingsNavigation

    private var campId: Number by argument(BundleArguments.ARG_CAMP_ID, null)

    private val imageAdapter by lazy {
        ImageAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FrCampInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerOnBackPressedCallback {
            navigation.onBackPressed(true)
        }
        binding.apply {
            toolbar.setNavigationOnClickListener {
                navigation.onBackPressed(true)
            }
            rvImages.apply {
                layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
                adapter = imageAdapter
            }
            PagerSnapHelper().attachToRecyclerView(rvImages)
            refreshLayout.setOnRefreshListener {
                viewModel.refresh(campId)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            viewModel.apply {
                refresh(campId)
                viewLifecycleOwner.lifecycleScope.launch {
                    loadingState.collect { state ->
                        when (state) {
                            is Error -> {

                            }

                            Loading -> {
                                placeholderProgressView.visible(true)
                                placeholderProgressView.startShimmer()
                                clContainer.visible(false)
                            }

                            is Success -> {
                                (state.item as? Camping)?.let {
                                    imageAdapter.setData(it.photos)
                                    tvTitle.text = it.name
                                    tvCity.text = it.nearestTown
                                    tvPhone.text = it.phone
                                    tvSite.text = it.site
                                    tvDescription.text = it.description

                                    placeholderProgressView.stopShimmer()
                                    placeholderProgressView.visible(false)
                                    refreshLayout.isRefreshing = false
                                    clContainer.visible(true)
                                }
                            }

                            null -> {}
                        }
                    }
                }
            }
        }
    }
}