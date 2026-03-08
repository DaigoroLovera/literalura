# Literalura

## Descripción
Literalura es una aplicación en Java con Spring Boot que permite buscar libros en la API de Gutendex, registrarlos en una base de datos PostgreSQL, y consultar información sobre libros y autores.

## Funcionalidades
- Buscar libros por título y registrar autores automáticamente.
- Listar todos los libros registrados.
- Listar todos los autores registrados.
- Listar autores vivos en un año específico.
- Listar libros por idioma.
- Mostrar estadísticas de la cantidad de libros por idioma.

## Tecnologías
- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- API REST de Gutendex
- Maven

## Instalación
1. Clonar el repositorio:
   ```bash
   git clone <URL-del-repositorio>
2. Configurar PostgreSQL y crear la base de datos literalura_db.

3. Configurar application.properties con tu usuario y contraseña de PostgreSQL.

4. Ejecutar la aplicación: mvn spring-boot:run
   
## Uso

Al ejecutar la aplicación, se mostrará un menú con las siguientes opciones:

1. Buscar libro por título

2. Listar libros registrados

3. Listar autores registrados

4. Listar autores vivos en determinado año

5. Listar libros por idioma

6. Mostrar cantidad de libros por idioma

7. Salir 
## Autores

Daigoro Lovera