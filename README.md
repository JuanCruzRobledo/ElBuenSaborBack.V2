# 🍔 ElBuenSabor - Backend API

Repositorio del backend de la aplicación ElBuenSabor, desarrollado con Java + Spring Boot y arquitectura hexagonal (puertos y adaptadores).

---

## 👥 Integrantes del equipo

- [@JuanCruzRobledo](https://www.github.com/JuanCruzRobledo)
- [@Maiten-Oviedo](https://www.github.com/Maiten-Oviedo)
- [@isabellaromo](https://www.github.com/isabellaromo)
- [@ambargorgon](https://www.github.com/ambargorgon)
## 📁 Estructura del proyecto

Estamos usando **Arquitectura Hexagonal (DDD + Clean)** para separar claramente las responsabilidades del sistema.
- 👉 Ver detalles en [ARCHITECTURE.md](./ARCHITECTURE.md)
---

## 📦 Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- MapStruct
- Lombok
- Swagger / OpenAPI
- JWT Security

---

## ⚙️ Cómo levantar el proyecto

```bash
git clone https://github.com/usuario/elbuensabor-back.git
cd elbuensabor-back
./mvnw spring-boot:run