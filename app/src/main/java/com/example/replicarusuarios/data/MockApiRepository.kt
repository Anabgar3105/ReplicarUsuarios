package com.example.replicarusuarios.data

import com.example.replicarusuarios.network.MockApiService

// Repositorio para MOCKAPI (Tu Base de Datos)
// Aquí necesitamos leer, escribir y borrar.
interface MockApiRepository {
    suspend fun getUsers(): List<User>
    suspend fun saveUser(user: User): User
    suspend fun deleteUser(id: Int)
}

class NetworkMockApiRepository(private val mockApiService: MockApiService) : MockApiRepository {
    // Obtener la lista de tu API
    override suspend fun getUsers(): List<User> {
        return mockApiService.getUsers()
    }

    override suspend fun saveUser(user: User): User {
        return mockApiService.createUser(user)
    }

    override suspend fun deleteUser(id: Int) {
        mockApiService.deleteUser(id)
    }
}