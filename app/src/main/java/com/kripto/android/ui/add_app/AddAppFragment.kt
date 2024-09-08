package com.kripto.android.ui.add_app

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kripto.android.databinding.FragmentAddAppBinding
import com.kripto.android.domain.model.Application
import com.kripto.android.ui.add_app.adapter.FrequencyAdapter
import com.kripto.android.ui.add_app.adapter.StatusAdapter
import com.kripto.android.ui.add_app.viewmodel.AddAppState
import com.kripto.android.ui.add_app.viewmodel.AddAppViewModel
import com.kripto.android.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddAppFragment : BaseFragment<FragmentAddAppBinding>(FragmentAddAppBinding::inflate) {

    private val viewModel: AddAppViewModel by viewModels()

    private var selectedFrequency = ""
    private var selectedStatus = ""

    override fun setupBinding() = with(binding) {
        val frequencyList = listOf("diaria", "semanal", "mensual")
        spinnerFrequency.adapter = FrequencyAdapter(requireContext(), frequencyList)
        spinnerFrequency.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                lifecycleScope.launch(Dispatchers.IO) {
                    if (position != 0) {
                        // Aquí ajustamos el índice restando 1 para obtener el elemento correcto de la lista
                        selectedFrequency = frequencyList[position - 1]
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val statusList = listOf("activo", "obsoleto")
        spinnerStatus.adapter = StatusAdapter(requireContext(), statusList)
        spinnerStatus.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                lifecycleScope.launch(Dispatchers.IO) {
                    if (position != 0) {
                        // Aquí ajustamos el índice restando 1 para obtener el elemento correcto de la lista
                        selectedStatus = statusList[position - 1]
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        btnSaveApp.setOnClickListener {
            val name = etAppName.text.toString().trim()
            val cpuUsage = etCpuUsage.text.toString().trim()
            val memoryUsage = etMemoryUsage.text.toString().trim()
            val functionality = etFunctionality.text.toString().trim()
            val userCount = etUserCount.text.toString().trim()

            viewModel.addApp(
                app = Application(
                    name = name,
                    cpuUsage = cpuUsage.toFloat(),
                    memoryUsage = memoryUsage.toFloat(),
                    usageFrequency = selectedFrequency,
                    status = selectedStatus,
                    functionality = functionality,
                    userCount = userCount.toInt()
                )
            )
        }
    }

    override fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            handleState(state)
        }
    }

    private fun handleState(state: AddAppState) {
        when (state) {
            AddAppState.Loading -> {

            }

            AddAppState.Success -> {
                findNavController().popBackStack()
            }

            is AddAppState.Fail -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }
}