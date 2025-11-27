package com.example.replicarusuarios.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.replicarusuarios.ReplicarUsuariosApplication
import com.example.replicarusuarios.data.DummyJsonRepository
import com.example.replicarusuarios.data.MockApiRepository
import com.example.replicarusuarios.data.User
import com.example.replicarusuarios.model.UserUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

class UserViewModel(
    private val dummyRepository: DummyJsonRepository,
    private val mockRepository: MockApiRepository
) : ViewModel() {

    // Variable de ESTADO: La pantalla "observará" esta variable.
    // Cuando cambie, la pantalla se redibujará sola.
    var uiState: UserUiState by mutableStateOf(UserUiState.Success)
        private set

    // Lista temporal para guardar los usuarios que traemos de Dummy
    // antes de enviarlos a MockAPI.
    private var usersToReplicate: List<User> = emptyList()

    /**
     * BOTÓN 1: INICIALIZAR
     * Trae los usuarios de DummyJson y los guarda en memoria.
     */
    fun inicializarUsuarios() {
        // Lanzamos una "corrutina" (hilo secundario) para no congelar la app
        viewModelScope.launch {
            uiState = UserUiState.Loading // Ponemos estado "Cargando"
            try {
                // 1. Llamamos al repositorio de Dummy
                val response = dummyRepository.getAllUsers()

                // 2. Guardamos la lista en memoria
                usersToReplicate = response.users

                // 3. Avisamos de que to-do salió bien
                uiState = UserUiState.SuccessFinish("Datos obtenidos: ${usersToReplicate.size} usuarios listos.")
            } catch (e: IOException) {
                uiState = UserUiState.Error("Error de red: Revisa tu conexión")
            } catch (e: Exception) {
                uiState = UserUiState.Error("Error desconocido: ${e.message}")
            }
        }
    }

    /**
     * BOTÓN 2: REPLICAR
     * Toma los usuarios que tenemos en memoria y los sube uno a uno a tu MockAPI.
     */
    fun replicarUsuarios() {
        viewModelScope.launch {
            uiState = UserUiState.Loading
            try {
                if (usersToReplicate.isEmpty()) {
                    uiState = UserUiState.Error("Primero debes 'Inicializar' para tener usuarios.")
                    return@launch
                }

                // FASE 1: LIMPIEZA
                val usuariosAntiguos = mockRepository.getUsers()
                if (usuariosAntiguos.isNotEmpty()) {
                    usuariosAntiguos.forEach { usuarioViejo ->
                        usuarioViejo.id?.let { id ->
                            try {
                                mockRepository.deleteUser(id)
                                // --- FRENO 1 ---
                                // Esperamos un poquito para no saturar la API borrando
                                delay(300)
                            } catch (e: Exception) {
                                println("Error borrando: ${e.message}")
                            }
                        }
                    }
                }

                // FASE 2: REPLICACIÓN
                var exitosos = 0
                usersToReplicate.forEach { user ->
                    try {
                        val userClean = user.copy(id = null, company = null)
                        mockRepository.saveUser(userClean)
                        exitosos++

                        // --- FRENO 2 ---
                        // Esperamos un poquito después de guardar cada uno
                        delay(300)

                    } catch (e: Exception) {
                        // Mira el Logcat para ver QUÉ error da exactamente
                        println("ERROR al subir usuario: ${e.message}")
                    }
                }

                uiState = UserUiState.SuccessFinish("Limpieza completa. Se han replicado $exitosos usuarios nuevos.")

            } catch (e: Exception) {
                uiState = UserUiState.Error("Error general: ${e.message}")
            }
        }
    }
    /**
     * FACTORY (La Fábrica)
     * Como nuestro ViewModel tiene parámetros (los repositorios), Android no sabe
     * crearlo solo. Este código le enseña cómo hacerlo usando el AppContainer.
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // 1. Accedemos a la "Application"
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ReplicarUsuariosApplication)
                // 2. Sacamos los repositorios del contenedor
                val dummyRepo = application.container.dummyJsonRepository
                val mockRepo = application.container.mockApiRepository
                // 3. Creamos el ViewModel entregándole las herramientas
                UserViewModel(dummyRepo, mockRepo)
            }
        }
    }
}