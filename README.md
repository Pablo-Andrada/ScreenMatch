![Programação-Java_ Persistencia de datos y consultas con Spring Data JPA](https://github.com/genesysR-dev/2066-java-persitencia-de-datos-y-consultas-con-Spring-JPA/assets/91544872/e0e3a9f8-afc7-4e7b-be83-469351ef2d70)

# ScreenMatch

Proyecto desarrollado durante el segundo curso de la formación Avanzando con Java de Alura

## 🔨 Objetivos del proyecto

* Avanzar en el proyecto Screenmatch, iniciado en el primer curso de la formación, creando un menú con varias opciones;
* Modelar las abstracciones de la aplicación a través de clases, enums, atributos y métodos;
* Consumir la API del ChatGPT(Opcional;
* Utilizar Spring Data JPA para persistir datos en la base de datos;
* Conocer varios tipos de bases de datos y utilizar PostgreSQL;
* Trabajar con varios tipos de consultas a la base de datos;
* Profundizar en la interfaz JPA Repository.

## ScreenMatch Back-end Overview

Este documento describe la estructura, el flujo de información y el rol de cada componente en el back-end de la aplicación **ScreenMatch**.

---

### 📁 Estructura de paquetes

```
com.aluracursos.screenmatch
├── ScreenmatchApplication.java
├── configuration
│   └── CorsConfiguration.java
├── controller
│   └── SerieController.java
├── dto
│   ├── SerieDTO.java
│   └── EpisodioDTO.java
├── model
│   ├── Serie.java
│   ├── Episodio.java
│   ├── Categoria.java
│   ├── DatosSerie.java
│   ├── DatosTemporadas.java
│   └── DatosEpisodio.java
├── repository
│   └── SerieRepository.java
├── service
│   ├── SerieService.java
│   ├── ConsumoAPI.java
│   ├── ConvierteDatos.java
│   └── IConvierteDatos.java
└── principal
    └── Principal.java
```

---

### 🔍 Descripción de componentes

#### 1. ScreenmatchApplication.java

* **Clase de arranque** de Spring Boot.
* Contiene `main()` que inicia el contexto de Spring.

#### 2. CorsConfiguration.java

* **Configuración CORS** para permitir peticiones desde el front (`http://127.0.0.1:5500`).
* Define orígenes y métodos HTTP permitidos.

#### 3. SerieController.java

* **Controlador REST** para rutas de series.
* Expone endpoints (`GET`) para:

  * Listar series (`/series`).
  * Top 5 (`/series/top5`).
  * Últimos lanzamientos (`/series/lanzamientos`).
  * Detalle de serie (`/series/{id}`).
  * Temporadas completas (`/series/{id}/temporadas/todas`).
  * Temporada específica (`/series/{id}/temporadas/{n}`).
  * Filtrar por género (`/series/categoria/{genero}`).

#### 4. DTOs (`SerieDTO`, `EpisodioDTO`)

* **Objetos de transferencia** para enviar datos ligeros al front.
* Rompen ciclos de serialización y evitan exponer entidades completas.

#### 5. Modelos JPA

* **Serie.java**: Entidad `series` con campos (id, título, totalTemporadas, evaluación, poster, género, actores, sinopsis) y relación a `Episodio`.
* **Episodio.java**: Entidad `episodios` con campos (id, temporada, título, númeroEpisodio, evaluación, fechaDeLanzamiento) y relación a `Serie`.
* **Categoria.java**: Enum con mapeo entre géneros OMDB y español.
* **DatosSerie**, **DatosTemporadas**, **DatosEpisodio**: Clases para parsear JSON de OMDB.

#### 6. SerieRepository.java

* **Interfaz JPA** (`JpaRepository<Serie,Long>`).
* Métodos CRUD genéricos y consultas personalizadas:

  * `findByTituloContainsIgnoreCase`
  * `findTop5ByOrderByEvaluacionDesc`
  * `findByGenero`
  * Queries para filtrado por temporadas/evaluación, búsqueda de episodios, top episodios, lanzamientos recientes.

#### 7. SerieService.java

* **Capa de negocio**.
* Llama al repositorio, transforma entidades a DTOs.
* Actualmente solo lee BD; puede ampliarse para poblar episodios desde OMDB.

#### 8. ConsumoAPI & ConvierteDatos

* **ConsumoAPI**: Llama a OMDB via `HttpClient`.
* **ConvierteDatos**: Mapea JSON a objetos Java usando Jackson.
* Usados por la clase de consola **Principal**, no en REST aún.

#### 9. Principal.java

* **Consola CLI** para buscar series/episodios en OMDB y guardarlos en BD.
* No forma parte del servidor web.

---

### 🔄 Flujo de información (REST)

1. El **front** llama a un endpoint (e.g. `GET /series/{id}/temporadas/todas`).
2. **CorsConfiguration** autoriza la petición.
3. **SerieController** recibe el request y delega a **SerieService**.
4. **SerieService** consulta **SerieRepository** (JPA/Hibernate) para obtener la entidad y sus episodios (fetch EAGER).
5. Convierte entidades a DTOs.
6. Spring serializa a JSON.
7. El **front** recibe y renderiza la data.

---
