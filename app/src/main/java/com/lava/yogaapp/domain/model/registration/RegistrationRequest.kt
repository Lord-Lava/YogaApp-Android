package com.lava.yogaapp.domain.model.registration

data class RegistrationRequest(
    val uid: String,
    val name: String,
    val age: Int,
    val slot: String,
    val amount: Int,
)