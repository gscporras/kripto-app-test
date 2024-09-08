package com.kripto.android.ui.detail

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kripto.android.databinding.FragmentAppDetailBinding
import com.kripto.android.ui.detail.viewmodel.AppDetailState
import com.kripto.android.ui.detail.viewmodel.AppDetailViewModel
import com.kripto.android.ui.main.MainActivity
import com.kripto.android.utils.BaseFragment
import com.kripto.android.utils.hide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppDetailFragment :
    BaseFragment<FragmentAppDetailBinding>(FragmentAppDetailBinding::inflate) {

    private val viewModel: AppDetailViewModel by viewModels()
    private val args: AppDetailFragmentArgs by navArgs()

    override fun setupBinding() = with(binding) {
        toolbar.btnBack.setOnClickListener { findNavController().popBackStack() }
        toolbar.tvTitle.text = args.app.name
        toolbar.btnDelete.setOnClickListener { viewModel.deleteApp(args.app.id) }

        tvCpuUsage.text = "${args.app.cpuUsage}%"
        tvMemoryUsage.text = "${args.app.memoryUsage}%"
        tvUsageFrequency.text = args.app.usageFrequency
        tvStatus.text = args.app.status
        tvFunctionality.text = args.app.functionality
        tvUserCount.text = args.app.userCount.toString()
    }

    override fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) {
            handleState(it)
        }
    }

    private fun handleState(state: AppDetailState) {
        when (state) {
            AppDetailState.Loading -> {
                (requireActivity() as MainActivity).showLoading()
            }

            is AppDetailState.Success -> {
                (requireActivity() as MainActivity).hideLoading()
                findNavController().popBackStack()
            }

            is AppDetailState.Fail -> {
                (requireActivity() as MainActivity).hideLoading()
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }

            else -> {
                (requireActivity() as MainActivity).hideLoading()
            }
        }
    }
}