# Literalura

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-Framework-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![API](https://img.shields.io/badge/API-Gutendex-purple)
![Status](https://img.shields.io/badge/Status-Completed-success)

## Descripción

Literalura es una aplicación desarrollada en **Java con Spring Boot** que permite buscar libros utilizando la **API de Gutendex**, registrar la información en una base de datos **PostgreSQL** y consultar datos sobre libros y autores mediante un menú interactivo en consola.

## Funcionalidades

* Buscar libros por título y registrar autores automáticamente.
* Listar todos los libros registrados.
* Listar todos los autores registrados.
* Listar autores vivos en un año específico.
* Listar libros por idioma.
* Mostrar estadísticas de la cantidad de libros por idioma.

## Tecnologías

* Java 17+
* Spring Boot
* Spring Data JPA
* PostgreSQL
* API REST de Gutendex
* Maven

## Instalación

Clonar el repositorio:

```
git clone https://github.com/DaigoroLovera/literalura
```

Entrar al directorio del proyecto:

```
cd literalura
```

Configurar PostgreSQL y crear la base de datos:

```
literalura_db
```

Configurar el archivo `application.properties` con tu usuario y contraseña de PostgreSQL.

Ejecutar la aplicación:

```
mvn spring-boot:run
```

## Uso

Al ejecutar la aplicación se mostrará un menú con las siguientes opciones:

1. Buscar libro por título
2. Listar libros registrados
3. Listar autores registrados
4. Listar autores vivos en determinado año
5. Listar libros por idioma
6. Mostrar cantidad de libros por idioma
7. Salir

## Autor

**Daigoro Lovera**
