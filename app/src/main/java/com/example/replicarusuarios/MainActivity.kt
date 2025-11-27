package com.example.replicarusuarios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.replicarusuarios.model.UserUiState
import com.example.replicarusuarios.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Un contenedor base de Material Design
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                ReplicarUsuariosScreen()
            }
        }
    }
}

@Composable
fun ReplicarUsuariosScreen() {
    // 1. OBTENEMOS EL VIEWMODEL
    // Usamos la "Factory" que creamos antes para que nos inyecte los repositorios automáticamente.
    val viewModel: UserViewModel = viewModel(factory = UserViewModel.Factory)

    // Obtenemos el estado actual (Success, Loading, Error...)
    val uiState = viewModel.uiState

    // 2. DISEÑO DE LA PANTALLA (Column = Elementos uno debajo de otro)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Centrar todo horizontalmente
    ) {

        // Título superior
        Text(
            text = "Replicar Usuarios...",
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.Start) // Alinear a la izquierda
                .padding(top = 16.dp)
        )

        // Empujamos el contenido hacia el centro vertical
        Spacer(modifier = Modifier.weight(1f))

        // Fila de Botones (Row = Elementos uno al lado del otro)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // BOTÓN 1: Inicializar (Traer de DummyJson)
            Button(onClick = { viewModel.inicializarUsuarios() }) {
                Text(text = "Inicializar Usuarios")
            }

            Spacer(modifier = Modifier.width(16.dp)) // Espacio entre botones

            // BOTÓN 2: Replicar (Guardar en MockAPI)
            Button(onClick = { viewModel.replicarUsuarios() }) {
                Text(text = "Replicar Usuarios")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 3. TEXTO DE ESTADO (Cambia según lo que pase en el ViewModel)
        val textoEstado = when (uiState) {
            is UserUiState.Success -> "Listo para empezar."
            is UserUiState.Loading -> "Cargando... Por favor espera."
            is UserUiState.SuccessFinish -> uiState.message // Mensaje de éxito del ViewModel
            is UserUiState.Error -> "ERROR: ${uiState.message}" // Mensaje de error
        }

        Text(text = "Estado de la consulta: $textoEstado")

        // Empujamos hacia arriba para que quede centrado
        Spacer(modifier = Modifier.weight(1f))
    }
}