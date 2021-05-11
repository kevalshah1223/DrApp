package com.task.drapp.dataclass

data class UserDetailsDataClass(
    val userId: Int,
    val userName: String,
    val userContact: String,
    val appointmentTime: String,
    val appointmentDate: String,
    val gender: String,
    val isApproved: String = "pending"
)