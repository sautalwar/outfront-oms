---
name: Test Writing
confidence: high
domain: testing
---

# Test Writing Skill

Reusable patterns for writing tests in the OutFront OMS Spring Boot project. Two categories: **controller integration tests** (MockMvc + H2) and **service unit tests** (Mockito).

## Naming Convention

All test methods follow the pattern:

```
shouldDoSomething_whenCondition
```

Examples:
- `shouldReturnAllOrders()`
- `shouldReturnOrder_whenValidIdProvided()`
- `shouldReturn404_whenOrderNotFound()`
- `shouldThrowException_whenInsufficientInventory()`

## Coverage Requirements

Every endpoint or service method should have tests for:

| Category      | Description                                      |
|---------------|--------------------------------------------------|
| Happy path    | Valid input produces the expected result          |
| Not found     | Request for a non-existent resource returns 404   |
| Validation    | Invalid or missing fields are rejected            |
| Business rule | Domain-specific constraints are enforced           |
| Edge cases    | Empty lists, boundary values, duplicate data       |

---

## Controller Integration Tests

Use `@SpringBootTest` + `@AutoConfigureMockMvc` to run against the full Spring context with an H2 in-memory database seeded by `data.sql`.

### Setup

```java
package com.outfront.workshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outfront.workshop.model.Widget;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
```

### Test: List All

```java
@Test
void shouldReturnAllWidgets() throws Exception {
    mockMvc.perform(get("/api/widgets"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
}
```

### Test: Get By ID

```java
@Test
void shouldReturnWidgetById() throws Exception {
    mockMvc.perform(get("/api/widgets/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Billboard Frame 48x14"));
}
```

### Test: 404 Not Found

```java
@Test
void shouldReturn404_whenWidgetNotFound() throws Exception {
    mockMvc.perform(get("/api/widgets/999"))
            .andExpect(status().isNotFound());
}
```

### Test: Create

```java
@Test
void shouldCreateWidget() throws Exception {
    final Widget widget = new Widget("Test Widget", 5);

    mockMvc.perform(post("/api/widgets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(widget)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Test Widget"))
            .andExpect(jsonPath("$.status").value("ACTIVE"));
}
```

### Test: Update

```java
@Test
void shouldUpdateWidget() throws Exception {
    mockMvc.perform(put("/api/widgets/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\": \"Updated Name\", \"quantity\": 50}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Name"));
}
```

### Test: Delete

```java
@Test
void shouldDeleteWidget() throws Exception {
    // Create a throwaway entity first to avoid breaking seed data
    final Widget widget = new Widget("Delete Me", 1);
    final String response = mockMvc.perform(post("/api/widgets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(widget)))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();

    final Long id = objectMapper.readTree(response).get("id").asLong();

    mockMvc.perform(delete("/api/widgets/" + id))
            .andExpect(status().isNoContent());

    // Confirm it is gone
    mockMvc.perform(get("/api/widgets/" + id))
            .andExpect(status().isNotFound());
}
```

### Test: Filter by Query Param

```java
@Test
void shouldFilterWidgetsByStatus() throws Exception {
    mockMvc.perform(get("/api/widgets").param("status", "ACTIVE"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].status").value("ACTIVE"));
}
```

### Full Controller Test File Template

```java
package com.outfront.workshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outfront.workshop.model.Widget;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for WidgetController.
 * Uses the full Spring context with an H2 database seeded by data.sql.
 */
@SpringBootTest
@AutoConfigureMockMvc
class WidgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllWidgets() throws Exception { /* … */ }

    @Test
    void shouldReturnWidgetById() throws Exception { /* … */ }

    @Test
    void shouldReturn404_whenWidgetNotFound() throws Exception { /* … */ }

    @Test
    void shouldCreateWidget() throws Exception { /* … */ }

    @Test
    void shouldUpdateWidget() throws Exception { /* … */ }

    @Test
    void shouldDeleteWidget() throws Exception { /* … */ }

    @Test
    void shouldFilterWidgetsByStatus() throws Exception { /* … */ }
}
```

---

## Service Unit Tests

Use `@ExtendWith(MockitoExtension.class)` to test service logic in isolation, mocking repository dependencies.

### Setup

```java
package com.outfront.workshop.service;

import com.outfront.workshop.model.Widget;
import com.outfront.workshop.repository.WidgetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
```

### Test: Get All

```java
@Test
void shouldReturnAllWidgets() {
    when(widgetRepository.findAll()).thenReturn(List.of(new Widget("W1", 10)));

    final List<Widget> result = widgetService.getAllWidgets();

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getName()).isEqualTo("W1");
    verify(widgetRepository).findAll();
}
```

### Test: Get By ID — Found

```java
@Test
void shouldReturnWidget_whenIdExists() {
    final Widget widget = new Widget("W1", 10);
    when(widgetRepository.findById(1L)).thenReturn(Optional.of(widget));

    final Optional<Widget> result = widgetService.getWidgetById(1L);

    assertThat(result).isPresent();
    assertThat(result.get().getName()).isEqualTo("W1");
}
```

### Test: Get By ID — Not Found

```java
@Test
void shouldReturnEmpty_whenIdDoesNotExist() {
    when(widgetRepository.findById(999L)).thenReturn(Optional.empty());

    final Optional<Widget> result = widgetService.getWidgetById(999L);

    assertThat(result).isEmpty();
}
```

### Test: Create

```java
@Test
void shouldCreateWidget() {
    final Widget input = new Widget("New Widget", 5);
    when(widgetRepository.save(any(Widget.class))).thenReturn(input);

    final Widget result = widgetService.createWidget(input);

    assertThat(result.getName()).isEqualTo("New Widget");
    verify(widgetRepository).save(input);
}
```

### Test: Delete — Not Found

```java
@Test
void shouldThrowException_whenDeletingNonExistentWidget() {
    when(widgetRepository.existsById(999L)).thenReturn(false);

    assertThatThrownBy(() -> widgetService.deleteWidget(999L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("not found");

    verify(widgetRepository, never()).deleteById(any());
}
```

### Full Service Test File Template

```java
package com.outfront.workshop.service;

import com.outfront.workshop.model.Widget;
import com.outfront.workshop.repository.WidgetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for WidgetService.
 * Repository dependencies are mocked — no Spring context needed.
 */
@ExtendWith(MockitoExtension.class)
class WidgetServiceTest {

    @Mock
    private WidgetRepository widgetRepository;

    @InjectMocks
    private WidgetService widgetService;

    @Test
    void shouldReturnAllWidgets() { /* … */ }

    @Test
    void shouldReturnWidget_whenIdExists() { /* … */ }

    @Test
    void shouldReturnEmpty_whenIdDoesNotExist() { /* … */ }

    @Test
    void shouldCreateWidget() { /* … */ }

    @Test
    void shouldThrowException_whenDeletingNonExistentWidget() { /* … */ }
}
```

---

## Assertion Style

Prefer **AssertJ** (`assertThat`) over JUnit's `assertEquals`/`assertTrue`:

```java
// ✅ Preferred — AssertJ
assertThat(result).isNotNull();
assertThat(result.getName()).isEqualTo("Expected");
assertThat(items).hasSize(3).extracting("name").contains("Widget A");
assertThatThrownBy(() -> service.delete(999L))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("not found");

// ❌ Avoid — plain JUnit assertions
assertNotNull(result);
assertEquals("Expected", result.getName());
```

For MockMvc response assertions, use Hamcrest matchers (they integrate with `jsonPath`):

```java
.andExpect(jsonPath("$.name").value("Widget A"))
.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
.andExpect(jsonPath("$.status").value("ACTIVE"))
```

## Checklist

After writing tests, verify:

- [ ] Test class is in `src/test/java/com/outfront/workshop/` (controller tests) or `src/test/java/com/outfront/workshop/service/` (service tests).
- [ ] Test names follow `shouldDoSomething_whenCondition` pattern.
- [ ] Happy path, 404/not-found, and at least one business-rule test are covered.
- [ ] Controller tests use `@SpringBootTest` + `@AutoConfigureMockMvc` (not `@WebMvcTest`).
- [ ] Service tests use `@ExtendWith(MockitoExtension.class)` with `@Mock` / `@InjectMocks`.
- [ ] Assertions use AssertJ (`assertThat`) for service tests, Hamcrest for MockMvc JSON.
- [ ] Delete tests create their own data rather than relying on seed data IDs.
- [ ] `mvn test` passes with no failures.
