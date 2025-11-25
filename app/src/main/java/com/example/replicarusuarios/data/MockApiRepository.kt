package com.example.replicarusuarios.data

import com.example.replicarusuarios.network.MockApiService


interface MockApiRepository {

}

class NetworkMockApiRepository(private val mockApiService: MockApiService): MockApiRepository{

}