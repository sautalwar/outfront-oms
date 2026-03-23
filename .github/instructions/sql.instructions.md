---
applyTo: "**/*.sql"
---

# SQL Code Instructions

## Compatibility
- Write **standard SQL** compatible with both **H2** (dev) and **SQL Server** (production)
- Avoid database-specific functions unless necessary
- Use `IDENTITY(1,1)` or `GENERATED ALWAYS AS IDENTITY` for auto-increment
- Prefer `NVARCHAR` over `VARCHAR` in SQL Server DDL for Unicode support

## Query Style
- Use **UPPER_CASE** for SQL keywords: `SELECT`, `FROM`, `WHERE`, `ORDER BY`
- Always use **explicit column names** — never `SELECT *`
- Use **meaningful aliases** when joining tables

## Naming Conventions
- Use **snake_case** for tables and columns: `order_date`, `customer_name`
- Prefix boolean columns with `is_` or `has_`: `is_active`, `has_shipped`

## Schema Design
- Always define a **primary key**
- Use **foreign key constraints** for referential integrity
- Include `created_at` and `updated_at` on transactional tables
- Add **NOT NULL** where values are always required

## Seed Data
- Use realistic data reflecting OutFront Media's billboard business
- Group related seed data with comments
- Use ISO date format: `'YYYY-MM-DD'`
