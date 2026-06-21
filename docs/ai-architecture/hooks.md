# Hooks Used in the Development Workflow

This project documents both traditional development hooks and AI workflow hooks.

## Pre-commit Hooks

Pre-commit hooks are useful guardrails before code reaches the repository.

### Example script

```bash
#!/usr/bin/env bash
set -e
echo "Running backend tests..."
(cd backend && mvn test)
echo "Building frontend..."
(cd frontend && npm install && npm run build)
```

### Purpose

- Block commits that fail backend tests
- Catch frontend template/build errors early
- Force validation after AI-generated edits

## GitHub Actions CI/CD Pipeline

The repository includes a CI workflow under `.github/workflows/ci.yml`.

### Responsibilities

- Set up Java 17 and Node 18
- Run backend tests
- Install frontend dependencies
- Build the Angular app

### Example configuration excerpt

```yaml
- name: Run backend tests
  working-directory: backend
  run: mvn --batch-mode clean test

- name: Build frontend
  working-directory: frontend
  run: |
    npm install
    npm run build
```

## Copilot Workspace Hooks

These are workflow triggers rather than literal Git hooks. During development, the “hook” moments were:

- Opening a file and receiving inline scaffold suggestions
- Highlighting an error and asking Copilot Chat for an explanation
- Accepting a generated method body, then immediately running validation
- Asking for `/doc`-style assistance after completing a feature

### Practical examples

- Opening `ProductService.java` triggered mapping suggestions.
- Opening `login.component.ts` triggered submit-handler suggestions.
- Opening `SecurityConfig.java` triggered filter-chain suggestions.

## Post-generate Hooks

Post-generate hooks describe what was done after AI output was accepted.

### Example verification script

```bash
#!/usr/bin/env bash
set -e
echo "Verifying generated backend code"
(cd backend && mvn test)
echo "Verifying generated frontend code"
(cd frontend && npm run build)
```

### Post-generate checklist

1. Re-read the generated code.
2. Fix imports and naming mismatches.
3. Run backend tests or frontend build.
4. Ask a follow-up AI prompt if the result is unclear.
5. Commit only after validation.

## Why Hooks Matter for AI-Assisted Development

Hooks are what turn AI speed into dependable output. Without hooks, the workflow becomes “generate and hope.” With hooks, it becomes “generate, review, validate, refine.”
