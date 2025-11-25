package com.example.replicarusuarios.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val age: Int,
    val email: Int,
    val image: String,
    val username: String,
    val password: String,
    val gender: String,
    val phone: String,
    val height: Int,
    val weight: Int,
    val company: Object,
    )