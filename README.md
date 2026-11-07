# 🐾 PetClinic — Pruebas Unitarias con Base de Datos

**Curso:** Construcción y Pruebas de Software  
**Docente:** Jean Marco Miranda Llanos  
**Laboratorio:** N° 11 — Pruebas Unitarias con Base de Datos (Spring Boot + JPA + JUnit 5 + Mockito)

---

## 🚀 Comandos de Ejecución

### Ejecutar todas las pruebas unitarias:
```bash
./mvnw test
```
o con Maven instalado:
```bash
mvn test
```

### Ejecutar pruebas por caso específico (para captura de evidencias):

- **Caso 1 (Vets):**
  ```bash
  mvn test -Dtest=VetServiceTest
  ```
- **Caso 2 (Owners):**
  ```bash
  mvn test -Dtest=OwnerServiceTest
  ```
- **Caso 3 (Specialties):**
  ```bash
  mvn test -Dtest=SpecialtyServiceTest
  ```
- **Caso 4 (Visits):**
  ```bash
  mvn test -Dtest=VisitServiceTest
  ```
- **Caso 5 (Types):**
  ```bash
  mvn test -Dtest=TypeServiceTest
  ```
- **Caso 6 (Vet Specialties):**
  ```bash
  mvn test -Dtest=VetSpecialtyServiceTest
  ```

---

## 👥 Integrantes y Responsabilidades

| Integrante | Rol / Caso Asignado | Rama Git | Pruebas Implementadas |
|---|---|---|---|
| **Integrante 1** | Caso 1: Vets & Caso 2: Owners | `feature/caso1-vets`, `feature/caso2-owners` | CRUD de veterinarios (`VetServiceTest`), CRUD de dueños (`OwnerServiceTest`) |
| **Integrante 2** | Caso 3: Specialties & Caso 4: Visits | `feature/caso3-specialties`, `feature/caso4-visits` | CRUD de especialidades (`SpecialtyServiceTest`), CRUD de visitas (`VisitServiceTest`) |
| **Integrante 3** | Caso 5: Types & Caso 6: VetSpecialties | `feature/caso5-types`, `feature/caso6-vet-specialties` | CRUD de tipos de mascotas (`TypeServiceTest`), Asignación y consultas de especialidades (`VetSpecialtyServiceTest`) |

---

## 🌿 Flujo Git y Resolución de Conflictos

1. Cada caso fue desarrollado en su propia rama aislada (`feature/...`).
2. Se realizaron integraciones continuas a la rama `main` mediante `merge`.
3. **Conflicto Resuelto:** Durante la integración de los casos 3 y 4 en `schema.sql` y `pom.xml`, se presentaron diferencias en dependencias de testing y restricciones de clave foránea entre `visits` y `specialties`, resolviéndose manualmente manteniendo la integridad referencial y las dependencias de JUnit 5 + Mockito.

---

## 📊 Resumen de Pruebas Unitarias

- **Total de pruebas ejecutadas:** 62
- **Fallos (Failures):** 0
- **Errores (Errors):** 0
- **Estado general:** `BUILD SUCCESS`
