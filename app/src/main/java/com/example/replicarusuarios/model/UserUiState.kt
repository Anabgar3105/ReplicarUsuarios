package com.example.replicarusuarios.model

// Una "sealed interface" es como un semáforo: solo puede estar en uno de estos estados a la vez.
sealed interface UserUiState {
    // 1. Estado inicial: La app acaba de abrirse, no ha pasado nada.
    object Success : UserUiState // O podrías llamarlo "Idle" (En reposo)

    // 2. Cargando: Se ha pulsado un botón y estamos esperando.
    object Loading : UserUiState

    // 3. Terminado con éxito: Mostraremos un mensaje.
    data class SuccessFinish(val message: String) : UserUiState

    // 4. Error: Algo falló (internet, servidor caído, etc).
    data class Error(val message: String) : UserUiState
}