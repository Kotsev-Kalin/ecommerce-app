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
---**Prompt #19** | Tool: GitHub Copilot Agent | Phase: Java Upgrade
**User:** "Upgrade Java runtime to the latest LTS version for this project (backend). Update project build files, compiler settings, and run/build tests as needed."
**AI Response Summary:** Ran a full incremental upgrade workflow — assessed current state (Java 17, Spring Boot 3.3.1), generated an upgrade plan, upgraded `pom.xml` to Spring Boot 3.5.15 and Java 25 (Microsoft JDK), bumped MapStruct to 1.6.3 and Lombok to 1.18.46, fixed a pre-existing compile bug in `CustomUserDetailsService.java` (List passed to varargs), updated CI/CD pipeline to Java 25, and confirmed 3/3 tests passing with no CVEs.
**Outcome:** Backend fully upgraded to Java 25 and Spring Boot 3.5.15. Changes committed to branch `appmod/java-upgrade-20260622122015`.
---
**Prompt #20** | Tool: GitHub Copilot Agent | Phase: Dev Environment Setup
**User:** "Setup the system so this command works: `mvn clean install -U` from the workspace root."
**AI Response Summary:** Created a root `pom.xml` multi-module POM with `backend` as a module, added Maven 3.9.4 (`C:\Users\kotse\.maven\apache-maven-3.9.4\bin`) to the user PATH permanently, and set `JAVA_HOME` to the JDK 25 installation permanently via `[System.Environment]::SetEnvironmentVariable`.
**Outcome:** `mvn clean install -U` now works from the workspace root in any new terminal session.
---
**Prompt #21** | Tool: GitHub Copilot Agent | Phase: Frontend Build
**User:** "Build the frontend."
**AI Response Summary:** Diagnosed that Node.js (`v24.17.0`) was installed at `C:\Program Files\nodejs` but not on the PATH; added it permanently to the user PATH. Fixed PowerShell execution policy (`RemoteSigned`) blocking npm scripts. Resolved Angular build failure caused by `@types/node` 22.x (requiring TypeScript 5.2+) being incompatible with Angular 15's TypeScript 4.9 — pinned `@types/node` to `^18.19.0` in devDependencies. Build completed successfully.
**Outcome:** Angular 15 frontend built successfully — output in `frontend/dist/ecommerce-frontend/` (336.89 kB initial bundle, 88.34 kB gzipped).
---