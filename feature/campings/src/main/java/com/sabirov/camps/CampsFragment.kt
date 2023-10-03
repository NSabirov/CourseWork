package com.sabirov.camps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.bumptech.glide.Glide
import com.sabirov.utils.visible
import com.sabirov.CampingsNavigation
import com.sabirov.campings.databinding.FrCampsBinding
import com.sabirov.core_api.entities.camping.Camping
import com.sabirov.utils.dpToPx
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

    private val campingsAdapter by lazy {
        CampingsAdapter {
            navigation.navigateToCampInfo(it)
        }
    }

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
                    getString(
                        com.sabirov.resources.R.string.title_hello_s,
                        if (!viewModel.user.value?.firstName.isNullOrEmpty()) {
                            "\n${viewModel.user.value?.firstName}"
                        } else {
                            ""
                        }
                    )
                } else {
                    getString(
                        com.sabirov.resources.R.string.text_placeholder,
                        "${viewModel.user.value?.firstName ?: ""} ${viewModel.user.value?.lastName ?: ""}"
                    )
                }
            }
            ivAvatar.setOnClickListener {
                viewModel.user.value?.profileId?.let { it1 -> navigation.navigateToProfile(it1) }
            }
            refreshLayout.setOnRefreshListener {
                searchView.setQuery("", true)
                viewModel.refresh()
            }

            rvCampings.apply {
                layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
                adapter = campingsAdapter
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    campingsAdapter.setData(viewModel.campings.value?.filter { camp: Camping ->
                        camp.name?.contains(
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
        viewModel.apply {
            refresh()
            user.observe(viewLifecycleOwner) {
                binding.colToolbar.title = getString(
                    com.sabirov.resources.R.string.title_hello_s,
                    if (!viewModel.user.value?.firstName.isNullOrEmpty()) {
                        "\n${viewModel.user.value?.firstName}"
                    } else {
                        ""
                    }
                )
                Glide.with(requireContext())
                    .load(it?.photoUrl)
                    .placeholder(com.sabirov.resources.R.drawable.ic_avatar)
                    .into(binding.ivAvatar)
            }

            campings.observe(viewLifecycleOwner) {
                campingsAdapter.setData(it)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                loadingState.collect {
                    binding.refreshLayout.isRefreshing = it
                }
            }
        }
    }
}