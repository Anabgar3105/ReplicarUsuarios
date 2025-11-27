package com.example.replicarusuarios.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    // Lo ponemos nullable (? = null) por si al crear uno nuevo aún no tiene ID.
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val email: String,
    val image: String,
    val username: String,
    val password: String,
    val gender: String,
    val phone: String,
    val height: Double,
    val weight: Double,
    val company: Company? = null,
    )