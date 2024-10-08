package com.kripto.android.ui.home

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kripto.android.databinding.FragmentHomeBinding
import com.kripto.android.ui.home.adapter.AppsAdapter
import com.kripto.android.ui.home.viewmodel.HomeViewModel
import com.kripto.android.utils.BaseFragment
import com.kripto.android.utils.hide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private var appsAdapter: AppsAdapter? = null

    override fun setupBinding() = with(binding) {
        appsAdapter = AppsAdapter { app ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAppDetailFragment(
                    app
                )
            )
        }
        rvApps.layoutManager = LinearLayoutManager(requireContext())
        rvApps.setHasFixedSize(true)
        rvApps.adapter = appsAdapter

        etSearch.doAfterTextChanged {
            val query = it.toString().trim()
            appsAdapter?.filter(query)
            btnClose.isVisible = query.isNotEmpty()
            btnClose.setOnClickListener { etSearch.text?.clear() }
        }

        btnAdd.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddAppFragment())
        }
        btnSeeRecomendations.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRecommendedFragment())
        }
    }

    override fun setupObservers() {
        viewModel.apps.observe(viewLifecycleOwner) { applications ->
            appsAdapter?.submitList(applications)  // Trabajar directamente con Application
            binding.btnSeeRecomendations.isVisible = applications.isNotEmpty()
            binding.llSearch.isVisible = applications.isNotEmpty()
            binding.tvEmpty.isVisible = applications.isEmpty()
        }
    }
}