---
mode: agent
description: "Generate comprehensive tests for a class"
tools:
  - codebase
  - terminal
---

Write tests for {{class_name}} following these guidelines:

**For Controllers** (`@WebMvcTest`):
- Test each endpoint (GET, POST, PUT, DELETE)
- Test 200/201 success, 404 not found, 400 validation errors
- Use MockMvc and @MockBean for service dependencies
- Verify response JSON structure with jsonPath()

**For Services** (`@ExtendWith(MockitoExtension.class)`):
- Test happy path and error cases
- Test business rule validation
- Mock repository calls with Mockito
- Verify interactions with verify()

**Naming**: `shouldDoSomething_whenCondition`
**Pattern**: Given (arrange) / When (act) / Then (assert)

Run `mvn test` after generating to verify all tests pass.
