package com.example.replicarusuarios.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    // Lo ponemos nullable (? = null) por si al crear uno nuevo aún no tiene ID.
    val id: Int? = null,
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
    val company: Company? = null,
    )