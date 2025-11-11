USE tpi_usuario_credencial;

INSERT INTO usuario (eliminado, username, email, activo, fecha_registro)
VALUES
(FALSE, 'juanperez', 'juan@gmail.com', TRUE, NOW()),
(FALSE, 'maria', 'maria@gmail.com', TRUE, NOW());

INSERT INTO credencial_acceso (eliminado, hash_password, ultimo_cambio, requiere_reset, usuario_id)
VALUES
(FALSE, 'hash123', NOW(), FALSE, 1),
(FALSE, 'pass987', NOW(), TRUE, 2);
