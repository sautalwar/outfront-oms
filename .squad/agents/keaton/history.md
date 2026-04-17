# Keaton — History

## Project Context
- OutFront Media Order Management System for billboard/digital signage equipment
- Java 17, Spring Boot 3.2.5, Maven, H2/SQL Server, JUnit 5
- Two domains: Orders (PENDING→CONFIRMED→SHIPPED) and Inventory (SKU-tracked, low-stock threshold 10)
- User: Copilot

## Learnings

### Campaign Epic Breakdown (2025-01-XX)
- **Codebase patterns discovered in outfront-oms:**
  - Entities use inner enums for status (`Order.OrderStatus`, `PurchaseOrder.PurchaseOrderStatus`)
  - `@GeneratedValue(strategy = GenerationType.IDENTITY)` for all PKs
  - `LocalDate` for timestamps (not `LocalDateTime`) throughout existing entities
  - `double` used for `InventoryItem.unitPrice` — flagged as tech debt, Campaign uses `BigDecimal` instead
  - `GlobalExceptionHandler` handles `ResourceNotFoundException` (404), `InsufficientStockException` (409), `IllegalStateException` (400), `MethodArgumentNotValidException` (400)
  - Constructor injection everywhere, no Lombok, no DTOs yet (entities returned directly from controllers)
  - Seed data in `data.sql` uses plain INSERT statements grouped by entity
  - OrderController uses `PUT` for status changes; Campaign will use `PATCH` (semantic improvement)
- **Key file paths:**
  - Models: `src/main/java/com/outfront/oms/model/`
  - Controllers: `src/main/java/com/outfront/oms/controller/`
  - Services: `src/main/java/com/outfront/oms/service/`
  - Repositories: `src/main/java/com/outfront/oms/repository/`
  - Exceptions: `src/main/java/com/outfront/oms/exception/`
  - Seed data: `src/main/resources/data.sql`
- **Architecture decision:** Campaign-Order FK linkage deferred to avoid cross-domain schema changes in the initial epic
- **Epic breakdown saved to:** `docs/epic-breakdown.md` (34 story points, 6 stories, 22 tasks)
