package com.kripto.android.ui.add_app

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kripto.android.databinding.FragmentAddAppBinding
import com.kripto.android.domain.model.Application
import com.kripto.android.ui.add_app.adapter.FrequencyAdapter
import com.kripto.android.ui.add_app.adapter.StatusAdapter
import com.kripto.android.ui.add_app.viewmodel.AddAppState
import com.kripto.android.ui.add_app.viewmodel.AddAppViewModel
import com.kripto.android.ui.main.MainActivity
import com.kripto.android.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

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
                selectedFrequency = if (position != AdapterView.INVALID_POSITION) {
                    frequencyList[position]
                } else ""
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
                selectedStatus = if (position != AdapterView.INVALID_POSITION) {
                    statusList[position]
                } else ""
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        btnSaveApp.setOnClickListener {
            if (validateInputs()) {
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
    }

    private fun validateInputs(): Boolean {
        with(binding) {
            var isValid = true

            if (etAppName.text.isNullOrEmpty()) {
                etAppName.error = "El nombre es obligatorio"
                isValid = false
            }

            if (etCpuUsage.text.isNullOrEmpty()) {
                etCpuUsage.error = "El uso de CPU es obligatorio"
                isValid = false
            } else if (!isValidFloat(etCpuUsage.text.toString())) {
                etCpuUsage.error = "El uso de CPU debe ser un número válido"
                isValid = false
            }

            if (etMemoryUsage.text.isNullOrEmpty()) {
                etMemoryUsage.error = "El uso de memoria es obligatorio"
                isValid = false
            } else if (!isValidFloat(etMemoryUsage.text.toString())) {
                etMemoryUsage.error = "El uso de memoria debe ser un número válido"
                isValid = false
            }

            if (selectedFrequency.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Selecciona la frecuencia de uso",
                    Toast.LENGTH_SHORT
                ).show()
                isValid = false
            }

            if (selectedStatus.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Selecciona el estado de la aplicación",
                    Toast.LENGTH_SHORT
                ).show()
                isValid = false
            }

            if (etFunctionality.text.isNullOrEmpty()) {
                etFunctionality.error = "La funcionalidad es obligatoria"
                isValid = false
            }

            if (etUserCount.text.isNullOrEmpty()) {
                etUserCount.error = "El número de usuarios es obligatorio"
                isValid = false
            } else if (!isValidInt(etUserCount.text.toString())) {
                etUserCount.error = "El número de usuarios debe ser un número válido"
                isValid = false
            }

            return isValid
        }
    }

    private fun isValidFloat(value: String): Boolean {
        return try {
            value.toFloat()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun isValidInt(value: String): Boolean {
        return try {
            value.toInt()
            true
        } catch (e: NumberFormatException) {
            false
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
                (requireActivity() as MainActivity).showLoading()
            }

            AddAppState.Success -> {
                (requireActivity() as MainActivity).hideLoading()
                findNavController().popBackStack()
            }

            is AddAppState.Fail -> {
                (requireActivity() as MainActivity).hideLoading()
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }

            else -> {
                (requireActivity() as MainActivity).hideLoading()
            }
        }
    }
}