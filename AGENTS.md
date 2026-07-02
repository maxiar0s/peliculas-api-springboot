# AGENTS.md

## Purpose
- This file defines the rules AI agents must follow in this repository.
- Prioritize small, clear, reviewable, and safe changes; implement scoped tasks, open Pull Requests, and provide evidence for human review.

## Project Shape
- Single Spring Boot 3.4.4 Maven service, Java 17, root package `com.peliculas_api`.
- Main entrypoint is `src/main/java/com/peliculas_api/PeliculasApiApplication.java`; REST endpoints live under `/peliculas` in `PeliculaController`.
- Persistence is Spring Data JPA/MySQL in runtime; tests use H2 MySQL mode from `src/test/resources/application.properties`.

## Branch Workflow
- For any feature, bugfix, improvement, or refactor, create a new branch named `codex/<branch_name>`.
- Use `develop` as the base branch unless the issue explicitly states another base branch.
- Never work directly on `develop`, `main`, `master`, or any protected branch.
- Branch names must be descriptive, short, and kebab-case, for example `codex/add-login-validation` or `codex/fix-user-profile-error`.
- Do not mix unrelated features, fixes, or refactors in the same branch.

## Pull Requests
- All changes targeting `develop` must go through a Pull Request.
- Before opening a PR: create a `codex/<branch_name>` branch, implement the scoped change, and run the project validation commands.
- Open PRs against `develop` and wait for human review.
- Never merge directly into `develop`, push directly to `develop`, approve your own PR, merge your own PR, bypass review, or force a merge with elevated permissions.
- Every agent-created PR must be approved by at least one person other than the PR author before merge.

## PR Description
- Include a clear summary, main files modified, validation commands executed, test/lint/build results, known risks, and pending items.
- Use this format:

```md
## Summary
Briefly describe what was changed and why.

## Changes Made
- 
- 
- 

## Validation
- [ ] mvnw.cmd test

## Risks or Considerations
- 

## Pending Items
- 
```

- If any validation command could not be executed, state that explicitly and explain why.

## Updating AGENTS.md
- Update `AGENTS.md` only when truly necessary; do not use it as a changelog.
- Propose updates only for important structural changes, new architecture or folder organization, centralized helpers/services/styles, naming conventions, rules for components/services/endpoints/modules, required validation commands, branch/PR/review workflow changes, security restrictions, styling-system changes, or technical conventions future agents must follow.
- Do not update `AGENTS.md` for small or isolated changes, feature-specific details, or without justifying it in the PR description.
- Include any `AGENTS.md` update in the same PR that introduces the related convention or structural change.
- An `AGENTS.md` update is valid only after a PR has been approved by another person and merged.
- Never approve or merge a PR that modifies `AGENTS.md` by yourself.

## Security Rules
- Do not read, modify, or expose `.env` files.
- Do not create, print, or modify secrets, tokens, private keys, or credentials.
- Do not add hardcoded credentials or modify production configuration without explicit instruction.
- Do not change authentication, authorization, roles, permissions, or deployment pipelines without human review or explicit approval.
- Do not add new dependencies without justifying the reason.
- Do not execute destructive commands without explicit human authorization.
- Forbidden unless explicitly authorized by a human: `rm -rf`, `git push --force`, `git reset --hard`, `git clean -fd`, `npm publish`, `docker system prune`, `kubectl delete`, `terraform destroy`.

## Working Style
- Make small, focused changes and preserve the existing project style.
- Reuse existing components, functions, and patterns before creating new ones.
- Avoid duplicated logic and prefer simple solutions over unnecessary abstractions.
- Document relevant decisions in the PR.
- Ask for clarification when the task is ambiguous or risky.
- Stop if an action could affect production, real data, security, or repository integrity.
- Do not rewrite complete modules without necessity, change API contracts without stating it, modify unrelated areas in one task, add libraries for convenience, include large refactors inside small feature tasks, or hide build/test errors.

## Commands
- Use the Maven wrapper: `./mvnw test` on Unix-like shells or `mvnw.cmd test` on Windows PowerShell.
- Run one test class with `mvnw.cmd -Dtest=PeliculaServiceTest test` or one method with `mvnw.cmd -Dtest=PeliculaServiceTest#deberiaActualizarPeliculaCuandoExiste test`.
- Start locally with `mvnw.cmd spring-boot:run`; it requires `.env` values because `application.properties` imports `optional:file:.env[.properties]` and uses unresolved `${SERVER_PORT}` / `${DB_*}` placeholders.
- Start the app plus MySQL with `docker compose up --build`; MySQL uses `mysql:8.4`, container name `peliculas-mysql`, and port mapping from `${DB_PORT}:3306`.

## Validation
- Before opening a PR, run this project's validation command: `mvnw.cmd test` on Windows PowerShell or `./mvnw test` on Unix-like shells.
- If the project later adds lint, test, build, or other validation scripts, follow `README.md`, manifests, configuration files, or internal documentation.
- If validation fails, try to fix failures related to your changes; never hide failures, and report whether they appear pre-existing.

## Environment
- Copy `.env.example` to `.env` for local runtime; `.env` is gitignored.
- Default example DB port is `33106`, so local JDBC is `jdbc:mysql://localhost:33106/...`; inside Docker the API overrides `DB_HOST=mysql` and `DB_PORT=3306`.
- Runtime schema management is `spring.jpa.hibernate.ddl-auto=update`; tests use `create-drop`.

## Behavior To Preserve
- `DataSeeder` inserts exactly five movies only when the repository is empty; controller integration tests assume those five rows and their generated IDs/order.
- List responses are HATEOAS `CollectionModel` payloads using collection relation `peliculas`; tests assert `$._embedded.peliculas` and `_links` paths.
- `PeliculaRepository.findAllByOrderByIdAsc()` controls list ordering; changing it can break response order assumptions.
- OpenAPI is exposed at `/v3/api-docs`, Swagger UI at `/swagger-ui.html`, and tests assert the title `Peliculas API` plus `/peliculas` GET summary.

## Notes
- There is no separate lint/format/typecheck config in this repo; `mvnw.cmd test` is the practical verification command.
- `HELP.md` is generated Spring Initializr guidance and is ignored by git; prefer `pom.xml`, `application.properties`, and tests as sources of truth.

## Expected Result
- Agent work should finish as a PR targeting `develop`, created from a `codex/<branch_name>` branch, with clear, validated changes ready for human review.
- No agent-generated change may reach `develop` without a PR, human review, and approval from a person other than the agent.
