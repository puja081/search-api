# Interview setup — Spring Boot + MySQL + CRUD

## Prerequisites

1. **Java 17**
2. **MySQL** running locally (`brew services start mysql` on Mac)
3. Update `src/main/resources/application.properties` with your MySQL username/password

## Run the app

```bash
./gradlew bootRun
```

Server: **http://localhost:8080**

Hibernate creates the `documents` table automatically (`ddl-auto=update`).

## CRUD API

| Method | URL | Status |
|--------|-----|--------|
| POST | `/api/v1/documents` | 201 Created |
| GET | `/api/v1/documents/{id}` | 200 / 404 |
| GET | `/api/v1/documents` | 200 |
| PUT | `/api/v1/documents/{id}` | 200 / 404 |
| DELETE | `/api/v1/documents/{id}` | 204 No Content |

## Sample requests (curl)

**Create**
```bash
curl -X POST http://localhost:8080/api/v1/documents \
  -H "Content-Type: application/json" \
  -d '{"title":"Hello","content":"World"}'
```

**Get by id**
```bash
curl http://localhost:8080/api/v1/documents/1
```

**List all**
```bash
curl http://localhost:8080/api/v1/documents
```

**Update**
```bash
curl -X PUT http://localhost:8080/api/v1/documents/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated","content":"New content"}'
```

**Delete**
```bash
curl -X DELETE http://localhost:8080/api/v1/documents/1
```

## Project layout (for live coding)

```
src/main/java/search_api/
├── SearchApiApplication.java      # main
├── controller/DocumentController.java
├── service/DocumentService.java
├── repository/DocumentRepository.java
├── model/Document.java
├── dto/DocumentRequest.java, DocumentResponse.java
└── exception/                     # 404 + validation errors
```
