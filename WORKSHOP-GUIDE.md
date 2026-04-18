# 🎬 Facilitator Guide — GitHub Copilot for OutFront Media

> **For Saurabh's eyes only.** This is the behind-the-scenes playbook for running the 2-hour workshop. It contains talk tracks, demo scripts, timing cues, fallback plans, and energy-management tips. Participants should never see this document.

---

## Quick Reference

| Phase | Title | Time | Cumulative |
|-------|-------|------|------------|
| 0 | Zero-Setup Start | 5 min | 0:05 |
| 1 | Copilot Configuration | 15 min | 0:20 |
| 2 | Copilot Customization Ecosystem | 15 min | 0:35 |
| 3 | Build CRUD Endpoints Live | 25 min | 1:00 |
| 4 | Build Web Portal Live | 15 min | 1:15 |
| 5 | Custom Agents → Squad | 10 min | 1:25 |
| 6 | PR Workflow with @copilot | 15 min | 1:40 |
| 7 | GitHub Advanced Security | 10 min | 1:50 |
| 8 | Wrap-Up & Q&A | 10 min | 2:00 |

**Buffer:** Phases 3 and 6 have the most variability. If you're running long, trim Phase 2 walkthrough (skip the hierarchy diagram) or shorten Phase 7 to 5 min.

---

## Pre-Workshop Checklist

Run through this **the morning of** — not the night before (Codespaces can idle-stop overnight).

- [ ] Codespace created, open, and tested — `mvn spring-boot:run` completes cleanly
- [ ] App running on port 8080 inside Codespace
- [ ] Swagger UI loads: `https://<codespace>-8080.app.github.dev/swagger-ui.html`
- [ ] H2 Console loads: `https://<codespace>-8080.app.github.dev/h2-console` (JDBC URL: `jdbc:h2:mem:omsdb`, user: `sa`, no password)
- [ ] GitHub issue pre-created for Phase 6 (title: "Add supplier endpoint" or similar — something the audience can relate to)
- [ ] `@copilot` (Copilot Workspace / Copilot coding agent) enabled on the repo
- [ ] GHAS enabled — CodeQL, Dependabot, and secret scanning all active
- [ ] Backup: local clone with `mvn spring-boot:run` already running on localhost:8080
- [ ] Pre-built `index.html` web portal saved somewhere (fallback for Phase 4)
- [ ] Browser tabs pre-opened:
  - Tab 1: Codespace editor
  - Tab 2: Swagger UI
  - Tab 3: GitHub repo (Issues tab)
  - Tab 4: GitHub repo (Actions tab)
  - Tab 5: GitHub repo (Security tab)
- [ ] Slide deck loaded (if any)
- [ ] Screen resolution set to 1920×1080 or higher; font size bumped to 16+ in VS Code
- [ ] Do Not Disturb / Focus mode ON (no Slack/Teams popups mid-demo)

---

## Phase 0: Zero-Setup Start (5 min)

**Goal:** Get every participant running with zero local setup — establish the "magic" of Dev Containers + Codespaces from the first minute.

### Setup
- Codespace link ready to share (paste in chat/Slack/email before you start)
- Your own Codespace already open with the app running

### Demo Script

| Step | Action | What to Say |
|------|--------|-------------|
| 1 | Share the Codespace link | *"Click this link. That's it. No Java install, no Maven, no database setup. Just click."* |
| 2 | While they load, show your Codespace | *"While yours is spinning up, let me show you what you're getting..."* |
| 3 | Open terminal, run `mvn spring-boot:run` | *"One command. Spring Boot starts with an H2 database pre-seeded with OutFront inventory data."* |
| 4 | Open Swagger UI in browser tab | *"We already have a live API with documentation. Nobody installed anything."* |
| 5 | Click `GET /api/inventory` → Try it out → Execute | *"Real data. LED panels, digital billboards, all the stuff OutFront actually manages."* |

### Talk Track
- **Key line:** *"Notice nobody installed Java 17, Maven, or any dependencies. The Dev Container did all of that. This is what 'developer experience' actually means."*
- If someone asks about the Dev Container config: *"Great question — we'll peek under the hood later. For now, just know that `.devcontainer/` defines everything: Java version, extensions, port forwarding, even the git hooks."*
- **Energy note:** Keep this upbeat and fast. The goal is momentum, not depth.

### Fallback Plan
- **Codespaces is slow/down:** Switch to your local instance. Say: *"Codespaces is warming up — let me show you on my local copy. The experience is identical because it's the same Dev Container."*
- **Port not forwarding:** In VS Code, go to the Ports tab, find 8080, click the globe icon to open in browser. If that fails, use `curl http://localhost:8080/api/inventory` in the terminal.
- **Maven build fails:** This shouldn't happen if you tested earlier. If it does, check `pom.xml` hasn't been modified. Worst case: `mvn clean install -DskipTests && mvn spring-boot:run`.

### Transition
*"So we have a running app with zero setup. But Copilot doesn't know anything about our project yet. Let's fix that."*

---

## Phase 1: Copilot Configuration (15 min)

**Goal:** Show that Copilot can be taught your team's standards — transforming it from a generic assistant into YOUR assistant.

### Setup
- Close Swagger UI (you'll come back later)
- Have `.github/copilot-instructions.md` ready to open
- Prepare a throwaway Java file for the "before/after" demo

### Demo Script

| Step | Action | What to Say |
|------|--------|-------------|
| 1 | Open a blank Java file or Copilot Chat | *"Let me ask Copilot to create a simple service class — no instructions loaded yet."* |
| 2 | Prompt: `Create a Java service class that retrieves orders from a database` | *"Watch what it generates..."* |
| 3 | Point out the generic output | *"Field injection with @Autowired. No Javadoc. No Optional. No transaction annotations. It works, but it's not OUR code."* |
| 4 | Open `.github/copilot-instructions.md` | *"This file is the master instruction set. Copilot reads it automatically for every conversation in this repo."* |
| 5 | Scroll through key sections | Highlight: constructor injection, Optional, @Transactional, SLF4J, method size limit |
| 6 | Open `.github/instructions/java.instructions.md` | *"This one applies to every .java file. It's more specific — naming patterns, test patterns, layered architecture."* |
| 7 | Open `.github/instructions/sql.instructions.md` | *"And this one kicks in for .sql files. H2 and SQL Server compatible syntax, UPPER_CASE keywords."* |
| 8 | Now re-prompt the same request | *"Same prompt, but now Copilot has our instructions..."* |
| 9 | Show the improved output | *"Constructor injection. @Transactional(readOnly = true). SLF4J logger. Optional return. Javadoc. THIS is our code."* |

### Talk Track
- **Key line:** *"This is the difference between a generic assistant and YOUR assistant. Same AI, different instructions, completely different output."*
- *"These files live in your repo. They're version-controlled. When your standards evolve, Copilot evolves with them."*
- *"Think of copilot-instructions.md as the 'always on' brain — it loads for every Copilot interaction in this repository."*
- If someone asks "does this work in IntelliJ?": *"The .github/copilot-instructions.md is IDE-agnostic — it works in VS Code, JetBrains, Neovim, anywhere Copilot runs."*

### Fallback Plan
- **Copilot doesn't pick up instructions:** Make sure you're in the workspace that has the `.github/` folder at the root. Try closing and reopening the Copilot Chat panel. If still broken, just show the files side-by-side and narrate: *"In your normal workflow, Copilot reads these automatically. Let me show you what the output looks like..."* and show a pre-generated example.
- **Output looks the same both times:** Copilot can be non-deterministic. If the "before" output accidentally looks good, say: *"Okay, Copilot got lucky this time — but watch, it doesn't know about our specific patterns like the order status flow or the low-stock threshold..."* and pivot to a domain-specific prompt.

### Transition
*"So we've seen the foundation — copilot-instructions.md and file-specific instructions. But that's only 2 of 7 customization mechanisms. Let me show you the full ecosystem."*

---

## Phase 2: Copilot Customization Ecosystem (15 min)

**Goal:** Give participants a mental model of ALL 7 customization mechanisms — from always-on to on-demand.

### Setup
- File explorer open to `.github/` directory
- Have the hierarchy clear in your mind (you'll narrate it)

### Demo Script

| # | Mechanism | File(s) to Show | Demo Action | What to Say |
|---|-----------|----------------|-------------|-------------|
| 1 | **Instructions** (always-on) | `.github/copilot-instructions.md` | Already shown in Phase 1 | *"You've seen this — the 'always on' layer. Every Copilot interaction reads this."* |
| 2 | **File instructions** (pattern-matched) | `.github/instructions/java.instructions.md`, `sql.instructions.md` | Already shown in Phase 1 | *"These activate automatically when you're editing matching files — .java, .sql."* |
| 3 | **Prompt files** (reusable workflows) | `.github/prompts/add-endpoint.prompt.md` | Open the file, show the `{{resource}}` variable | *"This is a reusable recipe. Any dev can run this to add a new endpoint. It even has the checklist: Model → Repo → Service → Controller → Tests → Seed data → Verify."* |
| 4 | **Chat modes** (persona switching) | `.github/chatmodes/code-reviewer.chatmode.md` | Switch to code-reviewer mode in Copilot Chat | *"Watch — I just switched Copilot's personality. Now it thinks like a senior reviewer: 🔴 Must Fix, 🟡 Should Fix, 🟢 Suggestion."* |
| 5 | **Skills** (composable capabilities) | `.github/skills/crud-endpoint.skill.md`, `test-writing.skill.md` | Open one and explain | *"Skills are building blocks that agents and prompts can invoke. Think of them as function libraries for AI."* |
| 6 | **Agents** (autonomous assistants) | `.github/agents/oms-developer.agent.md` | Open the file, point out tools and context | *"This is a full agent definition. It knows our tech stack, architecture, file structure, and even our business rules. We'll use it in Phase 3."* |
| 7 | **Git hooks** (guardrails) | `.githooks/pre-commit` | Run `.githooks/setup.sh`, then create a file with `System.out.println`, stage it, try to commit | *"Even if Copilot generates something non-compliant, the hook catches it. Belt AND suspenders."* |

After the 7 mechanisms:

| Step | Action | What to Say |
|------|--------|-------------|
| 8 | Show CI workflows briefly | Open `.github/workflows/ci.yml` and `.github/workflows/codeql.yml` in the file tree (don't deep-dive) | *"And on the CI side — every push runs tests, every PR gets CodeQL security analysis. We'll see this in action in Phase 7."* |
| 9 | Narrate the hierarchy | Draw on whiteboard or just speak | *"Think of these as layers: Instructions are always on. File instructions are pattern-matched. Prompts are on-demand recipes. Chat modes are persona switches. Skills are composable blocks. Agents are autonomous workers. Hooks are guardrails. Each layer builds on the last."* |

### Talk Track
- **Key line:** *"Think of these as layers — from always-on to on-demand. Instructions are the foundation. Agents are the ceiling. Everything in between gives you fine-grained control."*
- *"You don't need all 7 on day one. Start with copilot-instructions.md. Add file instructions next. Then prompts when you have repeatable workflows. Agents come last."*
- *"The beautiful thing: these are all just Markdown files in your repo. No special tooling, no SaaS config, no admin portal."*

### Fallback Plan
- **Git hook demo fails (Windows/Codespace permission issues):** Skip the live hook demo. Show the `pre-commit` file content and narrate: *"This checks for System.out.println and @Autowired on fields, then runs tests. If any check fails, the commit is blocked."*
- **Running long (>15 min):** Cut the skills and hooks demos. Just show the files, explain in one sentence each, and move on. The audience will explore these themselves.
- **Someone asks about MCP:** *"Great question — there's an MCP server in this repo too, in the `mcp-server/` directory. It exposes inventory and order data to Copilot via tools. We won't deep-dive today, but check out `.vscode/mcp.json` after the workshop."*

### Transition
*"Okay, we've seen the blueprint. Now let's build something real. We're going to use the @oms-developer agent to build CRUD endpoints from scratch — and you'll see every one of these mechanisms working together."*

---

## Phase 3: Build CRUD Endpoints Live (25 min)

**Goal:** The "wow" moment. Build real, production-quality CRUD endpoints live, using the agent, and test them in Swagger UI.

> ⚡ **Energy note:** This is one of the two peak moments (with Phase 4). Be animated. Narrate every output. React to what Copilot generates.

### Setup
- App running on 8080 (should still be up from Phase 0)
- Swagger UI open in a browser tab
- Copilot Chat open, ready to invoke the agent

### Demo Script

| Step | Action | What to Say |
|------|--------|-------------|
| 1 | Open Copilot Chat, invoke `@oms-developer` | *"Remember the agent definition from Phase 2? Let's put it to work."* |
| 2 | Prompt: `Create CRUD endpoints for the Supplier entity. A supplier has: name, contactEmail, phone, address, and isActive status. Follow the add-endpoint prompt template.` | *"I'm giving it the business requirement. The agent already knows our architecture, naming, and patterns."* |
| 3 | Watch it generate the **Model** | *"First, the entity. Notice: @Entity, @Table, GenerationType.IDENTITY, validation annotations, timestamps. All from our instructions."* |
| 4 | Watch it generate the **Repository** | *"JpaRepository with a custom derived query — findByIsActiveTrue. It knows our pattern."* |
| 5 | Watch it generate the **Service** | *"Constructor injection. @Transactional. Optional return types. SLF4J logger. All OUR conventions."* |
| 6 | Watch it generate the **Controller** | *"ResponseEntity returns. @Valid on request bodies. Proper HTTP status codes — 201 for create, 204 for delete."* |
| 7 | Accept the changes | Click "Accept" or apply the code |
| 8 | Add seed data to `data.sql` | If the agent didn't do it, prompt: *"Add seed data for 3 suppliers to data.sql"* |
| 9 | Restart the app | `Ctrl+C` the running app, then `mvn spring-boot:run` |
| 10 | Open Swagger UI, expand `/api/suppliers` | *"Brand new endpoints. Let's test them."* |
| 11 | `GET /api/suppliers` → Execute | *"All three seeded suppliers come back."* |
| 12 | `POST /api/suppliers` → Try with a new supplier JSON | *"201 Created. Let's verify..."* |
| 13 | `GET /api/suppliers` again | *"Four suppliers now. Full CRUD, tested live."* |
| 14 | Run tests: `mvn test` | *"Let's make sure we didn't break anything..."* |

### Talk Track
- **Key line:** *"The agent knows our architecture because we taught it in Phase 1. It's not guessing — it's following our rules."*
- *"Count the files it just created: Entity, Repository, Service, Controller, maybe DTOs and tests. That's 15-20 minutes of boilerplate, done in 2 minutes."*
- *"But here's the important part — every line follows our conventions. Constructor injection, Optional, Javadoc, the right annotations. This isn't 'AI-generated code you need to fix.' This is code that passes our review."*
- Point out specific patterns as they appear: *"See the @Transactional(readOnly = true) on the read methods? That came from our instructions."*

### Fallback Plan
- **Agent is slow (>3 min to respond):** Narrate: *"While it's thinking — it's reading our copilot-instructions.md, the agent definition, checking the existing code patterns..."* If it's REALLY slow (>5 min), switch to inline Copilot: manually create the files one by one with Copilot autocomplete. You can also type the class signatures and let Copilot fill in method bodies.
- **Agent generates code that doesn't compile:**
  1. Don't panic. Say: *"AI isn't perfect — let's see what it missed."*
  2. Fix the issue live. This is actually a GOOD teaching moment: *"This is why we have the layered approach. The pre-commit hook would have caught this."*
  3. If the fix is non-trivial, switch to your backup: paste in pre-written Supplier files.
- **Tests fail after adding code:** Fix the failing test live or skip with `-DskipTests` for the demo, noting: *"In a real workflow, we'd fix this before committing. The CI would catch it too."*
- **App won't restart:** Check for port conflicts. `lsof -i :8080` and kill the old process. Or use a different port: `mvn spring-boot:run -Dserver.port=8081`.

### Transition
*"We have a running API with CRUD for orders, inventory, AND suppliers. But customers don't use Swagger UI. Let's give this a face."*

---

## Phase 4: Build Web Portal Live (15 min)

**Goal:** The second "wow" moment. Show Copilot generating a full frontend that connects to the API we just built.

> ⚡ **Energy note:** This is the other peak moment. The payoff of "zero to full-stack in under an hour" lands HERE.

### Setup
- App still running on 8080
- Know the path: `src/main/resources/static/index.html` (Spring Boot auto-serves static content from this directory)

### Demo Script

| Step | Action | What to Say |
|------|--------|-------------|
| 1 | Open Copilot Chat | *"Let's go full stack."* |
| 2 | Prompt: `Create a web dashboard at src/main/resources/static/index.html that displays orders from our API. Include a table showing all orders with their status, a form to create new orders, and the ability to update order status. Style it professionally with a modern look. Use the OutFront Media brand — dark theme with orange accents. Use vanilla HTML/CSS/JS with fetch() to call our REST API.` | *"One prompt. Let's see what happens."* |
| 3 | Watch it generate the HTML | *"It's building the layout, the table, the form..."* |
| 4 | Watch it generate the CSS | *"Dark theme, orange accents — OutFront brand colors."* |
| 5 | Watch it generate the JavaScript | *"fetch() calls to our API. GET to load, POST to create, PATCH to update status."* |
| 6 | Accept and save the file | Apply the changes |
| 7 | Open browser to `http://localhost:8080/` or the Codespace URL | *"No restart needed — Spring Boot serves static files automatically."* |
| 8 | Show the dashboard loading real data | *"There it is. Real orders from our database, in a professional dashboard."* |
| 9 | Create an order through the form | *"Let's add an order..."* → Submit → Show it appearing in the table |
| 10 | Update an order status | *"Change this from PENDING to CONFIRMED..."* → Show the update |

### Talk Track
- **Key line:** *"We went from zero to a full-stack app in under an hour. Zero setup, AI-generated backend, AI-generated frontend, all following our standards."*
- *"Notice it used fetch() — no React, no Angular, no build step. This is intentional for the workshop. In production, you'd use your framework of choice, and Copilot would follow THOSE instructions."*
- *"The AI didn't just generate code — it understood our API structure. The endpoints, the JSON shapes, the status enums. That context came from the codebase."*

### Fallback Plan
- **Generated HTML doesn't work:**
  1. Check the browser console for errors (F12 → Console)
  2. Common issue: wrong API URL. Fix the base URL in the JS to point to the correct host
  3. Common issue: CORS. Shouldn't happen since it's same-origin, but if it does, add `@CrossOrigin` to the controller
  4. If fixing takes >2 min: paste in your pre-built `index.html` fallback. Say: *"Let me use the version I prepared earlier — the concept is the same."*
- **Page is ugly:** *"AI-generated CSS isn't always pixel-perfect. In a real project, you'd have your design system as an instruction file, and Copilot would use that. The point is: the structure and API integration are right."*
- **No data shows up:** Check that the app is running and the API returns data. Hit `/api/orders` in the browser directly. If data.sql didn't seed: restart the app.

### Transition
*"So we've hand-built agents, prompts, and instructions. That's powerful, but it takes effort. What if the system could figure out your team structure automatically?"*

---

## Phase 5: Custom Agents → Squad (10 min)

**Goal:** Show the evolution from manually defined agents to auto-discovered team compositions.

### Setup
- Have `.github/agents/oms-developer.agent.md` open (from Phase 3)
- Be ready to show the `.squad/` directory

### Demo Script

| Step | Action | What to Say |
|------|--------|-------------|
| 1 | Open `.github/agents/oms-developer.agent.md` | *"In Phase 3, we used this hand-crafted agent. I wrote every line — the tools, the context, the workflow."* |
| 2 | Open `.github/agents/code-reviewer.agent.md` | *"Here's another one — a code reviewer agent. Also hand-crafted."* |
| 3 | Show the `.squad/` directory | *"Now look at this. The Squad feature analyzes your repo and automatically creates a team of specialists."* |
| 4 | Open a Squad decision file if available | *"It figured out we need a backend dev, a database specialist, a test writer — based on our actual codebase."* |
| 5 | Invoke Squad in Copilot (if available) | *"Let me ask Squad to help with a cross-cutting task..."* Prompt: `Help me add a new PurchaseOrder feature with API, database changes, and tests` |
| 6 | Show how Squad delegates to specialists | *"See how it's breaking the work into sub-tasks and routing to the right specialist? We didn't program that."* |

### Talk Track
- **Key line:** *"What if you didn't have to define each agent? What if the system figured it out from your codebase?"*
- *"Custom agents are great for teams that know exactly what they need. Squad is for when you want AI to figure out the team structure."*
- *"This is the progression: manual prompts → reusable prompts → agents → auto-discovered squads. Each step is less work for you."*

### Fallback Plan
- **Squad isn't available/working:** This is fine — it's a preview feature. Show the concept by narrating: *"Squad would analyze our repo structure, see the Spring Boot layers, the test patterns, the SQL files, and automatically create specialist agents. Think of it as agents-as-code, generated from your codebase."*
- **Squad generates unexpected output:** Roll with it. Say: *"Part of the value is seeing how AI interprets your codebase. Sometimes it surprises you — and those surprises are usually insights about your own architecture."*

### Transition
*"We've been building locally. Let's see what happens when AI participates in your real GitHub workflow — creating PRs, reviewing code, the whole lifecycle."*

---

## Phase 6: PR Workflow with @copilot (15 min)

**Goal:** Show AI as a full team member in the GitHub workflow — from issue to merged PR.

> ⚠️ **IMPORTANT:** Pre-create the GitHub issue BEFORE the workshop. You'll assign it to @copilot live.

### Setup
- GitHub repo open in browser (Issues tab)
- Pre-created issue ready (e.g., "Add GET /api/suppliers/active endpoint to return only active suppliers")
- Make sure `@copilot` is enabled on the repo (Settings → Copilot → Enable for this repo)

### Demo Script

| Step | Action | What to Say |
|------|--------|-------------|
| 1 | Show the GitHub Issues tab | *"Here's a normal GitHub issue. Just like any other ticket."* |
| 2 | Open the pre-created issue | *"Somebody filed this: 'Add an endpoint for active suppliers.' Typical feature request."* |
| 3 | Assign the issue to `@copilot` | Click "Assignees" → type `copilot` → select | *"Instead of assigning to a developer... I'm assigning to Copilot."* |
| 4 | Wait for Copilot to start working | *"Watch the issue — Copilot is reading it, analyzing our codebase, planning the implementation..."* |
| 5 | Show the PR when it appears | *"There it is — a pull request. Created by Copilot."* |
| 6 | Open the PR, show the diff | *"Look at the code: it followed our architecture. Controller → Service → Repository. Constructor injection. The right annotations."* |
| 7 | Show the CI checks running | *"Our CI pipeline is running automatically — build, tests, CodeQL security scan."* |
| 8 | Review a specific file | *"Let's review this like we would any PR..."* |
| 9 | (Optional) Add a review comment | *"I could request changes and Copilot would address them. Just like a junior dev."* |
| 10 | Merge the PR (or show the merge button) | *"When we're satisfied, we merge. Done."* |

### Talk Track
- **Key line:** *"This is the future — AI as a team member, not just a tool. It reads your issues, follows your standards, creates PRs, responds to review feedback."*
- *"Notice it didn't just dump code. It created a proper PR with a description, linked to the issue, and the code follows our conventions. That's because of everything we set up in Phases 1 and 2."*
- *"The human is still in the loop. You review, you approve, you merge. AI proposes, humans dispose."*
- If someone asks about trust: *"Would you merge a junior dev's PR without reviewing? Same principle. Review everything. The AI accelerates the writing, not the reviewing."*

### Fallback Plan
- **@copilot doesn't respond (>3 min):** Say: *"Copilot is processing — it's reading the entire repo for context, which can take a few minutes."* If it hasn't responded in 5 min, switch to Plan B:
  - Show a pre-created PR from a previous run: *"Let me show you one I triggered earlier — the workflow is identical."*
  - Navigate to the Pull Requests tab and open the pre-existing PR
- **@copilot creates a bad PR:** This is actually good! Say: *"See? This is why human review matters. AI isn't perfect. But look — it got 80% right in 2 minutes. The review cycle would fix the rest."*
- **CI fails on the Copilot PR:** *"The CI caught an issue. In a real workflow, you'd add a review comment, and Copilot would push a fix. This is the system working as designed."*

### Transition
*"Every PR Copilot created was automatically scanned for security issues. Let's look at what's been happening behind the scenes."*

---

## Phase 7: GitHub Advanced Security (10 min)

**Goal:** Show that security is baked into the workflow, not bolted on after the fact.

### Setup
- GitHub repo Security tab open
- Know where to find: CodeQL alerts, Dependabot alerts, secret scanning

### Demo Script

| Step | Action | What to Say |
|------|--------|-------------|
| 1 | Open the Security tab on GitHub | *"This tab has been active the entire time we've been building."* |
| 2 | Show CodeQL results | *"CodeQL analyzed every PR — including the one Copilot created. It checks for SQL injection, XSS, insecure deserialization, and more."* |
| 3 | Show the CodeQL workflow | Open `.github/workflows/codeql.yml` briefly | *"This runs on every push and every PR. Zero manual intervention."* |
| 4 | Show Dependabot alerts (if any) | *"Dependabot monitors our dependencies. If Spring Boot has a CVE, we know immediately."* |
| 5 | Show `SECURITY.md` | *"We even have a security policy. Copilot helped write this too."* |
| 6 | Show secret scanning (Settings → Code security) | *"And secret scanning prevents anyone — human or AI — from committing API keys or passwords."* |
| 7 | Connect back to the workshop | *"Every feature we built today went through this pipeline. The AI writes code, the CI tests it, CodeQL scans it, and a human reviews it. Four layers of quality."* |

### Talk Track
- **Key line:** *"Every PR we merged was automatically scanned. Security isn't a phase — it's a continuous process baked into every push."*
- *"This is GitHub Advanced Security — CodeQL, Dependabot, and secret scanning. It works on every repo, with every PR, whether the code was written by a human or by AI."*
- *"AI-generated code needs MORE scrutiny, not less. GHAS gives you that automatically."*
- If someone asks about false positives: *"CodeQL has a very low false-positive rate. When it flags something, pay attention. You can dismiss with a reason if it's genuinely a false positive."*

### Fallback Plan
- **No CodeQL alerts to show:** That's actually good! Say: *"No alerts means our code is clean. But let me show you what it WOULD catch..."* and show the CodeQL query library or a sample alert from the docs.
- **GHAS not enabled:** Show the workflows in `.github/workflows/` and explain: *"In production, these would be running on every push. Let me show you the workflow definition..."*
- **Running short on time:** Compress to 5 min — just show the Security tab overview and the `codeql.yml` workflow. Skip Dependabot and secret scanning details.

### Transition
*"Let's wrap up. We've covered a LOT in two hours."*

---

## Phase 8: Wrap-Up & Q&A (10 min)

**Goal:** Reinforce the key takeaways, share resources, and create a clear "what to do next" path.

### Setup
- Nothing technical — this is all talk track

### Demo Script

| Step | Action | What to Say |
|------|--------|-------------|
| 1 | Recap the 7 mechanisms | Count them on your fingers or list on screen | See talk track below |
| 2 | Show the repo structure one more time | Quick file tree view | *"Everything you saw today lives in this repo. Fork it, explore it, adapt it."* |
| 3 | Share links | Paste in chat | Codespace link, GitHub repo, workshop labs folder |
| 4 | Point to `workshop-labs/` | *"There are 6 hands-on labs if you want to go deeper after the workshop."* |
| 5 | Open for Q&A | *"What questions do you have?"* |

### Talk Track — The Recap

*"Let's recap what we covered. Seven mechanisms to customize Copilot for your team:*

1. *`copilot-instructions.md` — the 'always on' brain*
2. *File-specific instructions — `.github/instructions/*.instructions.md`*
3. *Prompt files — reusable recipes in `.github/prompts/`*
4. *Chat modes — persona switching in `.github/chatmodes/`*
5. *Skills — composable building blocks in `.github/skills/`*
6. *Agents — autonomous assistants in `.github/agents/`*
7. *Git hooks — guardrails in `.githooks/`*

*Plus GitHub Actions for CI/CD and GHAS for security. All Markdown. All version-controlled. All part of your repo."*

*"The key insight: Copilot is as good as the context you give it. The teams that invest in these instruction files get dramatically better results than teams that use Copilot out of the box."*

*"Everything you saw today is available in your Dev Container. The Codespace will stay active — go explore."*

### Common Q&A Questions and Answers

| Question | Answer |
|----------|--------|
| "How much does this cost?" | *"Copilot Business is $19/user/month. Copilot Enterprise is $39/user/month and includes the agent features. GHAS is separate pricing. Talk to your GitHub rep for exact numbers."* |
| "Does this work with our IDE?" | *"Copilot works in VS Code, JetBrains (IntelliJ, PyCharm, etc.), Neovim, and Visual Studio. The instruction files work everywhere."* |
| "Can AI-generated code be trusted?" | *"Trust, but verify. That's why we showed 4 layers: human review, CI tests, CodeQL scanning, and git hooks. AI accelerates writing, not reviewing."* |
| "How do we get started?" | *"Start with copilot-instructions.md. Write down your team's coding standards. That alone gives you 80% of the value."* |
| "What about proprietary code?" | *"Copilot Business and Enterprise do NOT use your code for training. Your code stays private. Check the Copilot Trust Center for details."* |
| "How do we measure ROI?" | *"Track: time to first PR, PR cycle time, developer satisfaction surveys. Most teams see 30-55% faster task completion in studies."* |

### Fallback Plan
- **No questions:** Have 2-3 "questions I often get" ready. Ask them yourself: *"One question I always get is..."*
- **Hostile question:** Stay calm. Acknowledge the concern. Be honest about limitations. *"That's a fair point. AI isn't perfect for every task. The key is knowing where it helps most — boilerplate, patterns, exploration — and where you still need human expertise."*
- **Running over time:** Cut Q&A short with: *"I want to respect everyone's time. I'll be around after for anyone who wants to chat. And here are my contact details..."*

---

## General Facilitation Tips

### Energy Management
- **Phases 0-2** are setup and explanation — steady, clear, professional energy
- **Phases 3-4** are the "wow" moments — be animated, react to outputs, build excitement
- **Phase 5** is conceptual — dial back to thoughtful, forward-looking tone
- **Phase 6** is impressive but slower-paced — narrate while waiting for Copilot
- **Phases 7-8** are wind-down — confident, summary tone

### Pacing
- If you're **ahead of schedule**: Add more live testing in Phase 3 (test edge cases, try invalid inputs). Let Phase 6 play out in real-time.
- If you're **behind schedule**: Phase 2 can be compressed to 10 min (show files, skip live demos of hooks). Phase 7 can be 5 min (just show Security tab). Phase 5 can be narrated in 5 min without a live demo.
- **Hard stop at 2 hours.** People remember the ending — don't let it be a rushed, apologetic wrap-up.

### When Things Go Wrong
1. **Don't panic.** Narrate what's happening: *"Looks like we hit an issue — let me troubleshoot this live. This is actually a great example of real development..."*
2. **Have your fallbacks ready.** Every phase has a Plan B above. Know them.
3. **Turn failures into lessons.** A Copilot mistake is a chance to show why human review matters. A broken build is a chance to show why CI exists.
4. **Time-box debugging.** If something breaks, give yourself 60 seconds to fix it. If it's not fixed, switch to the fallback and move on.

### Audience Reading
- **Glazed eyes in Phase 2?** They want to see code, not files. Say: *"I know this is a lot of config — let's skip ahead and see it in action."* Jump to Phase 3.
- **Lots of questions in Phase 1?** Great — they're engaged. Let the conversation flow, but watch the clock. You can steal time from Phase 2 or 5.
- **Skepticism about AI-generated code?** Lean into it: *"I love that skepticism. Let's put it to the test..."* and make Phase 3 a challenge: can Copilot match your standards?

---

## Emergency Contacts & Resources

| Resource | URL |
|----------|-----|
| Repo | `https://github.com/<org>/outfront-oms` |
| Codespace | *(fill in before workshop)* |
| Swagger UI | `http://localhost:8080/swagger-ui.html` |
| H2 Console | `http://localhost:8080/h2-console` |
| Copilot Docs | `https://docs.github.com/en/copilot` |
| GHAS Docs | `https://docs.github.com/en/code-security` |
| Workshop Labs | `workshop-labs/` directory in repo |
| Saurabh's backup laptop | *(have it powered on and ready)* |
