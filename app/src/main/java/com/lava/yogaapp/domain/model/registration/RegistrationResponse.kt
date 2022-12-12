package com.lava.yogaapp.domain.model.registration

data class RegistrationResponse(
    val uid: String,
    val name: String,
    val age: Int,
    val slot: String,
    val amount: Int,
)