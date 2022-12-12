package com.lava.yogaapp.data.remote.dto.mapper

import com.lava.yogaapp.data.remote.dto.RegistrationResponseDto
import com.lava.yogaapp.domain.model.registration.RegistrationResponse

fun RegistrationResponseDto.toRegistrationResponse(): RegistrationResponse {
    return RegistrationResponse(
        uid = uid,
        name = name,
        age = age,
        amount = amount,
        slot = slot
    )
}