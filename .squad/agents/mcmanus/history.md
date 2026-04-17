# McManus — History

## Project Context
- OutFront Media Order Management System for billboard/digital signage equipment
- Java 17, Spring Boot 3.2.5, Maven
- Work items managed in Azure DevOps
- Team: Keaton (Lead), Verbal (Frontend), Fenster (Backend), Hockney (Tester)
- User: Copilot

## Campaign Management Epic CI/CD Setup (2026-04-17)
- **CI/CD Infrastructure:** ci.yml, codeql.yml, dependabot.yml for automated build/test/security
- **Security Policies:** SECURITY.md (vulnerability reporting, SLA, supported versions)
- **Branch Protection:** branch-protection-recommended.md (PR review, status checks, up-to-date requirement)
- **Integration:** api-tests.yml (32 endpoint tests) integrated into CI pipeline
- **Cross-Agent:** OWASP scanning (Fenster) + CodeQL (McManus) form multi-layered security gate

## Latest Session Closure (2026-04-17T08:47:00Z)
- **Campaign epic:** 32 issues created (GitHub + JIRA sync) with full orchestration logging
- **CI/CD ready:** All workflows deployed; security gates active; test automation running
- **Team memory:** Decisions archived, orchestration logs written (9 agents), session summary complete
- **Ready for:** Sprint execution, campaign feature development, workshop participant delivery
