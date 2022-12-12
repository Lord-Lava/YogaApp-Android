package com.lava.yogaapp.ui.components.register

import android.app.backup.BackupAgent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.lava.yogaapp.R
import com.lava.yogaapp.data.Resource
import com.lava.yogaapp.databinding.FragmentRegistrationBinding
import com.lava.yogaapp.domain.model.registration.RegistrationResponse
import com.lava.yogaapp.ui.base.BaseFragment
import com.lava.yogaapp.util.SingleEvent
import com.lava.yogaapp.util.extensions.observe
import com.lava.yogaapp.util.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegistrationFragment :
    BaseFragment<FragmentRegistrationBinding>(R.layout.fragment_registration) {

    private val viewModel: RegistrationViewModel by viewModels()

    private var age = 0
    private var slot = ""

    override fun initViewBinding(view: View): FragmentRegistrationBinding {
        return FragmentRegistrationBinding.bind(view)
    }

    override fun setupViews() {
        populateSpinner()
        binding.btnRegister.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val name = binding.etName.text?.trim().toString()
        val amount = if (binding.cbAmount.isChecked) 500 else 0
        viewModel.registerUser(
            name, age, slot, amount
        )
    }

    private fun populateSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.slots_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerSlot.adapter = adapter
            binding.spinnerSlot.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        pos: Int,
                        id: Long,
                    ) {
                        val item = parent.getItemAtPosition(pos)
                        slot = item.toString()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                    }
                }
        }
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.age_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerAge.adapter = adapter
            binding.spinnerAge.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        pos: Int,
                        id: Long,
                    ) {
                        val item = parent.getItemAtPosition(pos)
                        age = item.toString().toInt()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                    }
                }
        }
    }

    private fun handleRegistrationResponse(status: Resource<RegistrationResponse>) {
        when (status) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                viewModel.navigateToYogaScreen()
            }
            is Resource.DataError -> {
                status.errorCode?.let { viewModel.showToastMessage(it) }
            }
        }
    }

    private fun handleNavigationToYogaScreen(event: SingleEvent<Boolean>) {
        event.getContentIfNotHandled()?.let {
            this.findNavController()
                .navigate(RegistrationFragmentDirections.actionRegisterFragmentToYogaFragment())
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Toast.LENGTH_LONG)
    }

    override fun observeViewModel() {
        observe(liveData = viewModel.registrationResponse, action = ::handleRegistrationResponse)
        observe(liveData = viewModel.navigateToYogaScreen, action = ::handleNavigationToYogaScreen)
        observeToast(viewModel.showToast)
    }
}