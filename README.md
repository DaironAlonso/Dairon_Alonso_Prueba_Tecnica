# API de Gestión de Pólizas – Módulo 2 Prueba Técnica Seguros Bolívar

Implementación del caso técnico simplificado de **API de Gestión de Pólizas** solicitado en el Módulo 2 de la prueba para el cargo de Desarrollador TI – Seguros Bolívar. [file:1]

## Objetivo

Exponer una API REST que permita gestionar pólizas y riesgos (individuales y colectivas), aplicando las reglas de negocio descritas en la prueba: listar pólizas, listar riesgos, renovar y cancelar pólizas, agregar y cancelar riesgos, y simular la integración con un CORE transaccional mediante un mock. [file:1]

---

## Stack técnico

- Java 17
- Spring Boot 4 (snapshot)
- Spring Web
- Spring Data JPA
- Base de datos H2 en memoria
- Maven

---

## Arquitectura y estructura de capas

El proyecto sigue una arquitectura en capas: `controller → service → repository → model`.

**Paquetes principales:**

- `model`
    - `Poliza`
    - `Riesgo`

- `repository`
    - `PolizaRepository`
    - `RiesgoRepository`

- `service`
    - `PolizaService`
    - `RiesgoService`

- `controller`
    - `PolizaController`
    - `RiesgoController`
    - `CoreMockController`

- `config`
    - `ApiKeyFilter` (valida el header `x-api-key`)

- `exception`
    - `BusinessException` (para errores de reglas de negocio)

- `DataLoader`
    - Carga datos de ejemplo al iniciar la aplicación.

**Relaciones de dominio:**

- Una `Poliza` puede tener múltiples `Riesgo` (uno a muchos). [file:1]
- Un `Riesgo` pertenece a una `Poliza`. [file:1]

Para evitar ciclos infinitos en el JSON (`Poliza` → `Riesgo` → `Poliza` → …) se usan:

- `@JsonManagedReference` en `Poliza.riesgos`.
- `@JsonBackReference` en `Riesgo.poliza`.

---

## Reglas de negocio implementadas

Según el enunciado de la prueba: [file:1]

- Una póliza **INDIVIDUAL** solo puede tener **1** riesgo.
- Una póliza en estado **CANCELADA** no puede ser renovada.
- La cancelación de una póliza cancela todos sus riesgos asociados.
- El agregado de riesgos solo se permite si el tipo de póliza es adecuado (validación en servicio; el caso principal es `COLECTIVA`).
- La renovación incrementa `canonMensual` y `prima` aplicando un factor `1 + IPC` recibido en el cuerpo de la petición.

---

## Seguridad

Todas las peticiones a la API deben incluir el header:

```text
x-api-key: 123456
