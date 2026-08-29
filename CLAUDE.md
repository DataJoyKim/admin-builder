# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Admin Builder is a Spring Boot service that lets non-developers generate standard CRUD/admin pages through a web console instead of hand-coding them. A "console" UI defines entities, views, workflows, and data sources; the runtime interprets those definitions at request time to serve the actual pages and APIs. Java 17, Spring Boot 3.3.4, Thymeleaf + AdminLTE for the UI, H2 as the embedded application database (with MySQL/MariaDB support for connecting to *target* databases the generated admin pages manage).

## Commands

- Run the app: `./gradlew bootRun` (Windows: `gradlew.bat bootRun`) — serves on port 8080, console at `/console`
- Build: `./gradlew build`
- Run all tests: `./gradlew test`
- Run a single test class: `./gradlew test --tests "com.datajoy.admin_builder.workflow.WorkflowServiceTest"`
- Run a single test method: `./gradlew test --tests "com.datajoy.admin_builder.workflow.WorkflowServiceTest.methodName"`

Dev settings live in `src/main/resources/application.yml`: `spring.devtools.restart`/`livereload` are on, Thymeleaf/static resource caching is disabled, and `hibernate.ddl-auto: update` auto-migrates the embedded H2 schema at `C:\admin-builder\data\admin-builder` on startup. The H2 console is exposed at `/h2-console`.

## Architecture

### Two layers: the console (design-time) and the runtime (serve-time)

- **Console** (`console/`, templates under `templates/console/**`): a Thymeleaf+AdminLTE SPA-ish UI at `/console/*` where an admin defines entities, views, workflows, data sources, users/authorities, common codes, etc. `ConsoleViewController` is a single generic route (`/console/{path}` → `console/contents/{path}.html`) — most console screens are just static content panels that call the REST endpoints in `console/rest/*RestController` to CRUD the definition tables. `ConsoleSecurityFilter` (registered in `FilterConfig` for `/console/*`) gates the whole console behind authentication + a "console access" authority code.
- **Runtime** (`view/`, `templates/pages/**`, `templates/template/**`): serves the pages an end user actually visits, driven by data saved via the console. `ViewBuilderController`:
  - `GET /` renders `pages/index` (optionally behind auth, per `Layout.useAuthValidation`).
  - `GET /pages/{objectCode}` looks up a `ViewObject` by code and dispatches based on `ObjectType`: `FILE` → `template/mf-template` (a plain custom HTML/JS page authored outside the builder), `VIEW_BUILDER` → `template/view-builder-template` (a page whose layout/fields were assembled visually in the console). Per-object auth (`useAuthValidation`) and authority checks (`useAuthorityValidation`, via `ViewObjectService.validateAuthorization`) gate access to individual pages, mirroring the console's own auth filter.

### Workflows are the request-handling unit for generated pages

Generated pages don't call bespoke controllers — they POST a `RequestMessage` (workflow code + a keyed map of message bodies) to a workflow endpoint, and `WorkflowService.execute` runs it:
1. Look up the `Workflow` by code, authenticate if `useAuthValidation`, then authorize against `WorkflowAuthority` (a special `VALID_PASS` authority code means "any authenticated user").
2. Load its ordered `WorkflowFunction` steps. Each step names a `FunctionType` (`ENTITY`, `SQL`, `REST_CLIENT`, `MESSAGE_PROCESSOR`, `NOTIFICATION`) and reads/writes a slot in a shared `Map<String, List<Map<String,Object>>>` message store keyed by `requestMessageId`/`responseMessageId` — this is how steps pass data to each other.
3. `FunctionFactory.instance(FunctionType)` resolves the step to a `FunctionExecutor` implementation (`function/executor/*`); each executor's `execute(user, functionName, params)` does the real work and returns a `FunctionResult` (success/failure).
4. If every step succeeds, the accumulated response slots become the workflow's response body; if some (but not all) steps fail, a partial-failure error is returned with everything gathered so far.

When adding a new kind of workflow step: add a `FunctionType`, an executor implementing `FunctionExecutor`, and wire it into `FunctionFactory`.

### Function executors and the lower-level `executor/` package

`function/executor/*` are the workflow-facing adapters; they delegate to lower-level, reusable engines in `executor/`:
- `executor/sql` — parameterized SQL execution (`SqlExecutor`, `SqlQuery`) with pluggable parameter binding (`parameterbind/`: index-based vs named-based binding).
- `executor/rest` — outbound HTTP calls to externally-registered REST servers (`RestExecutor`).
- `executor/script` — sandboxed JS execution via GraalVM Polyglot (`ScriptEngine`): params are JSON-marshalled in, the user script is wrapped in an IIFE, run with `allowAllAccess(false)`, and the result is walked back into plain Java `List`/`Map`/primitive values. Used for the `MESSAGE_PROCESSOR` function type's custom transform scripts.
- `executor/notification` — outbound notification senders (SMTP today: `SmtpProvider`, `NotificationSender`), pluggable by `NotificationType`.

Entity CRUD (the `ENTITY` function type) doesn't go through `executor/sql` directly — it goes through `entity/query/EntityQueryGeneratorFactory`, which builds the appropriate `InsertQuery`/`SelectQuery`/`UpdateQuery`/`DeleteQuery` from an `EntityConfig` + `EntityColumn` definitions based on `EntityStatus` (C/R/U/D), driven off the console-defined entity metadata rather than JPA entities.

### Dynamic data sources

Because generated admin pages manage *other* databases and *other* REST servers (not just the app's own H2 store), `datasource/database` and `datasource/restserver` hold connection registries: `DatabaseFactory` builds a `Database` strategy per `DatabaseKind` (MySQL/MSSQL/Oracle/MariaDB — each with its own SQL dialect quirks), and `DataSourceDatabaseRegister`/`DataSourceRestServerRegister` register/validate connections defined through the console (`console/rest/DatabaseRestController`, `RestServerRestController`) so `executor/sql` and `executor/rest` can use them by a lookup key at runtime.

### Security

JWT-based auth, not Spring Security's filter chain — `security/filter/ConsoleSecurityFilter` is a plain `Filter` registered manually via `FilterConfig` (not `SecurityFilterChain`). `AuthService`/`LoginService` validate credentials and issue tokens (`JwtProvider`, `TokenCookie` — access token in an HTTP cookie). Authorization is role/authority-code based (`GrantedAuthority`, `Authority`, `UserGroupAuthority`) and is checked independently at three levels: console access (`ConsoleSecurityFilter`), per-view-object (`ViewObjectService.validateAuthorization`), and per-workflow (`WorkflowService.validateAuthorization`) — all following the same "does the user hold one of the authority codes assigned to this resource" pattern, with a `VALID_PASS` sentinel meaning "any authenticated user, no specific authority needed." Generic low-level security primitives (password hashing, business exception base class) live in the separate `com.datajoy.core` package (`core/crypto`, `core/exception`, `core/util`), not `com.datajoy.admin_builder.security`.

### Frontend

Server-rendered Thymeleaf + AdminLTE, no separate frontend build/bundler. Shared client-side helpers live in `src/main/resources/static/core/` (`HttpClient.js` for calling workflow endpoints, `Grid.js`/`GridLegacy.js` wrapping ag-Grid for data tables, `Popup.js`, `Util.js`) and are reused across both the console screens and generated `VIEW_BUILDER`/`FILE` pages.