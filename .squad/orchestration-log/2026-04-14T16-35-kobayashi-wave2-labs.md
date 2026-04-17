# Kobayashi Spawn — Wave 2 (Labs)

**Timestamp:** 2026-04-14T16:35:00Z  
**Agent:** Kobayashi (general-purpose, claude-sonnet-4.5)  
**Mode:** background  
**Duration:** 327s  
**Status:** COMPLETED ✓

## Mandate
Wrote Lab 1 exercises (5 files) + solutions (4 files) + lab README in workshop-labs/lab-01-prompts-skills-agents/.

## Deliverables
- **workshop-labs/lab-01-prompts-skills-agents/README.md** — Lab 1 overview, learning objectives, estimated time (60 min)
- **Exercises (5 files):**
  - 01_explore-lab-structure.md — Explore workshop-labs/ directory structure and MCP server
  - 02_create-path-scoped-skill.md — Create a path-scoped skill for Order domain validation
  - 03_use-prompts-for-inventory.md — Use workshop-prompts/ library to extend Inventory API
  - 04_create-custom-agent.md — Create a custom agent for cross-domain order-inventory validation
  - 05_build-end-to-end.md — Integrate skill + agent into Spring Boot project
- **Solutions (4 files):**
  - SOLUTION_01.md — Sample MCP server exploration commands
  - SOLUTION_02.md — Skill implementation scaffold + test cases
  - SOLUTION_03.md — Prompt-driven API extension using existing templates
  - SOLUTION_04.md — Custom agent routing logic with cross-domain checks

## Pedagogical Design
- Progressive difficulty: explore → skill → prompt reuse → agent → integration
- Each exercise is self-contained but builds on prior learnings
- Solutions show one valid approach; multiple correct answers encouraged

## Cross-Agent Notes
- Fenster: Prompts library (workshop-prompts/) used in Exercise 03
- Redfoot: Competitive Q&A can address "why use Copilot for this?" in lab discussions
- Verbal: Interactive UI supports live coding during lab walkthroughs
