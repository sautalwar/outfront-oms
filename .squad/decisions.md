# Squad Decisions

## Active Decisions

### Decision: Campaign Management Epic Architecture

**Author:** Keaton (Lead Architect)  
**Date:** 2026-04-17  
**Status:** Accepted

#### Context

Creating the Campaign Management feature for OutFront OMS. Reviewed existing codebase patterns in `outfront-oms` to ensure consistency.

#### Decisions

1. **BigDecimal for budget** — Campaign.budget uses `BigDecimal`, not `double`. Monetary precision matters. This deviates from `InventoryItem.unitPrice` (which uses `double`) — recommend migrating that field later.

2. **PATCH for status transitions** — `PATCH /api/campaigns/{id}/status` instead of `PUT` (as OrderController does). PATCH is semantically correct for partial updates. Consider aligning OrderController in a future refactor.

3. **Campaign-Order linkage deferred** — Adding `campaignId` FK to `orders` table touches the Order domain and requires schema migration. Placeholder method in CampaignService for now; full implementation in a follow-up story.

4. **No new exception classes** — Reuse existing `ResourceNotFoundException` and `IllegalStateException`. Both already handled by `GlobalExceptionHandler`.

5. **Inner enum pattern** — `CampaignStatus` is an inner enum of `Campaign`, consistent with `Order.OrderStatus`.

#### Impact

- Epic breakdown: `docs/epic-breakdown.md`
- 34 story points across 6 stories, 3-sprint delivery
- Critical path: Entity → Service → API → Tests
- Story assignments: Fenster (2), Hockney (2), Verbal (2)

---

### Decision: Comprehensive API Integration Test Workflow

**Date:** 2024-11-20  
**Agent:** Hockney (Tester)  
**Status:** Completed

#### Context

Created a GitHub Actions workflow for comprehensive API integration testing of the OutFront OMS application.

#### Decision

Implemented `api-tests.yml` workflow with the following structure:

**Coverage (30+ endpoints tested):**
- **Order Endpoints:** 9 tests (list, get by ID, filter by status, summary, date range, create, update status, delete, 404 handling)
- **Inventory Endpoints:** 11 tests (list, get by ID, get by SKU, search, categories, filter by category, low-stock, create, update, adjust stock, delete)
- **Supplier Endpoints:** 6 tests (list all, list active, get by ID, create, update, deactivate)
- **Purchase Order Endpoints:** 6 tests (list all, get by ID, filter by status, filter by supplier, create, update status)

**Testing Approach:**
1. Response Code Validation — Every test checks HTTP status codes
2. Response Body Validation — Uses `jq` for JSON structure and content validation
3. CRUD Lifecycle Testing — Tests create→update→delete flows for each domain
4. Edge Case Testing — Includes 404 not found tests
5. Pass/Fail Tracking — Each test step counts passes/fails and reports detailed results

**Workflow Features:**
- Uses Spring Boot Actuator health endpoint for readiness checks
- 60-second startup timeout with retry logic
- Separate test steps per domain for clear failure isolation
- Final summary aggregating all test results
- Proper cleanup to stop the app after tests

**Key Testing Patterns:**
- Used seed data IDs from `data.sql` (Order ID 1, Supplier ID 1, etc.)
- Created test entities to validate CRUD operations
- Validated JSON responses using `jq` expressions
- Exit with failure on first failing test within each step

#### Rationale

- **Separation by Domain:** Each API group (Orders, Inventory, Suppliers, Purchase Orders) has its own test step for better visibility when failures occur
- **Body Validation:** Not just HTTP codes—actual JSON structure validation ensures endpoints return expected data
- **Integration vs Unit:** These are true integration tests running against the full Spring Boot app with H2, complementing existing JUnit controller tests
- **CI/CD Ready:** Workflow runs on push, PR, and manual dispatch for maximum flexibility

#### Impact

- Provides automated regression testing for all 30+ API endpoints
- Catches breaking changes in API contracts before deployment
- Documents expected API behavior through executable tests
- Enables safe refactoring with confidence

## Governance

- All meaningful changes require team consensus
- Document architectural decisions here
- Keep history focused on work, decisions focused on direction
