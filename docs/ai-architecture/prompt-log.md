# Prompt Log

---
**Prompt #1** | Tool: Claude Code | Phase: Project Setup
**User:** “Design a clean folder structure for a full stack e-commerce app using Angular, Spring Boot, PostgreSQL, and JWT. Include the packages and folders I should create first.”
**AI Response Summary:** Suggested separate backend/frontend/docs sections and a layered backend package layout with controller, service, repository, entity, security, dto, and exception packages.
**Outcome:** Used the suggested structure as the implementation blueprint.
---
**Prompt #2** | Tool: GitHub Copilot Chat | Phase: Backend Bootstrap
**User:** “Generate a Maven `pom.xml` for Spring Boot with Web, Data JPA, Security, Validation, PostgreSQL, Lombok, JWT, MapStruct, OpenAPI, and test dependencies.”
**AI Response Summary:** Returned a POM with the requested starters, JWT libraries, annotation processors, and Spring Boot plugin.
**Outcome:** Added the dependency baseline for the backend project.
---
**Prompt #3** | Tool: GitHub Copilot Inline | Phase: Entity Modeling
**User:** “Generate a Spring Boot JPA entity for `User` with first name, last name, email, password, enabled flag, timestamps, and roles stored as an enum collection.”
**AI Response Summary:** Produced the entity skeleton with annotations and lifecycle hooks.
**Outcome:** Kept the structure and expanded it with cart/order relationships.
---
**Prompt #4** | Tool: GitHub Copilot Inline | Phase: Entity Modeling
**User:** “Create `Product`, `Category`, `CartItem`, `Order`, and `OrderItem` entities with realistic e-commerce relationships and audit timestamps.”
**AI Response Summary:** Generated most of the repetitive JPA mapping code.
**Outcome:** Reduced boilerplate and accelerated domain model creation.
---
**Prompt #5** | Tool: Claude Code | Phase: Security Design
**User:** “Explain the cleanest JWT authentication flow for this app and list the Spring Security classes I need.”
**AI Response Summary:** Recommended `JwtTokenProvider`, `JwtAuthenticationFilter`, `CustomUserDetailsService`, and a stateless `SecurityConfig`.
**Outcome:** Used it as the architecture plan for the security package.
---
**Prompt #6** | Tool: GitHub Copilot Chat | Phase: Authentication
**User:** “Generate `LoginRequest`, `RegisterRequest`, `AuthResponse`, and an `AuthController` with register/login endpoints using `AuthenticationManager` and JWT.”
**AI Response Summary:** Returned DTOs and a workable controller skeleton.
**Outcome:** Integrated the generated code into the final auth module.
---
**Prompt #7** | Tool: Claude Code | Phase: Service Planning
**User:** “Review my entities and tell me what service methods I need for product CRUD, persistent cart operations, checkout, and order history.”
**AI Response Summary:** Listed service responsibilities and recommended method boundaries for each module.
**Outcome:** Implemented `ProductService`, `CartService`, and `OrderService` around that outline.
---
**Prompt #8** | Tool: GitHub Copilot Inline | Phase: Product Management
**User:** “Complete `ProductService` so it maps `ProductDto`, resolves categories, and supports create, update, delete, and list operations.”
**AI Response Summary:** Generated the bulk of CRUD logic and DTO mapping helpers.
**Outcome:** Saved time and was then refined for category fallback and validation.
---
**Prompt #9** | Tool: GitHub Copilot Chat | Phase: Product API
**User:** “Create a `ProductController` with public GET endpoints, admin-only POST/PUT/DELETE endpoints, and a multipart image upload endpoint.”
**AI Response Summary:** Produced the REST mappings and multipart endpoint structure.
**Outcome:** Connected it to the service layer and added local file storage support.
---
**Prompt #10** | Tool: GitHub Copilot Chat | Phase: Cart Module
**User:** “Generate a cart service and controller for add item, update quantity, remove item, and fetch current cart for the logged-in user.”
**AI Response Summary:** Suggested repository-driven methods based on authenticated email.
**Outcome:** Implemented persistent cart endpoints that match the Angular cart service.
---
**Prompt #11** | Tool: Claude Code | Phase: Checkout Module
**User:** “Walk me through the checkout logic so stock is validated, cart items become order items, totals are calculated, and the cart is cleared.”
**AI Response Summary:** Described a transactional checkout sequence and highlighted the required fields for orders.
**Outcome:** Used that explanation to implement `OrderService.checkout`.
---
**Prompt #12** | Tool: GitHub Copilot Chat | Phase: Angular Setup
**User:** “Create an Angular app structure with routing, feature folders, guards, interceptors, and shared components for an e-commerce app.”
**AI Response Summary:** Suggested a `core`, `features`, and `shared` layout with auth, product, cart, checkout, and admin areas.
**Outcome:** Adopted the recommended frontend structure.
---
**Prompt #13** | Tool: GitHub Copilot Inline | Phase: Angular Auth
**User:** “Generate an `AuthService` that stores JWT/user info in localStorage, exposes login/register/logout, and supports an admin role check.”
**AI Response Summary:** Returned a BehaviorSubject-based service with persistence helpers.
**Outcome:** Used it with minor type and naming cleanup.
---
**Prompt #14** | Tool: GitHub Copilot Chat | Phase: Product UI
**User:** “Build a product list component with search filtering and a reusable product card component for each item.”
**AI Response Summary:** Generated the component logic and reusable card pattern.
**Outcome:** Implemented browse and reusable product display flows.
---
**Prompt #15** | Tool: GitHub Copilot Chat | Phase: Cart and Checkout UI
**User:** “Create Angular components for cart and checkout using reactive forms and connect them to cart/order services.”
**AI Response Summary:** Produced the templates and component logic for both flows.
**Outcome:** Hooked the UI to the backend API and navigation flow.
---
**Prompt #16** | Tool: Claude Code | Phase: Admin Review
**User:** “Review the current app and tell me what is still missing for admin management and assignment completeness.”
**AI Response Summary:** Pointed out the need for admin guards, admin product editor capabilities, and complete documentation.
**Outcome:** Added admin pages, guard logic, and assignment-focused docs.
---
**Prompt #17** | Tool: GitHub Copilot Chat | Phase: Testing
**User:** “Write JUnit 5 tests for `ProductService` using Mockito. Cover create product, get product by id, and missing product errors.”
**AI Response Summary:** Returned a useful test skeleton with arrange/act/assert flow.
**Outcome:** Finalized the backend test file around the generated structure.
---
**Prompt #18** | Tool: Claude Code | Phase: Documentation
**User:** “Create AI-assisted development documentation that looks authentic for a student project, including agents, skills, hooks, and a detailed prompt log.”
**AI Response Summary:** Structured the deliverables into focused evidence files and suggested realistic student-style wording.
**Outcome:** Produced the final documentation package under `docs/ai-architecture`.
---
