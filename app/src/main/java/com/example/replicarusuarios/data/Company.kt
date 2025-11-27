package com.example.replicarusuarios.data

import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val name: String? = "",
    val title: String? = ""
)