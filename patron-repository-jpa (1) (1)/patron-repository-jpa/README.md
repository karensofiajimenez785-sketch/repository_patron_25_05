# Patrón Repository con JPA
**Docente:** Germán Alberto Angarita Henao – 2024  
**SENA – Desarrollo de Software**

---

## Estructura del proyecto

```
src/
├── main/
│   ├── java/com/ejemplo/
│   │   ├── config/
│   │   │   └── JPAUtil.java            ← Reemplaza DatabaseConfig
│   │   ├── entity/
│   │   │   └── UserEntity.java         ← Reemplaza UserDTO
│   │   ├── repository/
│   │   │   └── UserRepository.java     ← Mismo patrón, nuevo motor
│   │   ├── service/
│   │   │   └── UserService.java        ← Mínimos cambios
│   │   ├── validator/
│   │   │   └── UserValidator.java      ← Sin cambios
│   │   ├── exception/
│   │   │   └── InvalidUserDataException.java
│   │   └── Main.java
│   └── resources/
│       └── META-INF/
│           └── persistence.xml         ← Configuración JPA/Hibernate
└── pom.xml
```

---

## Requisitos previos

- Java 17+
- Maven 3.8+
- MySQL corriendo en `localhost:3306`

---

## Configuración de la base de datos

1. Crear la base de datos en MySQL:
   ```sql
   CREATE DATABASE testdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. Editar `src/main/resources/META-INF/persistence.xml` con tus credenciales:
   ```xml
   <property name="jakarta.persistence.jdbc.url"
             value="jdbc:mysql://localhost:3306/testdb?useSSL=false&serverTimezone=UTC"/>
   <property name="jakarta.persistence.jdbc.user"     value="TU_USUARIO"/>
   <property name="jakarta.persistence.jdbc.password" value="TU_CONTRASEÑA"/>
   ```

3. Hibernate crea la tabla `users` automáticamente (`hbm2ddl.auto=update`).

---

## Ejecutar el proyecto

```bash
# Compilar
mvn clean compile

# Ejecutar
mvn exec:java -Dexec.mainClass="com.ejemplo.Main"
```

---

## Resumen: ¿Qué cambió vs JDBC puro?

| Componente              | Estado            | Descripción                        |
|-------------------------|-------------------|------------------------------------|
| DatabaseConfig.java     | **Reemplazada**   | → JPAUtil.java + persistence.xml   |
| UserDTO.java            | **Reemplazada**   | → UserEntity.java con @Entity      |
| UserRepository.java     | **Actualizada**   | SQL manual → EntityManager         |
| UserService.java        | **Mínimos cambios** | UserDTO → UserEntity             |
| UserValidator.java      | **Sin cambios**   | Misma lógica                       |
| InvalidUserDataException| **Sin cambios**   | Misma excepción                    |
| Main.java               | **Mínimos cambios** | + JPAUtil.close() al final       |
