# Fenster — History

## Project Context
- OutFront Media Order Management System for billboard/digital signage equipment
- Java 17, Spring Boot 3.2.5, Maven, H2/SQL Server, JUnit 5
- Two domains: Orders and Inventory with cross-domain check in OrderService.updateOrderStatus()
- Controller → Service → Repository pattern, constructor injection only
- User: Copilot

## Recent Work (2026-04-14)
- Created workshop-prompts/ directory with 7 reusable prompt templates
- Prompts cover: Order design, Inventory domain, API endpoints, tests, cross-domain validation, error handling, performance
- Each includes context, instructions, expected outputs
- README provides quick-reference index for participants

## Learnings

### Prompt Library Design
- Domain-specific prompts (OutFront Media billboard/signage context) increase participant relevance
- Structuring prompts with context + instructions + expected output reduces interpretation errors
- Participants benefit from fork-and-iterate approach vs. starting from scratch

### Cross-Agent Patterns
- Kobayashi: Presenter guide can showcase prompts in real-time (live coding demos)
- Redfoot: Competitive comparison can show Copilot prompt capability vs. alternatives
- Verbal: UI enhancements support interactive exploration of prompt outputs
