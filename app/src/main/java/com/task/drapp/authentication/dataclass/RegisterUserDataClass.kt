package com.task.drapp.authentication.dataclass

data class RegisterUserDataClass(
    val id: Int,
    val name: String,
    val gender: String,
    val email: String,
    val number: String,
    val password: String
) {
    constructor() : this(-1, "", "", "", "", "")
}
