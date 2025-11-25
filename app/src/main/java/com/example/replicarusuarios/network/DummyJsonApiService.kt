package com.example.replicarusuarios.network

import com.example.replicarusuarios.data.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyJsonApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("select") select: String,
        ): UserResponse
}