package com.lava.yogaapp.ui.components.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lava.yogaapp.data.Resource
import com.lava.yogaapp.data.error.AMOUNT_ERROR
import com.lava.yogaapp.data.error.CHECK_REGISTRATION_FIELDS
import com.lava.yogaapp.data.error.NAME_ERROR
import com.lava.yogaapp.domain.model.registration.RegistrationResponse
import com.lava.yogaapp.domain.repository.UserRepository
import com.lava.yogaapp.ui.base.BaseViewModel
import com.lava.yogaapp.util.SingleEvent
import com.lava.yogaapp.util.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private val _showToast = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = _showToast

    private val _registrationResponse = MutableLiveData<Resource<RegistrationResponse>>()
    val registrationResponse = _registrationResponse as LiveData<Resource<RegistrationResponse>>

    private val _navigateToYogaScreen = MutableLiveData<SingleEvent<Boolean>>()
    val navigateToYogaScreen = _navigateToYogaScreen as LiveData<SingleEvent<Boolean>>

    fun registerUser(name: String, age: Int, slot: String, amount: Int) {
        val isValidName = name.isNotEmpty()
        val isValidAmount = amount != 0
        if (!isValidName && isValidAmount) {
            _registrationResponse.value = Resource.DataError(NAME_ERROR)
        } else if (!isValidAmount && isValidName) {
            _registrationResponse.value = Resource.DataError(AMOUNT_ERROR)
        } else if (!isValidName && !isValidAmount) {
            _registrationResponse.value = Resource.DataError(CHECK_REGISTRATION_FIELDS)
        } else {
            viewModelScope.launch {
                wrapEspressoIdlingResource {
                    userRepository.registerUser(name, age, slot, amount).collect {
                        _registrationResponse.value = it
                    }
                }
            }
        }
    }

    fun navigateToYogaScreen() {
        _navigateToYogaScreen.value = SingleEvent(true)
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        _showToast.value = SingleEvent(error.description)
    }
}