# Talanto Java - API REST de Productos

API REST desarrollada con **Java y Spring Boot** para gestionar los productos de una tienda y sus categorías. Los datos se persisten en **MySQL** mediante **JPA/Hibernate**.

## Tecnologías

- Java
- Spring Boot (Spring Web MVC)
- Spring Data JPA / Hibernate
- MySQL
- Maven (con Maven Wrapper incluido)

## Arquitectura

El proyecto está organizado por capas dentro del paquete `com.talentoapirest.talanto_java`:

```
src/main/java/com/talentoapirest/talanto_java
├── controller   → Endpoints REST (ProductoController)
├── service      → Lógica de negocio (ProductoService)
├── repository   → Acceso a datos con JPA (ProductoRepository, CategoriaRepository)
├── model        → Entidades (Producto, Categoria)
├── exception    → Excepciones propias y manejo centralizado (GlobalExceptionHandler)
└── util         → Validaciones de datos (Validador)
```

## Requisitos previos

- JDK instalado (la versión indicada en `java.version` del archivo `pom.xml`)
- MySQL en ejecución
- Git

## Configuración de la base de datos

La conexión está definida en `src/main/resources/application.properties`:

```properties
spring.application.name=talanto-java
spring.datasource.url=jdbc:mysql://localhost:3307/tienda
spring.datasource.username=root
spring.datasource.password=
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

1. Iniciá MySQL (por defecto el proyecto espera el puerto **3307**).
2. Creá la base de datos:

```sql
CREATE DATABASE tienda;
```

3. Si tu MySQL usa otro puerto, usuario o contraseña, modificá los valores de `application.properties`.

Las tablas `producto` y `categoria` se crean automáticamente al iniciar la aplicación (`ddl-auto=update`).

## Cómo ejecutar el proyecto

1. Clonar el repositorio:

```bash
git clone https://github.com/Ajsr-27/talanto-java.git
cd talanto-java
```

2. Ejecutar la aplicación:

**Windows**

```bash
.\mvnw.cmd spring-boot:run
```

**Linux / macOS**

```bash
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

## Categorías de ejemplo

Los productos pueden asociarse a una categoría existente. Para cargar algunas categorías de prueba, ejecutá en MySQL:

```sql
USE tienda;
INSERT INTO categoria (nombre) VALUES ('Electrónica'), ('Hogar'), ('Ropa');
```

## Endpoints

Ruta base: `/productos`

| Método | Endpoint | Descripción | Respuestas |
|--------|----------|-------------|------------|
| GET | `/productos` | Lista todos los productos | 200 |
| GET | `/productos/{id}` | Busca un producto por ID | 200, 404 |
| POST | `/productos` | Crea un producto | 201, 400, 404 |
| PUT | `/productos/{id}` | Actualiza un producto existente | 200, 400, 404 |
| DELETE | `/productos/{id}` | Elimina un producto | 204, 404 |

### Códigos de respuesta

| Código | Significado |
|--------|-------------|
| 200 | OK: la operación se realizó correctamente |
| 201 | Created: el producto fue creado |
| 204 | No Content: el producto fue eliminado |
| 400 | Bad Request: datos inválidos o JSON mal formado |
| 404 | Not Found: el producto o la categoría no existe |

## Ejemplos de uso

### Crear un producto (POST `/productos`)

```json
{
  "nombre": "Auriculares Bluetooth",
  "precio": 25999.99,
  "stock": 15,
  "categoria": { "id": 1 }
}
```

Respuesta `201 Created`:

```json
{
  "id": 1,
  "nombre": "Auriculares Bluetooth",
  "precio": 25999.99,
  "stock": 15,
  "categoria": { "id": 1, "nombre": "Electrónica" }
}
```

El campo `categoria` es opcional. Si se envía, la categoría debe existir en la base de datos.

### Actualizar un producto (PUT `/productos/1`)

```json
{
  "nombre": "Auriculares Bluetooth Pro",
  "precio": 29999.99,
  "stock": 10,
  "categoria": { "id": 1 }
}
```

## Validaciones

Las validaciones se realizan en la clase `Validador` (`util`) antes de guardar o actualizar un producto:

| Campo | Regla |
|-------|-------|
| `nombre` | Obligatorio, no puede estar vacío y tiene un máximo de 100 caracteres |
| `precio` | Obligatorio y mayor a 0 |
| `stock` | No puede ser negativo |

## Manejo de errores

Los errores se manejan de forma centralizada con `@RestControllerAdvice` (`GlobalExceptionHandler`) y siempre se devuelven en formato JSON.

**Datos inválidos (400):**

```json
{
  "status": 400,
  "mensaje": "Los datos enviados no son válidos",
  "errores": {
    "nombre": "El nombre es obligatorio",
    "precio": "El precio debe ser mayor a 0"
  }
}
```

**Producto no encontrado (404):**

```json
{
  "status": 404,
  "mensaje": "El producto con ID 99 no existe."
}
```

**Categoría no encontrada (404):**

```json
{
  "status": 404,
  "mensaje": "La categoría con ID 99 no existe."
}
```

## Autor

Augusto - [@Ajsr-27](https://github.com/Ajsr-27)
