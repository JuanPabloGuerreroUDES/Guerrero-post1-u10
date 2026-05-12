# Guerrero-post1-u10
## Juan Pablo Guerrero Hernandez - 02230132029
## Suite de Pruebas con JUnit 5, Mockito y JaCoCo
**Programación Web — Unidad 10 | Ingeniería de Sistemas — UDES 2026**

---

## Descripción del Proyecto

Aplicación Spring Boot de gestión de tareas que implementa una suite completa de pruebas automatizadas con:
- **JUnit 5 + Mockito** para pruebas unitarias de la capa de servicio
- **@WebMvcTest** para pruebas de la capa de controladores
- **@DataJpaTest** para pruebas de la capa de repositorios con H2 en memoria
- **JaCoCo** para medición y verificación de cobertura de código (≥ 70 %)

---

## Prerrequisitos

| Herramienta | Versión mínima |
|-------------|----------------|
| Java JDK    | 17+            |
| Maven       | 3.9.x          |
| IDE         | IntelliJ IDEA / VS Code + Extension Pack for Java |

---

## Estructura del Proyecto

```
Guerrero-post1-u10/
├── src/
│   ├── main/
│   │   ├── java/com/guerrero/tareas/
│   │   │   ├── TareasApplication.java          # Clase principal Spring Boot
│   │   │   ├── entity/Tarea.java               # Entidad JPA
│   │   │   ├── repository/TareaRepository.java # Repositorio JPA
│   │   │   ├── service/TareaService.java        # Lógica de negocio
│   │   │   ├── controller/TareaController.java  # REST Controller
│   │   │   └── exception/GlobalExceptionHandler.java
│   │   └── resources/
│   │       └── application.properties           # Config H2 en memoria
│   └── test/
│       └── java/com/guerrero/tareas/
│           ├── service/TareaServiceTest.java    # Checkpoint 1: Mockito
│           ├── controller/TareaControllerTest.java # Checkpoint 2: @WebMvcTest
│           └── repository/TareaRepositoryTest.java # Checkpoint 2: @DataJpaTest
└── pom.xml                                      # Dependencias + JaCoCo plugin
```

---

## Cómo Ejecutar

### Ejecutar todos los tests
```bash
mvn clean test
```

### Ejecutar solo una clase de prueba
```bash
mvn test -Dtest=TareaServiceTest
mvn test -Dtest=TareaControllerTest
mvn test -Dtest=TareaRepositoryTest
```

### Generar reporte JaCoCo y verificar cobertura ≥ 70 %
```bash
mvn clean test jacoco:check
```

### Ver reporte HTML de cobertura
Después de ejecutar `mvn clean test`, abrir en el navegador:
```
target/site/jacoco/index.html
```

---

## Clases de Prueba

### Checkpoint 1 — `TareaServiceTest`
Pruebas **unitarias** de `TareaService` con `@ExtendWith(MockitoExtension.class)`:
- `crear_conTituloValido_guardaYRetorna` — verifica que el servicio guarda y retorna la tarea
- `crear_conTituloVacio_lanzaIllegalArgumentException` — verifica que el mock nunca es invocado con título en blanco
- `crear_conTituloNull_lanzaIllegalArgumentException` — verifica validación de nulos
- `buscarPorId_existeTarea_retornaTarea` — stub del repositorio para tarea existente
- `buscarPorId_noExiste_lanzaEntityNotFoundException` — stub `Optional.empty()`
- `completar_tareaExistente_marcaComoCompletada` — verifica que `setCompletada(true)` se persiste
- `completar_tareaNoExiste_lanzaEntityNotFoundException` — verifica que `save` nunca se llama

### Checkpoint 2 — `TareaControllerTest`
Pruebas de la **capa web** con `@WebMvcTest` y `MockMvc`:
- `get_tareaExiste_retorna200` — GET /api/tareas/1 → 200 OK con JSON
- `get_noExiste_retorna404` — GET /api/tareas/99 → 404 Not Found
- `getAll_retornaListaYStatus200` — GET /api/tareas → lista con 2 elementos
- `post_tareaValida_retorna201` — POST /api/tareas → 201 Created
- `patch_completar_tareaExistente_retorna200` — PATCH /api/tareas/1/completar → 200

### Checkpoint 2 — `TareaRepositoryTest`
Pruebas de la **capa de repositorio** con `@DataJpaTest` y H2 en memoria:
- `findByCompletada_false_retornaUnaTarea` — query derivada para tareas pendientes
- `findByCompletada_true_retornaUnaTarea` — query derivada para tareas completadas
- `save_nuevaTarea_persisteCorrectamente` — persiste y asigna ID
- `findById_existente_retornaTarea` — búsqueda por ID exitosa
- `findById_noExistente_retornaVacio` — Optional vacío para ID inexistente
- `delete_tareaExistente_eliminaCorrectamente` — eliminación verificada

> **Nota:** `@DataJpaTest` usa H2 en memoria y revierte los cambios automáticamente entre tests.

---

## Configuración JaCoCo

El plugin `jacoco-maven-plugin 0.8.11` está configurado en `pom.xml` con:
- **prepare-agent**: instrumenta el bytecode para medir cobertura
- **report** (fase `test`): genera reporte HTML en `target/site/jacoco/`
- **check**: falla el build si la cobertura de líneas en el bundle es < 70 %

Clases excluidas de la verificación:
- `**/*Application.class`
- `**/entity/**`

---

## Evidencia de Cobertura

Tras ejecutar `mvn clean test`, el reporte en `target/site/jacoco/index.html` muestra:
- **Líneas verdes**: cubiertas por los tests
- **Líneas rojas**: no cubiertas
- **Líneas amarillas**: ramas parcialmente cubiertas

La cobertura del paquete `service` debe ser ≥ 70 % para que `jacoco:check` pase.
