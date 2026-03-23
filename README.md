# OutFront OMS — Order Management System

A full-featured Order Management System built with Java 17 and Spring Boot, designed for managing billboard components, digital displays, and accessories across multiple warehouse locations.

---

## 🚀 Quick Start

### Prerequisites
- **Java JDK 17+** — [Download Eclipse Temurin](https://adoptium.net/)
- **Apache Maven 3.9+** — [Download Maven](https://maven.apache.org/download.cgi)

### Run (H2 in-memory — zero setup)

```bash
git clone https://github.com/sautalwar/outfront-oms.git
cd outfront-oms
mvn spring-boot:run
```

Open [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to explore the API.

---

## 📊 API Endpoints

### Orders — `/api/orders`
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/orders` | List all orders (filter by `?status=` and `?customer=`) |
| GET | `/api/orders/{id}` | Get order by ID |
| GET | `/api/orders/summary` | Order count dashboard by status |
| GET | `/api/orders/date-range` | Filter by `?startDate=` and `?endDate=` |
| POST | `/api/orders` | Create a new order |
| PUT | `/api/orders/{id}/status` | Update order status |
| DELETE | `/api/orders/{id}` | Delete PENDING/CANCELLED order |

### Inventory — `/api/inventory`
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/inventory` | List all inventory items |
| GET | `/api/inventory/{id}` | Get item by ID |
| GET | `/api/inventory/sku/{sku}` | Get item by SKU |
| GET | `/api/inventory/search?q=` | Search by name, SKU, or location |
| GET | `/api/inventory/categories` | List all categories |
| GET | `/api/inventory/category/{cat}` | Items in a category |
| GET | `/api/inventory/low-stock` | Items at or below reorder level |
| POST | `/api/inventory` | Create new item |
| PUT | `/api/inventory/{id}` | Update item |
| PATCH | `/api/inventory/{id}/stock` | Adjust stock quantity |
| DELETE | `/api/inventory/{id}` | Delete item |

### Suppliers — `/api/suppliers`
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/suppliers` | List all suppliers |
| GET | `/api/suppliers/active` | List active suppliers |
| GET | `/api/suppliers/{id}` | Get supplier by ID |
| POST | `/api/suppliers` | Create supplier |
| PUT | `/api/suppliers/{id}` | Update supplier |
| DELETE | `/api/suppliers/{id}` | Deactivate supplier |

### Purchase Orders — `/api/purchase-orders`
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/purchase-orders` | List all purchase orders |
| GET | `/api/purchase-orders/{id}` | Get by ID |
| GET | `/api/purchase-orders/status/{status}` | Filter by status |
| GET | `/api/purchase-orders/supplier/{id}` | Filter by supplier |
| POST | `/api/purchase-orders` | Create purchase order |
| PUT | `/api/purchase-orders/{id}/status` | Update status |

---

## 🏗️ Architecture

```
src/main/java/com/outfront/oms/
├── controller/          # REST API layer
│   ├── OrderController
│   ├── InventoryController
│   ├── SupplierController
│   └── PurchaseOrderController
├── service/             # Business logic
│   ├── OrderService
│   ├── InventoryService
│   ├── SupplierService
│   └── PurchaseOrderService
├── repository/          # Data access (Spring Data JPA)
│   ├── OrderRepository
│   ├── InventoryRepository
│   ├── SupplierRepository
│   └── PurchaseOrderRepository
├── model/               # JPA entities
│   ├── Order (PENDING → CONFIRMED → SHIPPED → DELIVERED)
│   ├── InventoryItem (SKU, category, reorder levels)
│   ├── Supplier (contact info, active/inactive)
│   └── PurchaseOrder (DRAFT → SUBMITTED → APPROVED → SHIPPED → RECEIVED)
├── exception/           # Custom exceptions + global handler
│   ├── ResourceNotFoundException
│   ├── InsufficientStockException
│   └── GlobalExceptionHandler
└── Application.java     # Entry point
```

---

## 💾 Database Profiles

### Default — H2 (in-memory)
```bash
mvn spring-boot:run
```
H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console) (JDBC URL: `jdbc:h2:mem:omsdb`)

### SQL Server
```bash
# With Docker Compose
docker compose up -d

# Or connect to existing SQL Server
mvn spring-boot:run -Dspring-boot.run.profiles=sqlserver
```

---

## 🧪 Testing

```bash
mvn test
```

Tests include:
- **Controller tests** — `@WebMvcTest` with MockMvc
- **Service tests** — `@ExtendWith(MockitoExtension.class)` with Mockito

---

## 🤖 GitHub Copilot Assets

This repo includes Copilot customization files for AI-assisted development:

```
.github/
├── copilot-instructions.md      # Repository-level coding standards
├── instructions/
│   ├── java.instructions.md     # Java/Spring patterns (always-on)
│   └── sql.instructions.md      # SQL conventions (always-on)
├── prompts/
│   ├── add-endpoint.prompt.md   # Recipe: add a REST endpoint
│   ├── fix-bug.prompt.md        # Recipe: systematic bug fixing
│   └── write-tests.prompt.md    # Recipe: generate tests
└── chatmodes/
    └── code-reviewer.chatmode.md  # Specialist: code review
```

---

## 📝 Built by
**Saurabh Talwar**, Microsoft — for the OutFront Media GitHub Copilot Workshop
