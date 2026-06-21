# Specialized Subagents / Roles

## Code Generation Subagent

- **Tool:** GitHub Copilot inline completions
- **Role:** Generate high-volume implementation code once a file and intent were defined.
- **Example tasks:**
  - Spring JPA entity scaffolding
  - Angular service CRUD methods
  - HTML templates for forms, cards, and tables
- **Prompt examples:**
  - “Generate a JPA entity for Product with price, stockQuantity, imageUrl, and a Category relation.”
  - “Complete this Angular service with methods for list/get/create/update/delete products.”

## Architecture Design Subagent

- **Tool:** Claude Code (Claude Sonnet)
- **Role:** Design structure and make cross-cutting decisions.
- **Example tasks:**
  - Package and folder design
  - JWT security flow planning
  - Mapping frontend screens to backend endpoints
- **Prompt examples:**
  - “Design the package structure for an Angular + Spring Boot e-commerce app with JWT auth.”
  - “List the missing classes needed for secure stateless login.”

## Test Generation Subagent

- **Tool:** GitHub Copilot Chat (`/tests`-style workflow)
- **Role:** Generate unit test skeletons and identify useful edge cases.
- **Example tasks:**
  - Mockito tests for `ProductService`
  - Error-path ideas for checkout and cart update logic
- **Prompt examples:**
  - “Write JUnit 5 tests for ProductService create/get flows.”
  - “List the most important tests for JWT and checkout.”

## Documentation Subagent

- **Tool:** GitHub Copilot Chat + Claude Code
- **Role:** Transform code changes into submission-quality documentation.
- **Example tasks:**
  - README drafting
  - Prompt-log formatting
  - AI workflow explanations
- **Prompt examples:**
  - “Draft a README with setup, endpoints, and repo structure.”
  - “Turn these implementation notes into AI-assisted development evidence.”

## Debugging Subagent

- **Tool:** GitHub Copilot Chat
- **Role:** Explain compiler/runtime issues and suggest fixes quickly.
- **Example tasks:**
  - Security 401/403 investigation
  - Angular DI/import errors
  - Entity mapping and serialization problems
- **Prompt examples:**
  - “Why is my JWT filter still returning 403 for valid tokens?”
  - “Explain this Angular `NullInjectorError` and show the missing import.”

## Summary

These “subagents” describe how different AI capabilities were used for different jobs during development. That role-based framing reflects a realistic student workflow rather than pretending a single AI prompt built the entire project in one step.
