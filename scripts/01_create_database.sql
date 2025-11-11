CREATE DATABASE IF NOT EXISTS tpi_usuario_credencial;
USE tpi_usuario_credencial;

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    username VARCHAR(30) NOT NULL UNIQUE,
    email VARCHAR(120) NOT NULL UNIQUE,
    activo BOOLEAN NOT NULL,
    fecha_registro DATETIME
);

CREATE TABLE credencial_acceso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    hash_password VARCHAR(255) NOT NULL,
    ultimo_cambio DATETIME,
    requiere_reset BOOLEAN NOT NULL,
    usuario_id BIGINT UNIQUE,
    CONSTRAINT fk_usuario_cred FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
        ON DELETE CASCADE
);
