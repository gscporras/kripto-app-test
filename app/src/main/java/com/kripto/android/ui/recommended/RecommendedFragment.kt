package com.kripto.android.ui.recommended

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kripto.android.databinding.FragmentRecommendedBinding
import com.kripto.android.ui.main.MainActivity
import com.kripto.android.ui.recommended.adapter.RecommendedAdapter
import com.kripto.android.ui.recommended.viewmodel.RecommendedState
import com.kripto.android.ui.recommended.viewmodel.RecommendedViewModel
import com.kripto.android.utils.BaseFragment
import com.kripto.android.utils.hide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendedFragment :
    BaseFragment<FragmentRecommendedBinding>(FragmentRecommendedBinding::inflate) {

    private val viewModel: RecommendedViewModel by viewModels()

    private var recommendedAdapter: RecommendedAdapter? = null

    override fun setupBinding() = with(binding) {
        toolbar.btnBack.setOnClickListener { findNavController().popBackStack() }
        toolbar.tvTitle.text = "Recomendaciones"
        toolbar.btnDelete.hide()

        viewModel.getRecommended()

        recommendedAdapter = RecommendedAdapter()
        rvRecommended.layoutManager = LinearLayoutManager(context)
        rvRecommended.adapter = recommendedAdapter
    }

    override fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) {
            handleState(it)
        }
    }

    private fun handleState(state: RecommendedState) {
        when (state) {
            RecommendedState.Loading -> {
                (requireActivity() as MainActivity).showLoading()
            }
            is RecommendedState.Success -> {
                (requireActivity() as MainActivity).hideLoading()
                loadRecommended(state.recommendedList)
            }
            is RecommendedState.Fail -> {
                (requireActivity() as MainActivity).hideLoading()
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
            else -> {
                (requireActivity() as MainActivity).hideLoading()
            }
        }
    }

    private fun loadRecommended(recommendedList: List<String>) {
        recommendedAdapter?.addAll(recommendedList)
    }
}