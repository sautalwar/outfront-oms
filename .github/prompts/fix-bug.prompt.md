---
mode: agent
description: "Systematically fix a bug in the OMS"
tools:
  - codebase
  - terminal
---

Fix the following bug: {{bug_description}}

Follow this workflow:

1. **Understand** — Clarify expected vs actual behavior
2. **Locate** — Find the affected files using @workspace search
3. **Root Cause** — Trace the issue through Controller → Service → Repository
4. **Fix** — Make the minimal change that fixes the bug
5. **Regression Test** — Add a test that would have caught this bug
6. **Verify** — Run `mvn test` to confirm the fix and no regressions
