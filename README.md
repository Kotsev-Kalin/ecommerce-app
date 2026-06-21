# Full Stack E-Commerce Application

A full stack e-commerce application built with Angular, Spring Boot, and PostgreSQL. The project demonstrates customer and admin workflows, JWT-based authentication, product CRUD, cart persistence, checkout, order history, and AI-assisted development documentation for an academic assignment.

## Tech Stack

![Angular](https://img.shields.io/badge/Frontend-Angular%2015+-DD0031?logo=angular&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Backend-Spring%20Boot-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-336791?logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/Auth-JWT-000000?logo=jsonwebtokens&logoColor=white)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white)
![Angular CLI](https://img.shields.io/badge/Tooling-Angular%20CLI-DD0031?logo=angular&logoColor=white)

## Project Description

The application provides:

- JWT-based register/login
- Profile and order history
- Product listing, filtering, and detail pages
- Admin product management and image upload
- Persistent cart for authenticated users
- Checkout and order creation
- Admin order dashboard
- AI-assisted development evidence under `docs/ai-architecture`

## Repository Structure

```text
ecommerce-app/
├── backend/
│   ├── pom.xml
│   └── src/
├── frontend/
│   ├── package.json
│   └── src/
├── docs/
│   └── ai-architecture/
├── .github/
│   └── workflows/ci.yml
├── .env.example
└── README.md
```

## Prerequisites

- Java 17+
- Maven 3.9+
- Node.js 18+
- npm 9+
- PostgreSQL 14+
- Angular CLI 15+

## Setup

1. Create a PostgreSQL database named `ecommerce_db`.
2. Copy `.env.example` values into your local environment or IDE run configuration.
3. Start the backend.
4. Start the frontend.

## Environment Variables (`.env.example`)

| Variable | Description | Example |
| --- | --- | --- |
| `DB_URL` | PostgreSQL JDBC URL | `jdbc:postgresql://localhost:5432/ecommerce_db` |
| `DB_USERNAME` | Database username | `postgres` |
| `DB_PASSWORD` | Database password | `postgres` |
| `JWT_SECRET` | JWT signing secret | `change-this-secret-key-change-this-secret-key` |
| `JWT_EXPIRATION_MS` | JWT expiration in ms | `86400000` |
| `SERVER_PORT` | Backend port | `8080` |
| `FILE_UPLOAD_DIR` | Local upload directory | `./uploads` |
| `ANGULAR_API_URL` | Frontend API base URL | `http://localhost:8080/api` |

## Run Backend

```bash
cd backend
mvn clean test
mvn spring-boot:run
```

Backend URLs:

- API: `http://localhost:8080/api`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Run Frontend

```bash
cd frontend
npm install
npm start
```

Frontend URL:

- `http://localhost:4200`

## API Endpoints Overview

| Area | Method | Endpoint | Description |
| --- | --- | --- | --- |
| Auth | POST | `/api/auth/register` | Register user |
| Auth | POST | `/api/auth/login` | Login and receive JWT |
| Users | GET | `/api/users/me` | Fetch current profile |
| Products | GET | `/api/products` | List/search products |
| Products | GET | `/api/products/{id}` | Product details |
| Products | POST | `/api/products` | Admin creates a product |
| Products | PUT | `/api/products/{id}` | Admin updates a product |
| Products | DELETE | `/api/products/{id}` | Admin deletes a product |
| Products | POST | `/api/products/{id}/image` | Admin uploads product image |
| Cart | GET | `/api/cart` | Fetch current cart |
| Cart | POST | `/api/cart` | Add item to cart |
| Cart | PUT | `/api/cart/{productId}` | Update cart quantity |
| Cart | DELETE | `/api/cart/{productId}` | Remove cart item |
| Orders | POST | `/api/orders/checkout` | Place order |
| Orders | GET | `/api/orders` | User order history |
| Orders | GET | `/api/orders/admin/all` | Admin views all orders |

## Screenshots

_Add screenshots before final submission._

- Home page
- Product listing
- Product details
- Cart
- Checkout
- Login/Register
- Admin dashboard

## AI-Assisted Development Documentation

- `docs/ai-architecture/agents.md`
- `docs/ai-architecture/subagents.md`
- `docs/ai-architecture/skills.md`
- `docs/ai-architecture/hooks.md`
- `docs/ai-architecture/prompt-log.md`

## License

MIT License.
