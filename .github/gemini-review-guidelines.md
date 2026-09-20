# Review guidelines

Guidance for the automated reviewer in
[`pr-review.yml`](workflows/pr-review.yml). This is a Spring Boot / Maven
service built with Java 11.

Review only the lines the pull request changes. Do not comment on
pre-existing code unless the change makes an existing problem materially
worse.

## Severity

The workflow gates on this, so apply it strictly.

**Critical** — one of the following, and nothing else:

- A security hole (see below).
- Data loss or corruption: a destructive query without a guard, a lost
  update from a missing transaction boundary, a migration that drops a
  column still in use.
- A break in existing behaviour: a changed API contract, a removed or
  renamed endpoint or response field, an exception path that now escapes
  where it previously did not.

**Major** — a real bug that will surface under some realistic input, but
is contained: a null dereference on an uncommon branch, an N+1 query, a
swallowed exception.

**Minor** — worth fixing, no urgency.

**Nit** — style, naming, formatting, preference.

Style, naming, and preference-level suggestions are **never** critical.
When you are unsure whether something is critical or major, it is major.

## What to look for

### Correctness

- Null handling on values that can be absent — `Optional.get()` without a
  presence check, unchecked results from repository lookups.
- Boundary conditions: off-by-one on ranges and pagination, empty
  collections, the first and last element.
- Swallowed exceptions — an empty `catch`, or one that only logs where
  the caller needed to know the operation failed.
- Thread safety: mutable state held on a singleton bean. Spring beans are
  singletons by default, so an instance field holding per-request state
  is shared across concurrent requests.

### Security

- SQL or JPQL assembled by string concatenation from anything that
  originates in a request. Parameter binding only.
- Credentials, API keys, or tokens committed in source or in
  `application.yml` / `application.properties`.
- Endpoints added without the authorization check their neighbours have.
- Deserialization of untrusted input into polymorphic types.
- Passwords, tokens, personal data, or full request bodies written to
  logs.

### Spring and Java conventions

- `@Transactional` on the wrong layer, or on a non-public method — Spring's
  proxy cannot intercept it and the annotation silently does nothing.
- Self-invocation: calling an annotated method from another method on the
  same bean bypasses the proxy, so `@Transactional`, `@Cacheable`, and
  `@Async` do not apply.
- N+1 queries from lazy associations touched inside a loop. Look for a
  missing `JOIN FETCH` or `@EntityGraph`.
- Field injection (`@Autowired` on a field) instead of constructor
  injection.
- JPA entities returned directly from a controller instead of a DTO. This
  leaks the persistence model into the API and drags lazy proxies into
  serialization.

### Tests

- New business logic arriving with no test. Say which behaviour is
  untested, not just "add tests".
- Assertions that only establish that nothing threw — no assertion on the
  returned value or on a resulting state change.
- A changed behaviour whose existing test was updated to match the new
  output without the change itself being justified.

## Tone

Be specific and short. Point at the line, say what goes wrong and under
what input, and propose the fix. Skip praise and skip summarizing what
the PR does — the author already knows.

If you find nothing worth raising, say so in one line rather than
manufacturing findings.
