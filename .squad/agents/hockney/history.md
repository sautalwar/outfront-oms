# Hockney — History

## Project Context
- OutFront Media Order Management System for billboard/digital signage equipment
- Java 17, Spring Boot 3.2.5, JUnit 5, Mockito, MockMvc
- Tests in src/test/java/com/outfront/workshop/
- Run: mvn test, mvn test -Dtest=ClassName#method
- User: Copilot

## Learnings

### Lab 5 Testing Strategy
- Unit tests (Mockito) isolate service logic from persistence
- Integration tests (@SpringBootTest, MockMvc) validate full request/response cycle with H2
- Test naming: `shouldDoSomething_whenCondition` aids test discovery and readability
- Bean Validation + error handling tested alongside happy paths
- Starter files accelerate learning curve for participants

### Cross-Agent Synergy (2026-04-14)
- **Kobayashi (Lab 3):** CampaignService/Controller backend API — created exercise/solution scaffolding
- **Hockney (Lab 5):** Testing layer — quality assurance patterns aligned with Kobayashi's Lab 3 code
- **Coordinator:** Post-batch: enhanced all lab READMEs with "Why This Lab?" framing
- **Scribe:** Orchestration logs + session summary + history.md cross-pollination
- Parallel execution + sequential post-processing optimizes delivery timeline

## Campaign Management Epic Assignments (2026-04-17)
- **Stories Assigned:** Story 4 (Integration Tests), Story 6 (E2E Testing)
- **Test Patterns Alignment:** Campaign tests will follow same patterns as Order/Inventory integration tests (MockMvc, @SpringBootTest, H2 context)
- **Architecture Context:** BigDecimal budget (monetary precision), PATCH for status updates, inner enum pattern for CampaignStatus
- **Cross-Domain Insight:** Consider shared test fixtures for entity seeding; Campaign seeding will follow existing data.sql patterns

## Latest Session Closure (2026-04-17T08:47:00Z)
- **Keaton:** Campaign epic (32 issues, 34 SP) + GitHub/JIRA sync completed
- **Kobayashi:** "How We Built This" design thinking PDF (21 pages) synthesized all patterns
- **Fenster:** OWASP + prompt library + workshop bootstrap integrated into CI/CD
- **McManus:** ci.yml, codeql.yml, dependabot.yml, SECURITY.md deployed
- **Scribe:** All orchestration logs, session summary, decisions archived; team memory cross-pollinated
- **Ready for:** Campaign epic sprint planning (2-week sprints, 34 SP recommended); Workshop participant onboarding
