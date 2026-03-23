---
mode: agent
description: "Add a new REST endpoint to the OMS"
tools:
  - codebase
  - terminal
---

Create a new REST API endpoint for {{resource}} in the OutFront OMS.

Follow this checklist:

1. **Model/Entity** — Create or update the JPA entity in `model/` package
   - Use `@Entity`, `@Table`, `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`
   - Add Bean Validation annotations (`@NotBlank`, `@Positive`, etc.)
   - Include `createdAt` / `updatedAt` timestamps

2. **Repository** — Create interface extending `JpaRepository` in `repository/` package
   - Add derived query methods as needed
   - Use `@Query` for complex queries

3. **Service** — Create service class in `service/` package
   - Use constructor injection
   - Add `@Transactional` on write methods
   - Throw `ResourceNotFoundException` for missing entities
   - Log business events with SLF4J

4. **Controller** — Create REST controller in `controller/` package
   - Use `@RestController` and `@RequestMapping("/api/{{resource}}")`
   - Return `ResponseEntity<T>` with proper HTTP status codes
   - Use `@Valid` on request bodies

5. **Tests** — Write tests:
   - Controller test with `@WebMvcTest` and MockMvc
   - Service test with `@ExtendWith(MockitoExtension.class)`

6. **Seed Data** — Add sample data to `data.sql`

7. **Verify** — Run `mvn test` to ensure everything passes
