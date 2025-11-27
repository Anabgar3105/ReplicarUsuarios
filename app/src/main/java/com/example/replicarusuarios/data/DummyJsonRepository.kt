package com.example.replicarusuarios.data

import com.example.replicarusuarios.network.DummyJsonApiService

// Repositorio para DUMMYJSON (Lectura)
// Solo necesitamos leer, así que la interfaz es corta.
interface DummyJsonRepository {
    suspend fun getAllUsers(): UserResponse
}

class NetworkDummyJsonRepository(
    private val dummyJsonApiService: DummyJsonApiService
) : DummyJsonRepository {
    override suspend fun getAllUsers(): UserResponse {
        // Aquí configuramos la llamada:
        // limit=100: Traer hasta 100 usuarios
        // select: Traer solo los campos útiles (ahorra datos)
        return dummyJsonApiService.getUsers(
            limit = 100,
            skip = 0,
            select = "firstName,lastName,age,email,image,username,password,gender,phone,height,weight,company,id"
        )
    }
}
