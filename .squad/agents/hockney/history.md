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
