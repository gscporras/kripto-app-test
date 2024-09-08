package com.kripto.android.ui.home

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kripto.android.databinding.FragmentHomeBinding
import com.kripto.android.domain.model.Application
import com.kripto.android.ui.home.adapter.AppsAdapter
import com.kripto.android.ui.home.viewmodel.HomeState
import com.kripto.android.ui.home.viewmodel.HomeViewModel
import com.kripto.android.ui.main.MainActivity
import com.kripto.android.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private var appsAdapter: AppsAdapter? = null

    override fun setupBinding() = with(binding) {
        viewModel.getApps()
        appsAdapter = AppsAdapter { app ->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAppDetailFragment(app))
        }
        rvApps.layoutManager = LinearLayoutManager(requireContext())
        rvApps.adapter = appsAdapter

        btnAddApp.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddAppFragment())
        }
        btnSeeRecomendations.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRecommendedFragment())
        }
    }

    override fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            handleState(state)
        }
    }

    private fun handleState(state: HomeState) {
        when (state) {
            HomeState.Loading -> {
                (requireActivity() as MainActivity).showLoading()
            }
            is HomeState.Success -> {
                (requireActivity() as MainActivity).hideLoading()
                loadApps(state.list)
            }
            is HomeState.Fail -> {
                (requireActivity() as MainActivity).hideLoading()
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
            else -> {
                (requireActivity() as MainActivity).hideLoading()
            }
        }
    }

    private fun loadApps(list: List<Application>) {
        appsAdapter?.addAll(list)
        binding.btnSeeRecomendations.isVisible = list.isNotEmpty()
    }
}