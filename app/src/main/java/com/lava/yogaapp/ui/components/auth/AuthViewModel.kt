package com.lava.yogaapp.ui.components.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lava.yogaapp.data.Resource
import com.lava.yogaapp.data.error.CHECK_LOGIN_FIELDS
import com.lava.yogaapp.data.error.PASSWORD_ERROR
import com.lava.yogaapp.data.error.USER_NAME_ERROR
import com.lava.yogaapp.domain.model.auth.AuthenticateResponse
import com.lava.yogaapp.domain.model.auth.LoginResponse
import com.lava.yogaapp.domain.repository.UserRepository
import com.lava.yogaapp.ui.base.BaseViewModel
import com.lava.yogaapp.util.RegexUtils.isValidEmail
import com.lava.yogaapp.util.SingleEvent
import com.lava.yogaapp.util.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private val _loginResponse = MutableLiveData<Resource<LoginResponse>>()
    val loginResponse = _loginResponse as LiveData<Resource<LoginResponse>>

    private val _authenticateResponse = MutableLiveData<Resource<AuthenticateResponse>>()
    val authenticateResponse = _authenticateResponse as LiveData<Resource<AuthenticateResponse>>

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = _showToast

    private val _navigateToYogaScreen = MutableLiveData<SingleEvent<Boolean>>()
    val navigateToYogaScreen = _navigateToYogaScreen as LiveData<SingleEvent<Boolean>>

    private val _navigateToRegistrationScreen = MutableLiveData<SingleEvent<Boolean>>()
    val navigateToRegistrationScreen =
        _navigateToRegistrationScreen as LiveData<SingleEvent<Boolean>>

    fun signIn(username: String, password: String) {
        val isUsernameValid = isValidEmail(username)
        val isPassWordValid = password.trim().length > 4
        if (isUsernameValid && !isPassWordValid) {
            _loginResponse.value = Resource.DataError(PASSWORD_ERROR)
        } else if (!isUsernameValid && isPassWordValid) {
            _loginResponse.value = Resource.DataError(USER_NAME_ERROR)
        } else if (!isUsernameValid && !isPassWordValid) {
            _loginResponse.value = Resource.DataError(CHECK_LOGIN_FIELDS)
        } else {
            viewModelScope.launch {
                _loginResponse.value = Resource.Loading()
                wrapEspressoIdlingResource {
                    userRepository.signIn(username, password).collect {
                        _loginResponse.value = it
                    }
                }
            }
        }
    }

    fun signUp(username: String, password: String) {
        val isUsernameValid = isValidEmail(username)
        val isPassWordValid = password.trim().length > 4
        if (isUsernameValid && !isPassWordValid) {
            _loginResponse.value = Resource.DataError(PASSWORD_ERROR)
        } else if (!isUsernameValid && isPassWordValid) {
            _loginResponse.value = Resource.DataError(USER_NAME_ERROR)
        } else if (!isUsernameValid && !isPassWordValid) {
            _loginResponse.value = Resource.DataError(CHECK_LOGIN_FIELDS)
        } else {
            viewModelScope.launch {
                _loginResponse.value = Resource.Loading()
                wrapEspressoIdlingResource {
                    userRepository.signUp(username, password).collect {
                        _loginResponse.value = it
                    }
                }
            }
        }
    }

    fun authenticate() {
        viewModelScope.launch {
            _authenticateResponse.value = Resource.Loading()
            wrapEspressoIdlingResource {
                userRepository.authenticate().collect {
                    _authenticateResponse.value = it
                }
            }
        }
    }

    fun navigateToYogaScreen() {
        _navigateToYogaScreen.value = SingleEvent(true)
    }

    fun navigateToRegistrationScreen() {
        _navigateToRegistrationScreen.value = SingleEvent(true)
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        _showToast.value = SingleEvent(error.description)
    }
}