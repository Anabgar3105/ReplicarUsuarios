package com.example.replicarusuarios.data

import com.example.replicarusuarios.network.DummyJsonApiService

interface DummyJsonRepository {
    suspend fun getAllUsers(): UserResponse
}

class NetworkDummyJsonRepository(
    private val dummyJsonApiService: DummyJsonApiService
) : DummyJsonRepository {
    override suspend fun getAllUsers(): UserResponse {
        return dummyJsonApiService.getUsers(
            limit = 10,
            skip = 0,
            select = "firstName,lastName,age,email,image,username,password,gender,phone,height,weight,company,id"
        )
    }
}
