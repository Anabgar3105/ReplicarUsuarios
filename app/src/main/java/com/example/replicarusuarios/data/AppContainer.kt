package com.example.replicarusuarios.data

import com.example.replicarusuarios.network.DummyJsonApiService
import com.example.replicarusuarios.network.MockApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// 1. La Interfaz: Define qué herramientas estarán disponibles
interface AppContainer {
    val dummyJsonRepository: DummyJsonRepository
    val mockApiRepository: MockApiRepository
}

// 2. La Implementación: Aquí construimos las herramientas de verdad
class DefaultAppContainer : AppContainer {

    // --- CONFIGURACIÓN GENERAL ---

    // Configuración del traductor JSON (Kotlin Serialization)
    // ignoreUnknownKeys = true: Es VITAL. Si la API añade un campo nuevo mañana,
    // tu app ignorará ese campo extra en lugar de explotar con un error.
    private val json = Json {
        ignoreUnknownKeys = true // Esto ya lo tenías

        // AÑADE ESTO: Permite JSONs un poco "sucios" o con comillas raras
        isLenient = true

        // AÑADE ESTO: Si viene un null donde no debe, intenta arreglarlo
        coerceInputValues = true

        // AÑADE ESTO: A veces ayuda con números entre comillas
        allowSpecialFloatingPointValues = true
    }

    // Tipo de archivo que vamos a leer (application/json)
    private val contentType = "application/json".toMediaType()

    // --- CONEXIÓN 1: DUMMYJSON ---

    private val dummyBaseUrl = "https://dummyjson.com/"

    // Creamos el cliente Retrofit para DummyJSON
    private val dummyRetrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory(contentType)) // Usamos el traductor
        .baseUrl(dummyBaseUrl)
        .build()

    // Creamos el servicio (la interfaz)
    private val dummyRetrofitService: DummyJsonApiService by lazy {
        dummyRetrofit.create(DummyJsonApiService::class.java)
    }

    // --- CONEXIÓN 2: MOCKAPI (Tu API) ---

    // Tu URL base (asegúrate de que termina en barra /)
    private val mockBaseUrl = "https://6926033a26e7e41498f90d34.mockapi.io/api/wirtz/"

    // Creamos el cliente Retrofit para MockAPI
    private val mockRetrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory(contentType))
        .baseUrl(mockBaseUrl)
        .build()

    // Creamos el servicio
    private val mockRetrofitService: MockApiService by lazy {
        mockRetrofit.create(MockApiService::class.java)
    }

    // --- REPOSITORIOS (Lo que usa la app) ---

    // Inicializamos el repositorio de Dummy pasándole su servicio
    override val dummyJsonRepository: DummyJsonRepository by lazy {
        NetworkDummyJsonRepository(dummyRetrofitService)
    }

    // Inicializamos el repositorio de Mock pasándole su servicio
    override val mockApiRepository: MockApiRepository by lazy {
        NetworkMockApiRepository(mockRetrofitService)
    }
}