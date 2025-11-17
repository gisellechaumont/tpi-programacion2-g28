📌 Trabajo Práctico Integrador – Programación II
Sistema de Gestión de Usuarios y Credenciales

TP Grupo 28 – UTN – Tecnicatura Universitaria en Programación – Comisión 12 (2025)

🧩 Descripción General

Este proyecto implementa un Sistema de Gestión de Usuarios y Credenciales de Acceso desarrollado en Java, utilizando MySQL como base de datos y aplicando una arquitectura completamente modular basada en:

Patrón MVC + Capas (Main → Service → DAO → DB)

JDBC con PreparedStatement

CRUD completo

Transacciones con commit/rollback

Soft delete

Reglas de negocio (email único, username único, relación 1:1)

El objetivo es simular un sistema real y demostrar dominio de programación estructurada, lógica de negocio y persistencia con base de datos.

🏗️ Arquitectura del Proyecto

El proyecto aplica una arquitectura por capas:

main → Entrada de la aplicación y menú interactivo

service → Lógica de negocio, validaciones, transacciones

dao → Acceso a datos con JDBC

models → Entidades mapeadas a la base de datos

config → Configuración de la conexión MySQL

📁 Estructura del Proyecto

```text
src/
 └─ main/
     └─ java/
         ├─ main/
         │   ├─ PuntoDeEntrada.java
         │   └─ CrudController.java
         │
         ├─ config/
         │   └─ DatabaseConnection.java
         │
         ├─ service/
         │   ├─ GenericService.java
         │   ├─ UsuarioService.java
         │   └─ CredencialAccesoService.java
         │
         ├─ dao/
         │   ├─ GenericDAO.java
         │   ├─ UsuarioDao.java
         │   └─ CredencialAccesoDAO.java
         │
         └─ models/
             ├─ Usuario.java
             └─ CredencialAcceso.java
```

🗃️ Base de Datos – tpi_usuario_credencial
Tabla: usuario

id (PK)

username

email

activo

fecha_registro

credencial_acceso_id (FK → credencial_acceso.id)

eliminado (soft delete)

Tabla: credencial_acceso

id (PK)

hash_password

ultimo_cambio

requiere_reset

eliminado (soft delete)

🔌 Configuración de Conexión (DatabaseConnection)

El archivo DatabaseConnection.java está preparado para ser subido a GitHub sin exponer credenciales.

private static final String URL =
System.getProperty("db.url", "jdbc:mysql://localhost:3306/tpi_usuario_credencial");

private static final String USER =
System.getProperty("db.user", "root");

private static final String PASSWORD =
System.getProperty("db.password", "");

✔ No contiene datos sensibles
✔ Compatible con cualquier entorno (cada usuario configura sus properties)
✔ Seguro para subir al repositorio

▶️ Cómo Ejecutar el Proyecto

Abrir el proyecto en IntelliJ IDEA

Levantar MySQL (Workbench, XAMPP, WAMP, etc.)

Crear la base:

CREATE DATABASE tpi_usuario_credencial;
Ejecutar los scripts SQL del grupo:

01_create_database.sql

02_insert_data.sql

Ejecutar:

main → PuntoDeEntrada.java

🎛️ Menú por Consola

====== MENÚ PRINCIPAL ======
1. CRUD Usuarios
2. CRUD Credenciales
3. Crear Usuario + Credencial (Transacción)
0. Salir

Cada CRUD incluye:

Crear

Listar

Buscar por ID

Actualizar

Eliminar (soft delete)

✔️ Pruebas recomendadas para el Informe

Estas pruebas deben incluir capturas en el PDF final:

1. Crear una Credencial

   hashPassword: test123
   requiereReset: false

2. Crear un Usuario

username: prueba1
email: prueba@gmail.com
activo: true

3. Crear Usuario + Credencial (Transacción)

(Opción 3 del menú)

4. Buscar por ID

Captura de consola y luego en SQL:

SELECT * FROM usuario WHERE id = X;

5. Actualizar registro

Ej: cambiar username o hashPassword
Verificar en SQL:

SELECT * FROM usuario;

6. Eliminar (soft delete)

Validar:

SELECT * FROM usuario WHERE eliminado = 0;

7. Listados

SELECT * FROM credencial_acceso;
SELECT * FROM usuario;

🙋‍♀️ Integrantes

Daniela Bonetti

Giselle Chaumont

Erica Bustamante

Agustina Cruz

🏫 Universidad Tecnológica Nacional

Tecnicatura Universitaria en Programación
Programación II – Comisión 12 – Año 2025


