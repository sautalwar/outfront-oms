# Decisions Ledger

**Project:** outfront-copilot-workshop  
**Last Updated:** 2026-04-14T16:36:30Z

---

## Foundational Decisions

### Workshop Structure & Labs
**Decision:** Multi-lab progression starting with Prompts → Skills → Agents → Integration  
**Rationale:** Scaffolded learning reduces cognitive overload; hands-on practice with real OutFront Media context  
**Owner:** Kobayashi  
**Date:** 2026-04-14  
**Impact:** Lab 1 (Prompts, Skills, Agents) + Road map for Lab 02+

### Directory Architecture
**Decision:** Separate workshop-labs/ from production app; include src/, mcp-server/, workshop-prompts/, .github/, Docker files  
**Rationale:** Clean separation allows participants to fork; reusable scaffolding; MCP server as hands-on extension point  
**Owner:** Fenster  
**Date:** 2026-04-14  
**Impact:** 50+ file structure enabling self-guided exploration

### Participant Onboarding
**Decision:** PREREQUISITES.md with platform-agnostic setup steps + troubleshooting; README.md as navigator  
**Rationale:** Reduces day-of support burden; participants arrive prepared; quick-start patterns documented  
**Owner:** Kobayashi  
**Date:** 2026-04-14  
**Impact:** Parallel setup (JDK 17, Maven, VS Code, Git, optional Docker/Node.js)

### Prompt Library
**Decision:** 7 reusable domain-specific prompts (Order design, Inventory, API, tests, validation, error handling, performance)  
**Rationale:** Fork-and-iterate model; OutFront Media billboard/signage context boosts relevance; reduces participant blank-page anxiety  
**Owner:** Fenster  
**Date:** 2026-04-14  
**Impact:** workshop-prompts/ used in Lab 1 Exercise 03; extensible for future labs

### Domain Context
**Decision:** All workshop materials grounded in OutFront Media Order Management (billboard/digital signage orders, warehouse inventory, cross-domain validation)  
**Rationale:** Real business domain increases motivation; Java Spring Boot alignment with production app; skill transfer to actual system  
**Owner:** Team  
**Date:** 2026-04-14  
**Impact:** Cohesive participant experience across Labs and exercises

---

## Derived Patterns (Cross-Agent Learnings)

### Workshop Content Format
- **HTML+PDF dual delivery** maximizes distribution (web + print)
- **Presenter guide** with click-by-click + Q&A prep reduces live-demo friction
- **Landscape PDF orientation** required for complex data tables (team workshop plan)

### Copilot Skill Deployment
- **Path-scoped skills** allow participants to extend Copilot without global configuration changes
- **Custom agents** enable cross-domain orchestration (order-inventory validation use case)
- **MCP server integration** bridges Copilot and backend data, supporting agent decision-making

### Participant Success Factors
- **Progressive difficulty** (explore → skill → prompt → agent → integration) scaffolds learning
- **Multiple correct answers** encouraged; solutions show one valid approach
- **Troubleshooting guide** (PREREQUISITES.md) embedded in setup reduces blockers

---

## Campaign Management Epic Decisions

### GitHub Project Infrastructure
**Decision:** GitHub project structure + 32 synchronized issues for Campaign Management epic  
**Rationale:** GitHub as SSOT for development team; Issue references in commits/PRs; Labels enable workflow automation  
**Owner:** Keaton  
**Date:** 2026-04-17  
**Architecture Decisions Enforced:**
- 13 labels (epic, story, task, backend, database, testing, frontend, mcp, security, P0-critical, P1-high, P2-medium, P3-low)
- BigDecimal for Campaign.budget (monetary precision)
- PATCH for status updates (semantic improvement over PUT)
- Campaign-Order FK linkage deferred (scope management)
- Inner enum pattern for CampaignStatus (codebase consistency)
- Exception reuse via GlobalExceptionHandler (no new custom exceptions)
- Constructor injection (Spring Boot alignment)

**Impact:** 32 issues (1 Epic, 6 Stories, 25 Tasks) provide granular tracking for 34 SP epic; Critical path: Story 1 → 2 → 3 → 4; Story 5 & 6 parallel

### JIRA CAMP Project Synchronization
**Decision:** Scrum template (sprints, burndown, velocity) mirrored exactly to GitHub issues  
**Rationale:** JIRA for sprint planning/reporting; GitHub for development workflow; Dual-tool sync reduces manual data entry  
**Owner:** Keaton  
**Date:** 2026-04-17  
**Project Structure:** CAMP-1 (Epic) + CAMP-2 to CAMP-30 (Stories/Sub-tasks)  
**Impact:** Team can plan in JIRA, work in GitHub; story point velocity tracked bi-weekly

### Campaign Entity Architecture
**Decision:** Use BigDecimal for Campaign.budget (not double); Inner enum for CampaignStatus  
**Rationale:** Monetary precision (double causes rounding errors); Inner enum mirrors Order.OrderStatus pattern (consistency); Triggers future InventoryItem.unitPrice migration  
**Owner:** Keaton  
**Date:** 2026-04-17  
**Impact:** Campaign entity follows OutFront standard; Tech debt documented for future sprint

### Status Update HTTP Verb
**Decision:** Use PATCH for Campaign status transitions (not PUT)  
**Rationale:** Semantic correctness (PATCH = partial update, PUT = full replacement); Suggests OrderController refactor in future  
**Owner:** Keaton  
**Date:** 2026-04-17  
**Impact:** Improves REST API semantic clarity; Aligns with HTTP RFC 5789 best practices

### Campaign-Order Linkage Deferral
**Decision:** Add campaignId FK to orders documented as placeholder; defer to separate story  
**Rationale:** Avoids cross-domain schema changes in initial epic; Reduces scope creep; Enables Story 1-3 completion without Order domain modifications  
**Owner:** Keaton  
**Date:** 2026-04-17  
**Impact:** Clear scope boundary; tracked in Story 2.3 placeholder for future sprint

---

## Archived Decisions
(None yet — all decisions actively referenced)
