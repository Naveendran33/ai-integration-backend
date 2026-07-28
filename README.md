# AI Integration Backend

A flexible, high-performance RESTful API built with **Spring Boot** for integrating Large Language Models (LLMs) via the **OpenRouter API** (featuring Google's `gemma-4-26b-a4b-it` model).

Developed as part of the CodTECH Internship.  
**Intern ID:** CITS7505

---

## 🚀 Tech Stack

- **Java 21**
- **Spring Boot** (WebMVC, WebFlux WebClient, Validation)
- **OpenRouter API** (OpenAI-compatible AI completion API)
- **PostgreSQL** (Database)
- **Docker & Docker Compose** (Containerized setup)
- **GitHub Actions** (CI/CD Automated Testing)
- **Maven** (Dependency Management)
- **Lombok** (Boilerplate reduction)
- **Swagger / OpenAPI** (Interactive API Documentation)

---

## ✨ Features

- **AI Completion Endpoint**: Exposes a clean REST API `/api/chat/completions` to interact with state-of-the-art AI models.
- **Provider Abstraction Architecture**: Uses an `AiProviderService` interface, enabling effortless swapping between AI providers (Google Gemini, OpenRouter, OpenAI, Anthropic, etc.).
- **Token Metrics**: Returns response text along with detailed token consumption metrics (`promptTokens`, `completionTokens`, `totalTokens`).
- **Containerized Database & Application**: Includes a pre-configured `docker-compose.yaml` to spin up PostgreSQL and the Spring Boot backend seamlessly.
- **Automated Unit Testing**: Includes unit tests built with JUnit 5 to test controller endpoints.
- **Interactive Documentation**: Integrated Swagger UI for testing API endpoints directly in your browser.

---

## 🛠️ Setup & Installation

### Prerequisites
- Java 21 or higher
- Maven 3.8+
- Docker and Docker Compose (recommended)
- An API Key from [OpenRouter](https://openrouter.ai/)

---

### 1. Environment Configuration

Before running the application, set your OpenRouter API Key in `docker-compose.yaml`:

```yaml
    environment:
      DB_USERNAME: postgres
      DB_PASSWORD: password221
      JDBC_URL: jdbc:postgresql://ai-integration-db-postgres:5432/ai_integration_db
      API_KEY: your_actual_openrouter_api_key_here
```

*Note: Never commit your real API key to a public GitHub repository!*

---

### 2. Build & Run with Docker (Recommended)

Run the following command from the root directory:

```bash
docker-compose up --build -d
```

This starts both the PostgreSQL database container and the Spring Boot backend server on port `8070`.

To stop the containers:
```bash
docker-compose down
```

---

### 3. Build & Test Locally with Maven

To compile the application and execute unit tests locally:

```bash
./mvnw clean test
```

To package the application into an executable `.jar`:

```bash
./mvnw clean package
```

---

## 📚 API Documentation (Swagger)

Once the application is running, explore and test the endpoints interactively via Swagger UI:

👉 **[http://localhost:8070/swagger-ui.html](http://localhost:8070/swagger-ui.html)**

### Key Endpoint:

* **Method**: `POST`
* **Path**: `/api/chat/completions`
* **Query Parameter**: `prompt` (String)

#### Example Request:
```bash
curl -X POST "http://localhost:8070/api/chat/completions?prompt=Hello%20AI"
```

#### Example Response:
```json
{
  "content": "Hello! How can I assist you today?",
  "promptTokens": 12,
  "completionTokens": 9,
  "totalTokens": 21
}
```

---

## 🔒 Security & Architecture Notes

* **Interface Decoupling**: The controller depends on `AiProviderService` rather than concrete implementations, allowing painless expansion to other LLM providers without altering web controller logic.
* **Secret Management**: API keys and database credentials are read dynamically from environment variables (`${API_KEY}`, `${JDBC_URL}`) rather than being hardcoded into source files.
* **Non-blocking Reactive HTTP Client**: WebFlux's `WebClient` is used for high-efficiency network calls to the OpenRouter gateway.
