# 📱 ReplicarUsuarios App

Una aplicación Android moderna construida con **Kotlin**, **Jetpack Compose** y **Retrofit**. 
El objetivo principal de este proyecto es practicar el consumo de APIs REST, la gestión de datos y la arquitectura limpia, leyendo usuarios de una fuente pública (DummyJSON) y replicándolos en una base de datos propia (MockAPI).

---

## 🏛️ Arquitectura del Proyecto

El proyecto sigue la arquitectura recomendada por Google (MVVM + Repository Pattern) para asegurar que el código sea escalable, testeable y fácil de mantener.

### 🛠️ Tecnologías Clave
* **Lenguaje:** Kotlin 2.0+
* **UI:** Jetpack Compose (Material Design 3)
* **Red:** Retrofit 2 + OkHttp
* **Serialización:** Kotlinx Serialization (JSON)
* **Imágenes:** Coil
* **Inyección de Dependencias:** Manual (AppContainer)
* **Corrutinas:** Gestión de hilos en segundo plano.

---

## 🚀 Paso a Paso: Construyendo la App

A continuación se detalla cada capa de la aplicación, explicando qué hicimos y por qué.

### 1. El Modelo de Datos (Data Layer)
Definimos las clases `User`, `UserResponse` y `Company` con la etiqueta `@Serializable`.

* **💻 Explicación Técnica:**
    Creamos *Data Classes* que actúan como espejo de la estructura JSON que recibimos de la API. Usamos la anotación `@SerialName` para mapear campos con nombres distintos (ej: `firstName` en JSON vs `firstName` en Kotlin) y aseguramos la compatibilidad de tipos.
* **💡 La Metáfora (El Diccionario):**
    Imagina que la API nos habla en un idioma extranjero. El Modelo es nuestro **diccionario**. Le dice a la App: *"Cuando escuches la palabra 'firstName', significa 'Nombre', y debes guardarlo como texto"*. Sin esto, la App no entendería qué está recibiendo.

### 2. Capa de Red (Network Layer)
Creamos dos interfaces: `DummyJsonApiService` (Lectura) y `MockApiService` (Escritura).

* **💻 Explicación Técnica:**
    Definimos interfaces usando **Retrofit**. Aquí especificamos los verbos HTTP (`@GET`, `@POST`, `@DELETE`), los endpoints (`/users`) y los parámetros necesarios (`@Body`, `@Path`).
* **💡 La Metáfora (El Menú del Restaurante):**
    Estas interfaces son como el **menú**. No cocinan la comida, solo listan qué platos (datos) están disponibles para pedir y qué ingredientes (parámetros) necesitas dar para obtenerlos.

### 3. El Patrón Repositorio (Repository)
Creamos `NetworkDummyJsonRepository` y `NetworkMockApiRepository`.

* **💻 Explicación Técnica:**
    El Repositorio actúa como una **capa de abstracción**. La UI no conoce a Retrofit directamente; solo pide datos al repositorio. Esto permite cambiar la fuente de datos (ej: de Internet a Base de Datos local) sin romper la pantalla.
* **💡 La Metáfora (El Camarero):**
    El Repositorio es el **camarero**. El cliente (la Pantalla) tiene hambre y pide comida. No le importa si el cocinero la saca del horno o de la nevera, ni cómo funciona la estufa. El camarero se encarga de ir a la cocina, pelearse con los cocineros y traer el plato listo.

### 4. Inyección de Dependencias (AppContainer)
Creamos la clase `AppContainer` y la clase `ReplicarUsuariosApplication`.

* **💻 Explicación Técnica:**
    Centralizamos la creación de objetos pesados (como la instancia de Retrofit) en un contenedor único que vive durante toda la vida de la App. Usamos `by lazy` para inicializar los objetos solo cuando se necesitan (Lazy Initialization).
* **💡 La Metáfora (La Caja de Herramientas):**
    En lugar de comprar un taladro nuevo cada vez que queremos colgar un cuadro, compramos una **Caja de Herramientas** al principio de la obra. La guardamos en un lugar seguro (`Application`) y, cuando alguien necesita trabajar, simplemente va a la caja y coge la herramienta que ya está lista.

### 5. El Cerebro (ViewModel)
Creamos `UserViewModel` y definimos los estados `UserUiState`.

* **💻 Explicación Técnica:**
    El ViewModel gestiona la lógica de negocio y el estado de la UI. Sobrevive a los cambios de configuración (como rotar la pantalla). Lanza **Corrutinas** (`viewModelScope.launch`) para no bloquear el hilo principal y actualiza el `uiState` para que la vista reaccione.
* **💡 La Metáfora (El Controlador de Tráfico):**
    Es el **cerebro** de la operación. La pantalla es tonta, solo dibuja. El ViewModel decide qué hacer: *"¿Pulsaste el botón? Vale, voy a llamar al repositorio. ¡Espera! Pon el semáforo en rojo (Loading)... Ya llegaron los datos, pon el semáforo en verde (Success)"*.

### 6. La Pantalla (UI con Compose)
Creamos la `MainActivity` y la función `ReplicarUsuariosScreen`.

* **💻 Explicación Técnica:**
    Usamos **Jetpack Compose**, el kit de herramientas declarativo moderno. La UI "observa" el estado del ViewModel. Cuando el estado cambia (de Cargando a Éxito), la pantalla se redibuja automáticamente (Recomposition).
* **💡 La Metáfora (El Lienzo Mágico):**
    No pintamos píxel a píxel. Le damos al pintor una lista de instrucciones: *"Si el estado es 'Cargando', dibuja un reloj. Si es 'Éxito', dibuja la lista"*. El lienzo se actualiza solo cada vez que las instrucciones cambian.

---

## ▶️ Cómo ejecutar el proyecto

1.  Clonar el repositorio.
2.  Abrir en **Android Studio Ladybug** (o superior).
3.  Sincronizar Gradle (`Sync Now`).
4.  Ejecutar en un emulador o dispositivo físico.
5.  Pulsar **"Inicializar Usuarios"** para descargar datos.
6.  Pulsar **"Replicar Usuarios"** para subirlos a tu API (observa el Logcat para ver el progreso).

---

Hecho con 🩸 y Kotlin.
