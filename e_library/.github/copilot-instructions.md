**Repository Overview**
- **Type**: Java web application packaged as a `war`.
- **Frameworks**: Spring MVC (REST) + Apache CXF (SOAP).
- **Data source**: Static XML `elibrary.xml` under `src/main/resources` (loaded and cached by `XMLParserService`).

**Big Picture / Architecture**
- **Entry points**: `WebAppInitializer.java` registers two servlets:
  - `DispatcherServlet` mapped to `/*` for REST controllers (see `SpringConfig.java`).
  - `CXFServlet` mapped to `/soap/*` for SOAP services (see `CXFConfig.java`).
- **REST layer**: `ELibraryRESTController` exposes JSON/XML endpoints under `/api/elibrary`.
- **SOAP layer**: `ELibrarySOAPServiceImpl` implements the `ELibrarySOAPService` interface and is published by `CXFConfig` at `/soap/ELibraryService`.
- **Core service**: `XMLParserService` is a singleton Spring `@Service` that loads `elibrary.xml` on startup (JAXB + DOM + XPath) and serves reads/queries in-memory. This means most changes to data are achieved by editing `src/main/resources/elibrary.xml` and restarting.

**Key Files to Reference**
- `src/main/java/e_library/config/WebAppInitializer.java` — servlet wiring and URL mappings.
- `src/main/java/e_library/config/CXFConfig.java` — CXF bus and SOAP endpoint publishing.
- `src/main/java/e_library/config/SpringConfig.java` — component-scan root (`e_library`).
- `src/main/java/e_library/rest/ELibraryRESTController.java` — all REST endpoints and example payloads (see `/xpath`).
- `src/main/java/e_library/service/XMLParserService.java` — XML loading, XPath execution, and business queries.
- `src/main/resources/elibrary.xml` and `elibrary.xsd` — canonical data and schema; `xpath_queries.txt` contains example queries.
- `pom.xml` — build, versions, and the `tomcat7-maven-plugin` configuration (port `8080`, path `/e_library`).

**Build & Run (developer workflows)**
- **Build WAR**: `mvn clean package` (Java 8 required as configured in `pom.xml`).
- **Run with embedded Tomcat (dev)**: `mvn tomcat7:run` — plugin reads config in `pom.xml` and serves at `http://localhost:8080/e_library`.
- **Exploded app / runtime files**: check `target/tomcat` and `target/e_library` for exploded contents and logs.
- **Common debugging checks**:
  - Ensure `elibrary.xml` is present under `target/classes` (classloader reads `/elibrary.xml`).
  - Look at `target/tomcat/logs` for startup exceptions from Spring or CXF.

**URL examples / quick tests**
- REST: `GET http://localhost:8080/e_library/api/elibrary/books`
- REST XPath: `POST http://localhost:8080/e_library/api/elibrary/xpath` with JSON body `{ "query": "<your xpath>" }`
- SOAP WSDL: `http://localhost:8080/e_library/soap/ELibraryService?wsdl`

**Project-specific patterns & conventions**
- **Single source XML**: The system treats `elibrary.xml` as the canonical dataset. `XMLParserService` caches the parsed JAXB model (`ELibrary`) and a DOM `Document` for XPath. Avoid code changes that assume a dynamic data store.
- **Package-root component-scan**: `SpringConfig` uses `@ComponentScan(basePackages = {"e_library"})` — new components must live under `e_library.*` to be auto-detected.
- **SOAP vs REST separation**: REST controllers live under `rest` package and are served by the Spring `DispatcherServlet` mapped to `/*`. CXF SOAP endpoints are published separately under `/soap/*`. When adding a new SOAP service, add a `@Component` implementation with `@WebService` and ensure it is injected in `CXFConfig` (or add new endpoint bean there).
- **XPath support**: The REST controller exposes a free-text XPath executor (`/xpath`). Queries may return node content; `xpath_queries.txt` contains sample queries to reuse.

**How to extend**
- Add REST endpoints: create `@RestController` under `e_library.rest` and map under `/api/elibrary` to follow existing patterns.
- Add SOAP services: define a Java interface (like `ELibrarySOAPService`), implement it as a `@Component` + `@WebService`, then publish via `CXFConfig` (see `endpoint.publish("/ELibraryService")`).

**Pitfalls & gotchas discovered from code**
- `XMLParserService` loads resources with `getResourceAsStream("/elibrary.xml")` — the leading `/` expects the XML on the classpath root. If running from IDE ensure resources are copied to `target/classes`.
- Both REST and CXF share the same Spring context instance. Be careful when adding beans with the same names or conflicting servlet filters.
- Java 8 compatibility: `pom.xml` sets `source`/`target` to `1.8` and uses JAXB runtime (explicit dependencies). Do not assume newer JDK JAXB behavior without adjusting `pom.xml`.

**Developer notes for AI agents**
- Prefer minimal, additive changes (the app is simple and classpath/resource-sensitive).
- When suggesting endpoint paths or resource moves, reference `WebAppInitializer.java` and `CXFConfig.java` to avoid breaking mappings.
- When editing `elibrary.xml`, run `mvn clean package` then `mvn tomcat7:run` to validate runtime effects; point to `target/tomcat/logs` for startup/troubleshooting logs.

If anything here looks incomplete or you'd like more examples (sample XPath, sample curl commands, or a short dev README), tell me which area to expand.
