# Kujan — History

## Project Context
- OutFront Media Order Management System for billboard/digital signage equipment
- Java 17, Spring Boot 3.2.5, Maven, H2/SQL Server, JUnit 5
- Repo contains code, presentations (.pptx/.pdf/.html), workshop materials, Docker config, MCP server
- Multiple versioned presentation files exist in repo root
- User: Copilot

## Learnings

### Workshop Structure (from workshop-outfront.html)
- 20 slides, 4 parts, ~3.5 hours total
- Part 1: Foundations (60 min) — overview, setup, custom instructions, chat modes
- Part 2: Hands-On Coding (65 min) — completions, chat, agent mode, model selection
- Part 3: Integrations (50 min) — MCP, Jira, security & governance
- Part 4: Wrap Up (20 min) — next steps, Q&A
- 7 hands-on slides marked with hammer icon
- Full speaker notes with timing on every slide

### Code Structure
- 9 Java source files: Application + 2 controllers + 2 services + 2 repos + 2 models
- 2 test files: OrderControllerTest, InventoryControllerTest (controller-level only)
- No service-layer tests (may be intentional workshop exercise)
- MCP server (Node.js) in mcp-server/ with 3 tools, fully implemented

### Documentation Coverage
- README, PARTICIPANT-PREP, ADMIN-PRECHECK, COPILOT all complete
- docs/WORKFLOW-REFERENCE.md covers 13+ GitHub Actions workflows
- docs/security/SECURITY-SETUP-GUIDE.md is comprehensive
- .github/ has 16 workflows, 3 prompts, 2 chat modes, 2 instructions

### Repo Clutter
- Root contains 20+ files from OTHER presentations (AKS Runner Fleet, Migration Resilience)
- 8 versions of Migration Resilience .pptx files
- parts/ dir is AKS slides, NOT Copilot workshop content
- Office temp files (~$*.pptx) and copilot-session-*.md files in root
- The actual workshop presentation is ONLY workshop-outfront.html

### Environment
- Maven and Java were NOT found on the machine during this scan
- Build verification could not be completed — must install JDK 17+ and Maven 3.9+
