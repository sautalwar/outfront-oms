# 🎯 GitHub Copilot Workshop — OutFront Media Order Management System

> **Duration:** 2 hours (~120 min) · **Format:** Instructor-led, hands-on · **Level:** Intermediate

Welcome! In this workshop you'll use **GitHub Copilot** to build, extend, and ship features in a real **Spring Boot Order Management System** for OutFront Media — an outdoor advertising company that manages billboard and digital signage equipment.

By the end you'll have built CRUD endpoints, a web portal, and a full PR workflow — all powered by Copilot's customization ecosystem.

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.2.5 |
| Build | Maven |
| Database | H2 (in-memory, auto-seeded) |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Testing | JUnit 5 + MockMvc |

---

## ✅ Prerequisites

| Requirement | Details |
|-------------|---------|
| **GitHub account** | With Copilot access enabled |
| **Browser** | Any modern browser (for GitHub Codespaces) |
| **OR** Local setup | Docker Desktop + VS Code (for Dev Container) |

> **That's it!** Java, Maven, Node.js, and all VS Code extensions are pre-installed in the Dev Container. No local setup needed if you use Codespaces.

---

## 📋 Workshop Agenda at a Glance

| Phase | Title | Duration | Format |
|-------|-------|----------|--------|
| 0 | [Zero-Setup Start](#-phase-0-zero-setup-start-5-min) | 5 min | ✅ Hands-on |
| 1 | [Copilot Configuration](#-phase-1-copilot-configuration--teach-copilot-about-your-project-15-min) | 15 min | ✅ Hands-on |
| 2 | [The Copilot Customization Ecosystem](#-phase-2-the-copilot-customization-ecosystem-15-min) | 15 min | 🎥 Demo + Discussion |
| 3 | [Build CRUD Endpoints Live](#-phase-3-build-crud-endpoints-live-25-min) | 25 min | ✅ Hands-on |
| 4 | [Build a Web Portal Live](#-phase-4-build-a-web-portal-live-15-min) | 15 min | ✅ Hands-on |
| 5 | [Custom Agents → Squad](#-phase-5-custom-agents--squad-10-min) | 10 min | 🎥 Demo |
| 6 | [PR Workflow with @copilot](#-phase-6-pr-workflow-with-copilot-15-min) | 15 min | 🎥 Demo + Hands-on |
| 7 | [GitHub Advanced Security](#-phase-7-github-advanced-security-10-min) | 10 min | 🎥 Demo |
| 8 | [Wrap-Up & Q&A](#-phase-8-wrap-up--qa-10-min) | 10 min | 💬 Discussion |
| | **Total** | **~120 min** | |

---

## 🚀 Phase 0: Zero-Setup Start (5 min)

Get the app running in under five minutes — no local installs required.

### Option A: GitHub Codespaces (recommended)

1. Open the repository on GitHub
2. Click **Code → Codespaces → Create codespace on main**
3. Wait for the container to build (~2 min) — everything is pre-configured

### Option B: Local Dev Container

1. Clone the repo and open it in VS Code
2. Install the **Dev Containers** extension if you haven't already
3. Press `Ctrl+Shift+P` → **Dev Containers: Reopen in Container**

### Verify it works

```bash
mvn spring-boot:run
```

| Resource | URL |
|----------|-----|
| 🌐 Application | http://localhost:8080 |
| 📖 Swagger UI | http://localhost:8080/swagger-ui.html |
| 🗄️ H2 Console | http://localhost:8080/h2-console |

> **H2 Console credentials:** JDBC URL `jdbc:h2:mem:omsdb` · User: `sa` · Password: *(leave blank)*

The `.devcontainer/` folder has everything: Java 17, Maven, Node.js 20, and all VS Code extensions.

---

## 🧠 Phase 1: Copilot Configuration — "Teach Copilot About Your Project" (15 min)

**Showcase:** `copilot-instructions.md` + path-scoped instructions

Great AI output starts with great context. In this phase you'll see how a few Markdown files transform Copilot from a generic assistant into a project-aware teammate.

### What you'll explore

| File | Purpose |
|------|---------|
| `.github/copilot-instructions.md` | Project-wide context — business domain, architecture, conventions |
| `.github/instructions/java.instructions.md` | Java-specific rules — constructor injection, `final` usage, naming |
| `.github/instructions/sql.instructions.md` | SQL-specific rules — H2/SQL Server compatibility, naming, style |

### Live demo

1. Open Copilot Chat and ask: *"How should I create a new service class?"*
2. Observe how the response follows your project's conventions (constructor injection, `@Transactional`, Javadoc)
3. Compare: what would Copilot say **without** these instruction files?

> 💡 **Key takeaway:** Instructions are the highest-leverage Copilot customization. Write them once, benefit every time.

---

## 🧩 Phase 2: The Copilot Customization Ecosystem (15 min)

**Showcase:** All 7 customization mechanisms

Copilot isn't just autocomplete — it's a fully customizable AI development platform. Here's the complete toolkit:

| # | Feature | File Location | What It Does |
|---|---------|---------------|--------------|
| 1 | **Instructions** | `.github/copilot-instructions.md` | Always-on project context |
| 2 | **Path-scoped Instructions** | `.github/instructions/*.instructions.md` | File-type specific rules |
| 3 | **Prompt Files** | `.github/prompts/*.prompt.md` | Reusable task templates |
| 4 | **Chat Modes** | `.github/chatmodes/*.chatmode.md` | Session-wide persona |
| 5 | **Custom Agents** | `.github/agents/*.agent.md` | Specialist roles |
| 6 | **Skills** | `.github/skills/*.skill.md` | Learned patterns |
| 7 | **Pre-commit Hooks** | `.githooks/` | Automated enforcement |

**Plus:** GitHub Actions Workflows (`.github/workflows/`) serve as always-running quality gates.

### Customization hierarchy

```
Instructions (always on)
  → Path-scoped Instructions (file-type rules)
    → Chat Modes (session persona)
      → Agents (specialist roles)
        → Prompts (task templates)
          → Skills (learned patterns)
            → Hooks (automated enforcement)
```

> 💡 **Key takeaway:** Each mechanism has a purpose. Instructions set the foundation; agents and prompts build on it.

---

## ⚡ Phase 3: Build CRUD Endpoints Live (25 min)

**Showcase:** Copilot code generation + custom agent (`@oms-developer`)

This is where Copilot shines. You'll build a complete set of Order endpoints from scratch — guided by the `@oms-developer` custom agent.

### Endpoints to build

| # | Method | Endpoint | Description |
|---|--------|----------|-------------|
| 1 | `GET` | `/api/orders` | List all orders |
| 2 | `GET` | `/api/orders/{id}` | Get a single order |
| 3 | `POST` | `/api/orders` | Create a new order |
| 4 | `PUT` | `/api/orders/{id}` | Update an existing order |
| 5 | `DELETE` | `/api/orders/{id}` | Delete an order |
| 6 | `GET` | `/api/orders?status=PENDING` | *(Optional)* Filtered search |

### Build pattern

Follow the Spring Boot layered architecture:

```
Model (JPA Entity)
  → Repository (JpaRepository)
    → Service (business logic)
      → Controller (HTTP endpoints)
```

### Steps

1. Open Copilot Chat and invoke the `@oms-developer` agent
2. Ask it to scaffold each layer, one at a time
3. Review the generated code — does it follow the project conventions?
4. Test each endpoint via **Swagger UI** at http://localhost:8080/swagger-ui.html

> 💡 **Key takeaway:** Custom agents combine instructions + role context to produce code that fits your project from the start.

---

## 🎨 Phase 4: Build a Web Portal Live (15 min)

**Showcase:** Copilot full-stack generation

Now let's give your API a face. You'll use Copilot to generate a complete HTML/CSS/JavaScript dashboard.

### What you'll build

- An **Orders table** displaying data from the REST API
- **Add**, **Edit**, and **Delete** buttons with working forms
- Connected to the endpoints you built in Phase 3
- Served as a static page from `src/main/resources/static/`

### Steps

1. Ask Copilot: *"Create an HTML dashboard for managing orders. It should display a table of orders with buttons to add, edit, and delete. Connect it to the /api/orders REST API."*
2. Place the generated files in `src/main/resources/static/`
3. Refresh the browser and verify the portal works

> 💡 **Key takeaway:** Copilot can generate full-stack features — backend and frontend — in a single session.

---

## 🤖 Phase 5: Custom Agents → Squad (10 min)

**Showcase:** `agents.md` → Squad escalation

### Custom agents (what you've been using)

In Phase 3 you used the `@oms-developer` agent — a hand-crafted specialist defined in `.github/agents/`. These agents are powerful but **manual**: you define each role yourself.

Other agents in this project:
- `@code-reviewer` — Reviews code for quality and convention compliance

### Squad (the next level)

**Squad** takes a different approach: describe what you're building and the system **auto-discovers** the right team composition. No agent definitions needed.

**Quick demo:**
1. Describe a feature to Squad (e.g., *"Add a campaign management module"*)
2. Watch Squad propose a team of specialists
3. See the agents spawn and work in parallel

> 💡 **Key takeaway:** Custom agents = precision control. Squad = autonomous team formation. Use both.

---

## 🔄 Phase 6: PR Workflow with @copilot (15 min)

**Showcase:** Copilot coding agent

See Copilot work **autonomously** — from issue to merged PR.

### Steps

1. **Create a GitHub issue**
   - Example: *"Add a GET /api/orders/count endpoint that returns the total number of orders"*

2. **Assign the issue to `@copilot`**
   - Copilot picks up the issue and starts working

3. **Watch Copilot work**
   - Creates a feature branch
   - Writes the implementation code
   - Opens a draft pull request

4. **Review the PR**
   - Use the `code-reviewer` chat mode to analyze the changes
   - Check for convention compliance, test coverage, edge cases

5. **Merge and close**
   - Approve, merge, and the issue closes automatically

> 💡 **Key takeaway:** Copilot isn't just a pair programmer — it's an autonomous contributor that can go from issue to PR.

---

## 🛡️ Phase 7: GitHub Advanced Security (10 min)

**Showcase:** GHAS features

Security isn't a phase — it's a layer that runs continuously.

### What you'll see

| Feature | What It Does |
|---------|--------------|
| **CodeQL Scanning** | Static analysis that finds vulnerabilities in your code (see `.github/workflows/`) |
| **Dependabot Alerts** | Flags vulnerable dependencies and suggests version upgrades |
| **Secret Scanning** | Detects accidentally committed secrets (API keys, tokens, passwords) |
| **Push Protection** | Blocks pushes that contain secrets *before* they reach the repo |
| **SECURITY.md** | Policy file that tells contributors how to report vulnerabilities |

> 💡 **Key takeaway:** GHAS provides defense in depth — scanning code, dependencies, and secrets automatically.

---

## 🎬 Phase 8: Wrap-Up & Q&A (10 min)

### What we covered today

| Phase | Copilot Feature |
|-------|----------------|
| 0 | Dev Containers + Codespaces |
| 1 | Instructions + path-scoped instructions |
| 2 | The full customization ecosystem (7 mechanisms) |
| 3 | Code generation + custom agents |
| 4 | Full-stack generation |
| 5 | Custom agents → Squad |
| 6 | Copilot coding agent (issue → PR) |
| 7 | GitHub Advanced Security |

### Next steps

- 🔗 **Share the Codespace/Dev Container link** with your team
- 📋 Check the **GitHub Project board** and **JIRA** for ongoing work
- 🧪 Try customizing Copilot for your own projects using the patterns from today

---

## 🔖 Quick Reference

| Resource | URL |
|----------|-----|
| 🌐 Application | http://localhost:8080 |
| 📖 Swagger UI | http://localhost:8080/swagger-ui.html |
| 🗄️ H2 Console | http://localhost:8080/h2-console |

**H2 Console credentials:**
- JDBC URL: `jdbc:h2:mem:omsdb`
- User: `sa`
- Password: *(leave blank)*

---

## ❓ Need Help?

- **Setup issues?** Check that your Codespace or Dev Container built successfully
- **During the workshop?** Raise your hand — facilitators are here to help
- **Copilot not responding as expected?** Verify the `.github/copilot-instructions.md` file is present
- **After the workshop?** Revisit these labs and adapt the Copilot configuration for your own projects

---

**Happy coding with Copilot! 🚀**
