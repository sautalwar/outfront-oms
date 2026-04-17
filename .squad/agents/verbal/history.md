# Verbal — History

## Project Context
- OutFront Media Order Management System for billboard/digital signage equipment
- Java 17, Spring Boot 3.2.5, Maven, H2/SQL Server, JUnit 5
- Swagger UI at /swagger-ui.html, static web app config
- User: Copilot

## Recent Work (2026-04-14)
- Added T-key toggle to Workshop_Presenter_Guide.html
- Enables presenter to hide/show: talk tracks, click-by-click, expected outcomes, curveball Q&A
- Lightweight JavaScript with localStorage state persistence
- No breaking changes to existing functionality

## Learnings

### UI Enhancement Strategy
- Small, focused features (T-key toggle) dramatically improve presenter experience
- Keyboard shortcuts are faster than UI buttons for live presentation
- State persistence (localStorage) provides seamless multi-session experience

### Cross-Agent Patterns
- Kobayashi: Content structure (20 slides, detailed notes) benefits from collapsible UI
- Redfoot: Q&A prep visibility control helps presenter manage confidence level
- Fenster: Prompt library could benefit from similar toggle for hiding/showing expected outputs during hands-on

## Campaign Management Epic Assignments (2026-04-17)
- **Stories Assigned:** Story 1 (Entity & Domain Model), Story 3 (API Controllers & Endpoints)
- **UI/API Integration:** Campaign API will expose RESTful endpoints (PATCH for status); Swagger UI integration via existing SpringDoc setup
- **Architecture Context:** Inner enum pattern for CampaignStatus, constructor injection, GlobalExceptionHandler exception mapping
- **Presenter Value:** Campaign entity demo aligns with Workshop_Presenter_Guide.html — consider adding Campaign API endpoint showcase
