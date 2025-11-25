package com.example.replicarusuarios.data

import com.example.replicarusuarios.network.MockApiService


interface MockApiRepository {
    suspend fun getUsers(): List<User>
    suspend fun saveUser(user: User): User
    suspend fun deleteUser(id: Int)
}

class NetworkMockApiRepository(private val mockApiService: MockApiService) : MockApiRepository {
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