package com.example.taskcdp.data.model


sealed class Responses {
    data class LoginUserDataResponse(
        val id: Long = -1,
        val username: String = "",
        val email: String = "",
        val firstName: String = "",
        val lastName: String = "",
        val age: String = "",
        val phone: String = "",
        val birthDate: String = "",
        val image: String = "",
        val token: String = "",
        val address: Address = Address()
    )

    data class Address(
        val address: String = "",
        val city: String = ""
    )
}

