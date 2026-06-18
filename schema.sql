SQL
1. Crear la base de datos (Ejecutar este comando primero en postgres)
        CREATE DATABASE db_pedidos;
# Estrategia de creación de tablas en desarrollo
# spring.jpa.hibernate.ddl-auto=update
2. -- Nota: Si el entorno requiere creación manual de tablas, este es el orden relacional correcto:

   CREATE TABLE IF NOT EXISTS clientes (
       id       BIGSERIAL       PRIMARY KEY,
       nombre   VARCHAR(100)    NOT NULL,
       apellido VARCHAR(100)    NOT NULL,
       dni      VARCHAR(15)     NOT NULL UNIQUE,
       correo   VARCHAR(150)    NOT NULL UNIQUE,
       fecha_registro TIMESTAMP WITHOUT TIME ZONE
   );

   CREATE TABLE IF NOT EXISTS productos (
       id           BIGSERIAL       PRIMARY KEY,
       nombre       VARCHAR(150)    NOT NULL,
       descripcion  VARCHAR(500),
       precio       DOUBLE          NOT NULL,
       stock        INTEGER         NOT NULL,
       estado       BOOLEAN         NOT NULL
   );

   CREATE TABLE IF NOT EXISTS pedidos (
       id           BIGSERIAL   PRIMARY KEY,
       fecha_pedido TIMESTAMP WITHOUT TIME ZONE,
       estado       VARCHAR(50) NOT NULL,
       total        DOUBLE      NOT NULL,
       cliente_id   BIGINT      NOT NULL,
       CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id)
   );

   CREATE TABLE IF NOT EXISTS detalle_pedidos (
       id               BIGSERIAL PRIMARY KEY,
       producto_id      BIGINT NOT NULL,
       nombre_producto  VARCHAR(150) NOT NULL,
       cantidad         INTEGER NOT NULL,
       precio_unitario  DOUBLE  NOT NULL,
       subtotal         DOUBLE  NOT NULL,
       pedido_id        BIGINT NOT NULL,
       CONSTRAINT fk_detalle_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
   );