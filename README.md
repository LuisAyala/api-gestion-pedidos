# API REST de Gestión de Pedidos para una Tienda

Este proyecto es una API REST funcional 
	desarrollada en: **Java 21** y **Spring Boot 3.x** 
	utilizando **Maven** como gestor de dependencias.

Objetivo: 	Gestionar el registro de clientes, productos y el procesamiento de pedidos con múltiples artículos en una relación de uno a muchos, aplicando 
			validaciones rigurosas, principios SOLID y patrones de diseño.

## Tecnologías y Herramientas Usadas
* **Java 21** (OpenJDK)
* **Spring Boot 3.x**
* **Maven**
* **PostgreSQL** (Persistencia de datos con Spring Data JPA)  y **DBeaver** como gestor de BD 
* **Lombok** (Implementación nativa del Patrón Builder)
* **JUnit 5 & Mockito** (Suite de pruebas unitarias)
* **Jakarta Validation** (Validación de datos de entrada en los DTOs)


## Aplicación de Principios de Diseño (SOLID)
* **S (Single Responsibility):**
		Separación estricta en capas independientes: `Controller` (HTTP), `Service` (Negocio), `Repository` (Datos) y `Mapper` (Transformación de DTOs).
* **O (Open/Closed):**
		Uso de interfaces para los servicios (`PedidoService`), permitiendo extender comportamientos mediante implementaciones (`PedidoServiceImpl`) sin alterar el código existente.
* **L (Sustitución de Liskov):**
		Este principio dicta que las subclases o implementaciones deben poder sustituir a sus clases base o interfaces sin romper el sistema.
		Se puede apreciar que en el Proyecto, cualquier servicio (como PedidoServiceImpl) hereda y respeta de forma exacta los contratos de su interfaz (PedidoService), por lo que Spring puede intercambiar las implementaciones de forma transparente.
* **I (Segregación de Interfaces):**
		Establece que es mejor tener interfaces pequeñas y específicas en lugar de una sola interfaz gigante.
		En el Proyecto se evidencia, por ejemplo, la interfaces de servicio (ClienteService, ProductoService, PedidoService) están perfectamente segregadas y especializadas según su dominio de negocio, evitando que un servicio deba implementar métodos que no le corresponden.
* **D (Dependency Inversion):**
		Todas las dependencias críticas de los repositorios y servicios se inyectan limpiamente a través de constructores (`@RequiredArgsConstructor` de Lombok), evitando el uso de acoplamientos rígidos.


## Configuración e Instrucciones para Ejecutar

### 1. Preparación de la Base de Datos
Asegúrese de tener instalado PostgreSQL y cree de forma manual la base de datos ejecutando el siguiente comando en su gestor SQL (pgAdmin, DBeaver o terminal):
```sql
CREATE DATABASE db_pedidos;

Las tablas y restricciones de integridad relacional se autogenerarán de forma automática al iniciar la aplicación gracias a la propiedad:
			  spring.jpa.hibernate.ddl-auto=update <-- configurada.
### 2. Compilación y Ejecución (Uso de Maven Wrapper)

Abra una terminal en la raíz del proyecto y ejecute los siguientes comandos según el flujo requerido: