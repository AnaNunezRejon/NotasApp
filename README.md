<img width="1156" height="638" alt="image" src="https://github.com/user-attachments/assets/aa831d04-6c9d-40bb-826c-40423af4fe1c" />

# 📒 NotasApp – Aplicación Android (DAM)

Aplicación Android desarrollada en **Java** como proyecto práctico del ciclo
**Desarrollo de Aplicaciones Multiplataforma (DAM)**.

La aplicación permite el registro de usuario por pasos, gestión de notas
y personalización de la experiencia mediante almacenamiento local.

---

## 🚀 Funcionalidades principales

### 👤 Usuario
- Registro de usuario dividido en fragments
- Validación de datos en tiempo real
- Persistencia de datos con SharedPreferences
- Edición de datos personales
- Cambio de contraseña
- Cierre de sesión
- Eliminación de cuenta

### 📝 Notas
- Crear, editar y eliminar notas
- Notas con colores
- Filtro por color
- Buscador por texto
- Nota fija de ejemplo
- Eliminación individual o total de notas

### 🎨 Interfaz
- Navegación mediante DrawerLayout (menú lateral)
- Uso de Fragments y Activities
- Popups personalizados
- Diseño simple y limpio

---

## 🧠 Arquitectura del proyecto

El proyecto sigue una **arquitectura MVVM simplificada**, separando lógica,
vista y modelos.

´´´java

com.example.notasapp  
│  
├── model  
│ ├── Nota  
│ └── Usuario  
│  
├── view  
│ ├── activities  
│ │ ├── MainActivity  
│ │ ├── RegistroActivity  
│ │ ├── HomeActivity
│ │ ├── DatosPersonalesActivity  
│ │ ├── CambiarContrasenaActivity  
│ │ ├── NotaActivity
│ │ └── NotaAleatoriaActivity  
│ │
│ ├── fragments
│ │ ├── FragmentInicio
│ │ ├── FragmentNombre
│ │ ├── FragmentContrasena
│ │ ├── FragmentCorreo
│ │ ├── FragmentRegistroFinal
│ │ ├── HomeFragment
│ │ ├── CrearNotaFragment
│ │ ├── EditarNotaFragment
│ │ └── FragmentNotaAleatoria
│ │
│ ├── adapter
│ │ └── NotasAdapter
│ │
│ └── popups
│ ├── PopupEliminarCuenta
│ ├── PopupEliminarNota
│ └── PopupEliminarTodasNotas
│
├── viewmodel
│ ├── RegistroViewModel
│ ├── NotasViewModel
│ └── FuncionesAuxiliares

´´´

---

## 💾 Persistencia de datos

La aplicación utiliza **SharedPreferences** para:

- Guardar datos del usuario
- Controlar sesión iniciada
- Almacenar las notas en formato JSON

El acceso está centralizado en la clase: FuncionesAuxiliares

---

## 🔐 Validaciones y seguridad

- Validación de nombre, correo y contraseña
- Prevención de inyección de código
- Control de sesión
- Sanitización de entradas
- Comprobación de contraseñas seguras

---

## 🛠️ Tecnologías utilizadas

- Java
- Android SDK
- Fragments
- ViewModel
- SharedPreferences
- RecyclerView
- Git & GitHub

---

## ✨ Autora

Ana Núñez Rejón
Estudiante de Desarrollo de Aplicaciones Multiplataforma (DAM)
Android · Java


