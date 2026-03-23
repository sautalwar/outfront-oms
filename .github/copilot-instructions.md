# OutFront OMS — Copilot Instructions

## Project Context
This is the **OutFront Order Management System (OMS)** — a Spring Boot REST API for managing billboard components, digital displays, and accessories across multiple warehouse locations.

## Tech Stack
- **Java 17** with **Spring Boot 3.2.5**
- **Maven** for build management
- **H2** (dev) / **SQL Server 2022** (production)
- **Spring Data JPA** with Hibernate
- **Bean Validation** (jakarta.validation)
- **SpringDoc OpenAPI** for API documentation

## Architecture
Follow strict **Controller → Service → Repository** layering:
- Controllers handle HTTP concerns only (no business logic)
- Services contain all business logic and transaction management
- Repositories extend JpaRepository with derived query methods

## Code Style
- Follow Google Java Style Guide
- Use constructor injection (no @Autowired on fields)
- Use SLF4J for logging (no System.out.println)
- Methods should be ≤ 30 lines
- Use Java records for DTOs and summary types
- Use Optional<T> instead of null checks

## Naming Conventions
- PascalCase for classes: `OrderController`, `InventoryService`
- camelCase for methods/variables: `getOrderById`, `lowStockItems`
- UPPER_SNAKE_CASE for constants: `LOW_STOCK_THRESHOLD`
- Test methods: `shouldDoSomething_whenCondition`

## REST API Standards
- Use proper HTTP methods: GET (read), POST (create), PUT (replace), PATCH (partial update), DELETE (remove)
- Return ResponseEntity<T> with appropriate HTTP status codes
- Use @Valid for request body validation
- Return structured error responses via @ControllerAdvice

## Error Handling
- Throw custom exceptions (ResourceNotFoundException, InsufficientStockException)
- GlobalExceptionHandler returns structured JSON errors
- Never expose stack traces in API responses

## Database
- Entity annotations use GenerationType.IDENTITY (compatible with H2 and SQL Server)
- Include created_at / updated_at timestamps on transactional entities
- Seed data in data.sql uses realistic OutFront Media business data

## Domain Model
- **Order** — customer orders for billboard components (PENDING → CONFIRMED → SHIPPED → DELIVERED)
- **InventoryItem** — warehouse stock with SKU, category, reorder levels, location tracking
- **Supplier** — component suppliers with contact info and active/inactive status
- **PurchaseOrder** — restock orders to suppliers (DRAFT → SUBMITTED → APPROVED → SHIPPED → RECEIVED)
