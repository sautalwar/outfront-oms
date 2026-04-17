# Orchestration: Keaton (Lead Architect) — GitHub & JIRA Projects

**Timestamp:** 2026-04-17T08:47:00Z  
**Agent:** Keaton  
**Model:** claude-haiku-4.5  
**Mode:** background  
**Tasks:**
1. Created GitHub Project + 32 issues (1 Epic, 6 Stories, 25 Tasks, 13 labels)  
2. Created JIRA CAMP project + 32 issues (1 Epic, 6 Stories, 25 Sub-tasks)

**Status:** ✅ DONE

## Summary
Keaton synchronized GitHub and JIRA project management for Campaign Management epic:

### GitHub Repository (sautalwar/outfront-oms)
- **Labels (13 total):**
  - Work types: epic, story, task
  - Domains: backend, database, testing, frontend, mcp, security
  - Priority: P0-critical, P1-high, P2-medium, P3-low

- **Issue #1:** EPIC: Campaign Management — Full CRUD with SQL Server
  - Summary table, critical path, tech stack, sprint recommendations

- **Issues #2–#7:** 6 Stories (25–34 SP)
  - Story 1: Campaign Entity & Repository (5 SP)
  - Story 2: Campaign Service Layer (8 SP)
  - Story 3: Campaign REST API (8 SP)
  - Story 4: Campaign Testing (5 SP)
  - Story 5: Campaign MCP Integration (5 SP)
  - Story 6: Campaign Frontend Dashboard (3 SP)

- **Issues #8–#32:** 25 Tasks
  - Organized by parent story
  - Each includes acceptance criteria, implementation patterns, code references

### JIRA Scrum Project (CAMP)
- **Project Key:** CAMP
- **Template:** Greenhopper Scrum (sprints, story points, velocity)
- **CAMP-1:** Epic (34 SP total)
- **CAMP-2 to CAMP-30:** 6 Stories + Sub-tasks (mirrors GitHub exactly)

### Critical Path
```
Story 1 (Entity & Repo) → Story 2 (Service) → Story 3 (REST API) → Story 4 (Testing)
                                                    ↓
                                           Story 5 (MCP Integration)
                                                    ↓
                                        Story 6 (Frontend Dashboard)
```

## Artifacts
- GitHub issues: sautalwar/outfront-oms/issues (Issues #1–#32)
- JIRA project: saurabhtalwar.atlassian.net/browse/CAMP
- 13 GitHub labels applied to all issues

## Architecture Decisions Enforced
- **BigDecimal for Budget:** Monetary precision (flagged InventoryItem.unitPrice as tech debt)
- **PATCH for Status:** Semantic improvement over PUT (OrderController uses PUT; Campaign improves)
- **Inner Enum Pattern:** CampaignStatus mirrors Order.OrderStatus design
- **Campaign-Order FK Deferred:** Avoids cross-domain schema changes in initial epic
- **Exception Reuse:** GlobalExceptionHandler handles all error cases (no new custom exceptions)
- **Constructor Injection:** Aligns with existing Spring Boot patterns
- **MCP Integration:** Story 5 extends mcp-server/ with lookup_campaign, list_active_campaigns

## Cross-Agent Synergy
- **Fenster & Hockney:** Story 2 & 4 patterns inform backend service/testing design
- **Kobayashi:** Epic breakdown fed Lab 3 (CampaignService) + Lab 5 (Testing)
- **McManus:** CI/CD pipeline hooks into GitHub issue automation
- **Scribe:** Issue tracking orchestrated via GitHub + JIRA; decisions logged
