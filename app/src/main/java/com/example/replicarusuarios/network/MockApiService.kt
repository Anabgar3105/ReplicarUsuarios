package com.example.replicarusuarios.network

import com.example.replicarusuarios.data.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MockApiService {
    @GET(value = "users")
    suspend fun getUsers(): List<User>

    @POST(value = "users")
    suspend fun createUser(@Body user: User): User

    @DELETE(value = "users/{id")
    suspend fun deleteUser(@Path("id") id: Int): User

}