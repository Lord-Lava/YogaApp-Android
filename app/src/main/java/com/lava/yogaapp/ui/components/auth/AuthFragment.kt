package com.lava.yogaapp.ui.components.auth

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.lava.yogaapp.R
import com.lava.yogaapp.data.Resource
import com.lava.yogaapp.data.error.UNAUTHORIZED
import com.lava.yogaapp.data.local.SharedPrefs
import com.lava.yogaapp.databinding.FragmentAuthBinding
import com.lava.yogaapp.domain.model.auth.AuthenticateResponse
import com.lava.yogaapp.domain.model.auth.LoginResponse
import com.lava.yogaapp.ui.base.BaseFragment
import com.lava.yogaapp.util.JWT_AUTH_TOKEN
import com.lava.yogaapp.util.SingleEvent
import com.lava.yogaapp.util.extensions.hideKeyboard
import com.lava.yogaapp.util.extensions.observe
import com.lava.yogaapp.util.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>(R.layout.fragment_auth) {

    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun initViewBinding(view: View): FragmentAuthBinding = FragmentAuthBinding.bind(view)

    override fun setupViews() {
        viewModel.authenticate()
        binding.btnSignIn.setOnClickListener { signIn() }
        binding.btnSignUp.setOnClickListener { signUp() }
    }

    private fun signIn() {
        val username = binding.etUsernameSignIn.text?.trim().toString()
        val password = binding.etPasswordSignIn.text?.trim().toString()
        viewModel.signIn(username, password)
        binding.btnSignIn.hideKeyboard()
    }

    private fun signUp() {
        val username = binding.etUsernameSignUp.text?.trim().toString()
        val password = binding.etPasswordSignUp.text?.trim().toString()
        viewModel.signUp(username, password)
        binding.btnSignUp.hideKeyboard()
    }

    private fun handleLoginResponse(status: Resource<LoginResponse>) {
        when (status) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                status.data?.let { checkRegistrationAndNavigate(it.isRegistered) }
            }
            is Resource.DataError -> {
                status.errorCode?.let {
                    viewModel.showToastMessage(it)
                }
            }
        }
    }

    private fun checkRegistrationAndNavigate(isRegistered: Boolean) {
        if (isRegistered) {
            viewModel.navigateToYogaScreen()
        } else {
            viewModel.navigateToRegistrationScreen()
        }
    }

    private fun handleNavigationToRegistrationScreen(event: SingleEvent<Boolean>) {
        event.getContentIfNotHandled()?.let {
            if (it) this.findNavController()
                .navigate(AuthFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    private fun handleNavigationToYogaScreen(event: SingleEvent<Boolean>) {
        event.getContentIfNotHandled()?.let {
            this.findNavController()
                .navigate(AuthFragmentDirections.actionLoginFragmentToYogaFragment())
        }
    }

    private fun handleAuthentication(status: Resource<AuthenticateResponse>) {
        when (status) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                status.data?.let {
                    val jwtAuthToken = sharedPrefs.getString(JWT_AUTH_TOKEN)
                    if (it.isRegistered && jwtAuthToken != null) {
                        viewModel.navigateToYogaScreen()
                    } else {
                        viewModel.showToastMessage(UNAUTHORIZED)
                    }
                }
            }
            is Resource.DataError -> {
                status.errorCode?.let { viewModel.showToastMessage(it) }
            }
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Toast.LENGTH_LONG)
    }

    override fun observeViewModel() {
        observe(liveData = viewModel.authenticateResponse, action = ::handleAuthentication)
        observe(liveData = viewModel.loginResponse, action = ::handleLoginResponse)
        observe(liveData = viewModel.navigateToRegistrationScreen,
            action = ::handleNavigationToRegistrationScreen)
        observe(liveData = viewModel.navigateToYogaScreen, action = ::handleNavigationToYogaScreen)
        observeToast(viewModel.showToast)
    }
}