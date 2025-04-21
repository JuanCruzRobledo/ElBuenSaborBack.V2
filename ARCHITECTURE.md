# 🧱 Arquitectura del Proyecto - ElBuenSabor (Backend)

Este documento describe la arquitectura del backend desarrollado con Java y Spring Boot siguiendo los principios de **Arquitectura Hexagonal** (también conocida como "Puertos y Adaptadores") y **DDD (Domain-Driven Design)**.

---

## 🔄 Estilo Arquitectónico

Utilizamos una combinación de:

- **Arquitectura Hexagonal**: separación clara entre el núcleo de negocio y los detalles técnicos (como bases de datos, APIs externas, frameworks).
- **DDD**: el modelo de dominio es el corazón del sistema, basado en conceptos del negocio real.
- **Clean Architecture (inspiración)**: separación por capas con dependencias dirigidas hacia el dominio.

---

## 📁 Estructura de paquetes

```text
src/
└── main/
    └── java/
        └── org.mija.elbuensaborback/
            ├── application/                     → Casos de uso (orquestación de lógica de negocio)
            │   ├── dto/                         → Objetos de transferencia
            │   │   ├── request/                 → DTOs de entrada
            │   │   └── response/                → DTOs de salida
            │   └── service/                     → Servicios de aplicación
            ├── domain/                          → Núcleo del negocio (puro, sin dependencias externas)
            │   ├── enums/                       → Enumeraciones del dominio
            │   ├── model/                       → Entidades y objetos de valor del dominio
            │   ├── repository/                  → Interfaces de persistencia (puertos de salida)
            │   └── service/                     → Servicios con lógica de negocio reutilizable
            ├── infrastructure/                  → Implementaciones tecnológicas concretas
            │   ├── configuration/               → Configuraciones generales del proyecto
            │   │   ├── app/                     → Configuración del contexto de aplicación
            │   │   └── security/                → Configuración relacionada a seguridad
            │   ├── persistence/                 → Persistencia (implementaciones JPA)
            │   │   ├── entity/                  → Entidades JPA (anotadas)
            │   │   ├── mapper/                  → Conversores entre entidades y dominio (MapStruct)
            │   │   └── repository/              → Implementaciones de interfaces del dominio
            │   └── presentation/                → Entrada del sistema (adaptadores de entrada)
            │       ├── advice/                  → Manejadores globales de excepciones
            │       └── controller/              → Controladores REST
            │           ├── admin/               → Controladores para funcionalidades administrativas
            │           └── user/                → Controladores para funcionalidades de usuario
            ├── shared.util.filters/             → Filtros reutilizables (ej: JWT, logs, CORS, etc)
            └── ElBuenSaborBackApplication.java  → Clase principal de arranque de Spring Boot
```

---

## 🧠 Reglas importantes

- Ninguna clase en `domain/` debe tener dependencia a Spring (`@Service`, `@Entity`, etc.).
- El `domain.model` representa el negocio puro, sin base de datos ni tecnología.
- El `domain.repository` son interfaces. Sus implementaciones están en `infrastructure/persistence/repository`.
- En `infrastructure` vive todo lo tecnológico (bases de datos, JWT, mail, files).
- En `application` se orquestan los **casos de uso**. No contiene lógica de presentación ni detalles de infraestructura.

---

## 🧭 Flujo de una request típica

1. 📥 `presentation/controller` recibe la petición.
2. 🎯 Llama a un **servicio de aplicación** (`application/service`).
3. 🧠 El servicio llama a:
    - Entidades del dominio
    - Repositorios (interfaces)
    - Servicios de dominio si es necesario
4. 🔌 Las interfaces se implementan en `infrastructure` usando Spring (ej. JPA).
5. 📤 El controlador devuelve una respuesta.

---

## 📌 Ejemplo práctico

---

## 🧰 Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- MapStruct
- Lombok
- JWT Security
- Swagger (OpenAPI)

---

## ✅ Convenciones del equipo

- Se usa `MapStruct` para mapear entre entidades JPA y modelos del dominio.
- Excepciones y validaciones comunes van en `shared/`.
- No se utiliza lógica de negocio en controladores.
- Los DTOs pueden ir en `presentation/dto` si son para entrada/salida.

---

## 🔗 Recursos de referencia

- [DDD Reference by Eric Evans](https://domainlanguage.com/ddd/)
- [Arquitectura Hexagonal explicada](https://reflectoring.io/spring-hexagonal/)
- [Clean Architecture by Uncle Bob](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html)

---
