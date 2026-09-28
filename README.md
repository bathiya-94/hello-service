# Hello World REST API

A Spring Boot REST API that validates a name and returns a personalized greeting.

The API accepts a single-word English name beginning with a letter from **A–M** and returns the name in title case.

---

## API

### `GET /hello-world`

Returns a greeting for a valid name.

#### Query Parameter

| Parameter | Required | Rules                                                                 |
| --------- | -------- | --------------------------------------------------------------------- |
| `name`    | Yes      | Single word, English letters only (`A-Z`, `a-z`), starting with `A–M` |

### Successful Request

```http
GET /hello-world?name=alice
```

**Response — `200 OK`**

```json
{
  "message": "Hello Alice"
}
```

The API normalizes the name to title case:

```text
aLICE → Alice
```

### Invalid Requests

The API returns `400 Bad Request` for invalid input.

Examples:

```text
GET /hello-world
GET /hello-world?name=nancy
GET /hello-world?name=alice%20bob
GET /hello-world?name=alice123
GET /hello-world?name=alice-bob
```

**Response:**

```json
{
  "error": "Invalid Input"
}
```

---

## Validation

Validation is implemented as an ordered pipeline using the `NameValidator` interface.

```text
Request
   │
   ▼
NotBlankValidator
   │
   ▼
NameFormatValidator
   │
   ▼
FirstNameValidator
   │
   ▼
HelloService
   │
   ▼
"Hello Alice"
```

### Validators

| Order | Validator             | Responsibility                                   |
| ----- | --------------------- | ------------------------------------------------ |
| 1     | `NotBlankValidator`   | Rejects null, empty, or whitespace input         |
| 2     | `NameFormatValidator` | Allows only single-word English alphabetic input |
| 3     | `FirstNameValidator`  | Accepts names beginning with `A–M`               |

This design keeps each validation rule independent and makes the validation pipeline easy to extend.

---

## Project Structure

```text
hello-service/
├── Dockerfile
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   └── java/com/hello/helloservice/
    │       ├── controller/
    │       ├── dto/
    │       ├── exception/
    │       ├── service/
    │       └── validator/
    └── test/
        └── java/com/hello/helloservice/
            ├── controller/
            ├── service/
            └── validator/
```

---

## Technology Stack

* **Java 21**
* **Spring Boot**
* **Spring Web MVC**
* **Maven**
* **JUnit 5**
* **Mockito**
* **MockMvc**
* **Docker**

---

## Getting Started

### Prerequisites

* JDK 21
* Maven 3.9+
* Docker (optional)

Verify your Java and Maven installations:

```bash
java -version
mvn -version
```

### Run Tests

```bash
mvn clean test
```

### Run the Application

```bash
mvn spring-boot:run
```

The application starts on:

```text
http://localhost:8080
```

### Test the API

```bash
curl -i "http://localhost:8080/hello-world?name=alice"
```

Expected response:

```json
{
  "message": "Hello Alice"
}
```

---

## Docker

### Build the Image

```bash
docker build -t hello-service .
```

### Run the Container

```bash
docker run -d \
  --name hello-service \
  -p 8080:8080 \
  hello-service
```

### Test the Container

```bash
curl -i "http://localhost:8080/hello-world?name=alice"
```

### Stop the Container

```bash
docker stop hello-service
docker rm hello-service
```

---

## Testing

The project contains tests at multiple levels:

* **Validator unit tests** — individual validation rules
* **Service unit tests** — validation pipeline and greeting logic
* **Controller tests** — HTTP request/response behavior
* **Integration tests** — complete application context

Run the complete test suite with:

```bash
mvn clean test
```

---

## License

This project is provided for demonstration and development purposes.
