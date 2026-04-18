---
name: CRUD Endpoint Creation
confidence: high
domain: api-development
---

# CRUD Endpoint Creation Skill

Reusable pattern for adding a new CRUD REST endpoint to the OutFront OMS Spring Boot application. Every new domain entity follows the **Model → Repository → Service → Controller** layering.

## Pattern

### Step 1 — Create the Entity (`model/{Entity}.java`)

- Annotate with `@Entity` and `@Table(name = "table_name")`.
- Use `@Id` + `@GeneratedValue(strategy = GenerationType.IDENTITY)` for the primary key.
- Use `@Column(nullable = false)` for required fields.
- Use `@Enumerated(EnumType.STRING)` for status enums.
- Provide a no-arg constructor (required by JPA) and a convenience constructor.
- Define a nested `enum` for status fields (e.g., `OrderStatus`).

```java
package com.outfront.workshop.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "widgets")
public class Widget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WidgetStatus status = WidgetStatus.ACTIVE;

    public enum WidgetStatus { ACTIVE, INACTIVE }

    public Widget() {}

    public Widget(final String name, final int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    // Getters and setters …
}
```

### Step 2 — Create the Repository (`repository/{Entity}Repository.java`)

- Extend `JpaRepository<Entity, Long>`.
- Annotate with `@Repository`.
- Add derived query methods for common filters.

```java
package com.outfront.workshop.repository;

import com.outfront.workshop.model.Widget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WidgetRepository extends JpaRepository<Widget, Long> {

    List<Widget> findByStatus(Widget.WidgetStatus status);

    List<Widget> findByNameContainingIgnoreCase(String name);
}
```

### Step 3 — Create the Service (`service/{Entity}Service.java`)

- Annotate with `@Service`.
- Use **constructor injection** — never `@Autowired` on fields.
- Return `Optional<T>` for single-entity lookups (never return `null`).
- Add `@Transactional` on methods that write data; `@Transactional(readOnly = true)` on read-only methods.
- Throw `RuntimeException` (or a custom exception) with a descriptive message for not-found / business-rule violations.

```java
package com.outfront.workshop.service;

import com.outfront.workshop.model.Widget;
import com.outfront.workshop.repository.WidgetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WidgetService {

    private final WidgetRepository widgetRepository;

    public WidgetService(final WidgetRepository widgetRepository) {
        this.widgetRepository = widgetRepository;
    }

    @Transactional(readOnly = true)
    public List<Widget> getAllWidgets() {
        return widgetRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Widget> getWidgetById(final Long id) {
        return widgetRepository.findById(id);
    }

    @Transactional
    public Widget createWidget(final Widget widget) {
        widget.setStatus(Widget.WidgetStatus.ACTIVE);
        return widgetRepository.save(widget);
    }

    @Transactional
    public Widget updateWidget(final Long id, final Widget updates) {
        final Widget existing = widgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Widget not found: " + id));
        existing.setName(updates.getName());
        existing.setQuantity(updates.getQuantity());
        return widgetRepository.save(existing);
    }

    @Transactional
    public void deleteWidget(final Long id) {
        if (!widgetRepository.existsById(id)) {
            throw new RuntimeException("Widget not found: " + id);
        }
        widgetRepository.deleteById(id);
    }
}
```

### Step 4 — Create the Controller (`controller/{Entity}Controller.java`)

- Annotate with `@RestController` and `@RequestMapping("/api/{entities}")`.
- Use **constructor injection**.
- Controllers contain **no business logic** — delegate everything to the service.
- Return `ResponseEntity<T>` with the correct HTTP status code.

```java
package com.outfront.workshop.controller;

import com.outfront.workshop.model.Widget;
import com.outfront.workshop.service.WidgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/widgets")
public class WidgetController {

    private final WidgetService widgetService;

    public WidgetController(final WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @GetMapping
    public List<Widget> getAllWidgets() {
        return widgetService.getAllWidgets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Widget> getWidgetById(@PathVariable final Long id) {
        return widgetService.getWidgetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Widget> createWidget(@RequestBody final Widget widget) {
        final Widget created = widgetService.createWidget(widget);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWidget(@PathVariable final Long id,
                                          @RequestBody final Widget widget) {
        try {
            final Widget updated = widgetService.updateWidget(id, widget);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWidget(@PathVariable final Long id) {
        try {
            widgetService.deleteWidget(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
```

## Standard HTTP Methods & Status Codes

| Operation | Method | Path               | Success Status      |
|-----------|--------|--------------------|---------------------|
| List all  | GET    | `/api/{entities}`  | `200 OK`            |
| Get by ID | GET    | `/api/{entities}/{id}` | `200 OK` / `404 Not Found` |
| Create    | POST   | `/api/{entities}`  | `201 Created`       |
| Update    | PUT    | `/api/{entities}/{id}` | `200 OK` / `400 Bad Request` |
| Delete    | DELETE | `/api/{entities}/{id}` | `204 No Content` / `404 Not Found` |

## Error Handling Pattern

The current codebase catches `RuntimeException` in controllers and returns error maps. The recommended evolution is to introduce custom exceptions and a `@ControllerAdvice` handler:

```java
// exception/WidgetNotFoundException.java
public class WidgetNotFoundException extends RuntimeException {
    public WidgetNotFoundException(final Long id) {
        super("Widget not found: " + id);
    }
}

// exception/GlobalExceptionHandler.java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WidgetNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(final WidgetNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", ex.getMessage(),
                "timestamp", java.time.Instant.now()
        ));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(final RuntimeException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                "error", ex.getMessage(),
                "timestamp", java.time.Instant.now()
        ));
    }
}
```

## Testing Pattern

See the companion skill [`test-writing.skill.md`](test-writing.skill.md) for full details. In summary, every new endpoint should have:

1. **Controller integration tests** — `@SpringBootTest` + `@AutoConfigureMockMvc` with `MockMvc`.
2. **Service unit tests** — `@ExtendWith(MockitoExtension.class)` with `@Mock` / `@InjectMocks`.

## Seed Data

Add representative rows to `src/main/resources/data.sql` so the new entity is available on startup:

```sql
INSERT INTO widgets (name, quantity, status) VALUES ('Billboard Frame 48x14', 25, 'ACTIVE');
INSERT INTO widgets (name, quantity, status) VALUES ('LED Module 96x48', 10, 'ACTIVE');
```

## Checklist

After creating a new CRUD endpoint, verify:

- [ ] Entity class has `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, and a no-arg constructor.
- [ ] Repository extends `JpaRepository<Entity, Long>` and is annotated with `@Repository`.
- [ ] Service uses constructor injection, returns `Optional` for single lookups, and handles not-found cases.
- [ ] Service write methods are annotated with `@Transactional`.
- [ ] Controller uses `@RestController`, `@RequestMapping`, and returns `ResponseEntity` with correct status codes.
- [ ] Controller contains **zero** business logic — all logic lives in the service.
- [ ] Seed data added to `data.sql`.
- [ ] Controller integration tests cover: list, get-by-id, 404, create, update, delete.
- [ ] Service unit tests cover: happy paths, not-found, business rules.
- [ ] `mvn clean verify -B` passes with no failures.
