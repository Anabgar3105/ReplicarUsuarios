package com.example.replicarusuarios

import android.app.Application
import com.example.replicarusuarios.data.AppContainer
import com.example.replicarusuarios.data.DefaultAppContainer

class ReplicarUsuariosApplication : Application() {
    // ESTO ES EL "ALMACÉN" GLOBAL
    // Al ser pública, cualquiera con acceso a la "Application" puede coger esta variable.
    lateinit var container: AppContainer
    // ESTO ES EL "ARRANQUE"
    // Esta función es lo PRIMERO que se ejecuta de todo tu código,
    // antes incluso de que se vea nada en la pantalla.
    override fun onCreate() {
        super.onCreate()
        // Aquí llenamos el almacén
        container = DefaultAppContainer()
    }
}