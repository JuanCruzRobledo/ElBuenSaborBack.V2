# 🍔 ElBuenSabor - Backend API

Repositorio del backend de la aplicación **ElBuenSabor**, un sistema web de gestión y pedidos para una hamburguesería, desarrollado con **Java + Spring Boot** utilizando arquitectura **hexagonal (puertos y adaptadores)**.

---

## 👥 Integrantes del equipo

- [@JuanCruzRobledo](https://www.github.com/JuanCruzRobledo) – Backend
- [@Maiten-Oviedo](https://www.github.com/Maiten-Oviedo) – Frontend
- [@isabellaromo](https://www.github.com/isabellaromo) – Frontend
- [@ambargorgon](https://www.github.com/ambargorgon) – Frontend

---

## 📦 Tecnologías utilizadas

- Java 17
- Spring Boot (Web, Security, JPA, OAuth2)
- MySQL
- JWT (Autenticación)
- WebSockets
- Swagger / OpenAPI
- MapStruct
- Lombok
- JasperReports
- Cloudinary
- MercadoPago SDK
- Arquitectura hexagonal (DDD + Clean Architecture)

---

## 📁 Estructura del proyecto

El proyecto sigue una estructura basada en **arquitectura hexagonal**, dividiendo claramente las responsabilidades entre:
- Dominio (lógica pura)
- Aplicación (casos de uso)
- Infraestructura (adapters, controladores, persistencia)

👉 Ver detalles en [ARCHITECTURE.md](./ARCHITECTURE.md)

---

## ⚙️ Instrucciones de instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/usuario/elbuensabor-back.git
cd elbuensabor-back
```

### 2. Configurar variables de entorno

Crear un archivo `.env` en la raíz del proyecto con las siguientes variables:

```
# Base de datos
DB_HOST=localhost
DB_PORT=3306
DB_NAME=elbuensabor
DB_USER=root
DB_PASSWORD=

# Google OAuth2
OAUTH_CLIENT_ID=...
OAUTH_CLIENT_SECRET=...
OAUTH_BASE_URL=http://localhost:8080

# Frontends
URL_FRONT_CLIENT=http://localhost:5173
URL_FRONT_ADMIN=http://localhost:3000

# Ngrok (para testing de pagos)
URL_NGROK=

# MercadoPago
ACCESS_TOKEN_MERCADOPAGO=...

# JWT
JWT_SECRET=...
JWT_EXPIRATION=86400000

# Cloudinary
CLOUD_NAME=...
CLOUD_API_KEY=...
CLOUD_API_SECRET=...
```

> ⚠️ Nota: Si se desea probar la integración con MercadoPago sin deploy, es necesario exponer el backend con una IP pública, por ejemplo, utilizando [Ngrok](https://ngrok.com/).

---

### 3. Levantar el proyecto

Si usás **Gradle**:

```
./gradlew bootRun
```

> ⚠️ Asegurate de tener corriendo un servidor MySQL con una base de datos llamada `elbuensabor`.

---

## 📌 Módulos implementados en el Backend

### 🔐 Módulo de Autenticación y Seguridad
- Login con JWT y Google (OAuth2)
- Registro de usuarios
- Gestión de perfiles
- Validación de roles y permisos

### 🍔 Módulo de Productos
- CRUD de artículos manufacturados
- CRUD de insumos
- CRUD de promociones
- Control de stock y stock mínimo

### 📦 Módulo de Pedidos
- Generación de pedidos
- Cambio de estados de pedido 
- WebSocket para notificaciones en tiempo real

### 👥 Módulo de Usuarios
- CRUD de clientes
- CRUD de empleados
- Asignación y control de roles

### 📄 Módulo de Facturación
- Generación de facturas en PDF (JasperReports)

### 📈 Módulo de Estadísticas y Reportes
- Ranking de productos más vendidos
- Ranking de clientes por cantidad o monto de pedidos
- Movimientos monetarios

### ☁️ Módulo de Imágenes
- Subida de imágenes a Cloudinary en Usuario
- Asociación de imágenes a productos e insumos

### 💳 Módulo de Pagos
- Integración con MercadoPago
- Validación de pagos y manejo de IPN 
- Uso de ngrok para pruebas en entorno local

> ⚠️ Esta estructura representa la lógica del backend. Algunas de estas funcionalidades ya están integradas al frontend, y otras están listas pero en espera de implementación del lado cliente.

---

## 🚧 Módulos pendientes

Aunque gran parte de la lógica ya está implementada, restan algunos detalles por completar:

- Envío de email para recuperación de contraseña o cambio de correo electrónico
- Integración final de Cloudinary en los endpoints de artículos
- Proteger dinámicamente los endpoints para que usuarios solo puedan acceder a los datos que coincidan con los suyos
- Validaciones adicionales en endpoints existentes
- Manejo de respuestas en caso de error (GlobalExceptionHandler)
- Envío de facturas por email
- Corrección en algunos CRUDs de los faltantes por implementar en el front  
---

## 📅 Última actualización Readme

📆 2025-06-19

---


