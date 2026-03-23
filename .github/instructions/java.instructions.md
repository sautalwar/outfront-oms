---
applyTo: "**/*.java"
---

# Java Code Instructions

## Language Features (Java 17)
- Use `final` for variables that shouldn't be reassigned
- Prefer streams and lambdas for collection operations
- Use records for DTOs and value objects
- Use `Optional<T>` for nullable return types — never return null from service methods
- Use switch expressions (Java 14+) for enum-based logic
- Use text blocks (Java 15+) for multi-line strings

## Spring Boot Patterns
- Use **constructor injection** — never use `@Autowired` on fields
- Annotate service methods that modify data with `@Transactional`
- Use `@Valid` on `@RequestBody` parameters for input validation
- Return `ResponseEntity<T>` from controller methods with explicit status codes

## Layered Architecture (strict)
- **Controller** → HTTP routing, request/response mapping, validation triggering
- **Service** → Business logic, transaction boundaries, orchestration
- **Repository** → Data access only, no business logic
- **Exception** → Custom exceptions + @ControllerAdvice for error handling

## Naming
- Classes: PascalCase (`OrderService`, `InventoryController`)
- Methods: camelCase (`getOrderById`, `findLowStockItems`)
- Constants: UPPER_SNAKE_CASE (`MAX_ORDER_QUANTITY`)
- Test methods: `shouldDoSomething_whenCondition`

## Testing
- Use `@WebMvcTest` for controller tests with MockMvc
- Use `@ExtendWith(MockitoExtension.class)` for service unit tests
- Use `@SpringBootTest` sparingly — only for integration tests
- One behavior per test method
- Follow Given / When / Then (Arrange-Act-Assert) pattern

## Logging
- Use SLF4J (`private static final Logger log = LoggerFactory.getLogger(...)`)
- Log at INFO for business events (order created, stock adjusted)
- Log at WARN for recoverable issues
- Log at ERROR for unrecoverable failures
- Never use `System.out.println` or `System.err.println`
