package com.lava.yogaapp.usecase.errors

import com.lava.yogaapp.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
