<img width="1240" height="3447" alt="Auth" src="https://github.com/user-attachments/assets/6bf0b01a-5a56-48cd-acff-2e731ba5edff" />
# 🤖 LLM RLHF(Reinforcement Learning from Human Feedback) Evaluation Pipeline

A production-grade **Java Spring Boot** backend platform that replicates real-world **LLM training workflows** — implementing RLHF annotation, pairwise response ranking, reward score modeling, and SFT dataset construction.

> Built to mirror the exact workflows used by AI labs like Anthropic, Google DeepMind, and OpenAI to train and align large language models.

---

## 📸 Screenshots

### Swagger UI — API Overview
![Swagger UI Overview](https://github.com/user-attachments/assets/8bc1f76a-d929-4b03-a247-efbcc5582944)

### Register & Login
![Auth APIs](https://github.com/user-attachments/assets/6bf0b01a-5a56-48cd-acff-2e731ba5edff)

### 5-Axis Evaluation
![Evaluation API](screenshots/evaluation.png)

### Pairwise Ranking & Leaderboard
![Ranking Leaderboard](screenshots/leaderboard.png)

---

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                   Client (Swagger UI / App)                  │
└─────────────────────────┬───────────────────────────────────┘
                          │ HTTP REST
┌─────────────────────────▼───────────────────────────────────┐
│              Spring Boot REST API (Port 8080)                │
│  ┌─────────────┐  ┌──────────────┐  ┌────────────────────┐  │
│  │  Auth Layer │  │  JWT Filter  │  │  Exception Handler │  │
│  └─────────────┘  └──────────────┘  └────────────────────┘  │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │                    Controllers                          │ │
│  │  Auth | Prompt | Evaluation | Ranking | SFT Dataset    │ │
│  └─────────────────────────────────────────────────────────┘ │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │                     Services                            │ │
│  │  AuthService | EvaluationService | RankingService | ... │ │
│  └─────────────────────────────────────────────────────────┘ │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │                   Repositories (JPA)                    │ │
│  └─────────────────────────────────────────────────────────┘ │
└─────────────────────────┬───────────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────────┐
│                     MySQL Database                           │
│  users | prompts | llm_responses | evaluations |            │
│  pairwise_rankings | sft_datasets                           │
└─────────────────────────────────────────────────────────────┘
```

---

## ✨ Features

### 🔐 Authentication & Authorization
- JWT-based stateless authentication
- 3 roles: **ADMIN**, **ANNOTATOR**, **REVIEWER**
- BCrypt password hashing
- Role-based endpoint protection

### 📝 Prompt Management
- Create prompts across domains: `education`, `coding`, `science`, `entertainment`, `qa`
- Filter prompts by domain and difficulty
- Attach multiple AI model responses per prompt

### ⭐ 5-Axis Evaluation Engine
Rate any AI response across 5 quality dimensions (score 1–5):

| Axis | What it measures |
|---|---|
| **Relevance** | Did the response answer the question? |
| **Accuracy** | Is the information factually correct? |
| **Clarity** | Is it easy to understand? |
| **Safety** | Is it free from harmful content? |
| **Helpfulness** | Did it actually help the user? |

- Auto-computes **overall score** as average of 5 axes
- Requires written **rationale** explaining the scoring
- Updates response reward score after each evaluation

### 🏆 Pairwise Ranking (RLHF Core)
- Compare two AI responses side-by-side for the same prompt
- Annotator picks: **Response A**, **Response B**, or **Tie**
- Requires written reasoning for the preference
- Auto-updates **reward scores** based on wins
- **Leaderboard** shows all responses ranked by win rate

### 📦 SFT Dataset Builder
- Annotators write **gold-standard responses** to prompts
- Reviewer approval workflow (DRAFT → APPROVED → EXPORTED)
- Export approved entries as **JSONL** — ready for Hugging Face fine-tuning

```json
{"prompt": "What is ML?", "response": "ML is...", "domain": "education"}
{"prompt": "Explain recursion", "response": "Recursion is...", "domain": "coding"}
```

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| **Java** | 21 (LTS) | Core language |
| **Spring Boot** | 3.2.5 | REST API framework |
| **Spring Security** | 6.x | Authentication & authorization |
| **Spring Data JPA** | 3.x | Database ORM |
| **MySQL** | 8.0 | Persistent storage |
| **JWT (jjwt)** | 0.12.3 | Token-based auth |
| **Swagger/OpenAPI** | 2.3.0 | API documentation |
| **Maven** | 3.8+ | Build tool |

---

## ⚙️ Prerequisites

- Java 21+
- Maven 3.8+
- MySQL 8.0+

---

## 🚀 Getting Started

### Step 1 — Clone the repository
```bash
git clone https://github.com/meJoydev/llm-rlhf-evaluation-pipeline.git
cd llm-rlhf-evaluation-pipeline
```

### Step 2 — Create MySQL database
```sql
CREATE DATABASE rlhf_db;
```

### Step 3 — Configure database password
Open `src/main/resources/application.properties`:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Step 4 — Run the application
```bash
mvn spring-boot:run
```

### Step 5 — Open Swagger UI
```
http://localhost:8080/swagger-ui.html
```

---

## 📋 API Reference

### Authentication
| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/api/auth/register` | Register new user | ❌ |
| POST | `/api/auth/login` | Login & get JWT token | ❌ |

### Prompts & Responses
| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/api/prompts` | Create a prompt | ✅ |
| GET | `/api/prompts` | Get all prompts | ✅ |
| GET | `/api/prompts/domain/{domain}` | Filter by domain | ✅ |
| POST | `/api/prompts/responses` | Add AI response | ✅ |
| GET | `/api/prompts/{id}/responses` | Get responses | ✅ |

### Evaluation
| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/api/evaluations` | Submit 5-axis evaluation | ✅ |
| GET | `/api/evaluations/response/{id}` | Get evaluations for response | ✅ |
| GET | `/api/evaluations/my` | Get my evaluations | ✅ |

### Pairwise Ranking
| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/api/rankings` | Submit pairwise comparison | ✅ |
| GET | `/api/rankings/leaderboard` | Reward score leaderboard | ✅ |

### SFT Dataset
| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/api/sft` | Submit gold response | ✅ |
| GET | `/api/sft/my` | My submissions | ✅ |
| GET | `/api/sft/review/pending` | Pending reviews | 🔒 REVIEWER |
| PUT | `/api/sft/review/{id}` | Approve/reject entry | 🔒 REVIEWER |
| GET | `/api/sft/export` | Export as JSONL | 🔒 REVIEWER |

---

## 🔄 Complete Usage Flow

```
1. Register user (ANNOTATOR role)
         ↓
2. Login → get JWT token
         ↓
3. Create a prompt (e.g. "What is photosynthesis?")
         ↓
4. Add AI responses (gpt-4, claude-3, gemini-pro)
         ↓
5. Evaluate each response on 5 axes + write rationale
         ↓
6. Submit pairwise comparisons (A vs B)
         ↓
7. View leaderboard → reward scores updated
         ↓
8. Submit gold-standard response for SFT
         ↓
9. Reviewer approves → export as JSONL
         ↓
10. JSONL file used for LLM fine-tuning 🚀
```

---

## 📁 Project Structure

```
src/main/java/com/joydev/rlhf/
├── RlhfApplication.java
├── config/
│   ├── SecurityConfig.java
│   └── SwaggerConfig.java
├── controller/
│   ├── AuthController.java
│   ├── EvaluationController.java
│   ├── PairwiseRankingController.java
│   ├── PromptController.java
│   └── SftDatasetController.java
├── dto/
│   ├── request/
│   └── response/
├── enums/
│   ├── DatasetStatus.java
│   ├── EvaluationStatus.java
│   ├── PreferredResponse.java
│   └── Role.java
├── exception/
│   └── GlobalExceptionHandler.java
├── model/
│   ├── Evaluation.java
│   ├── LlmResponse.java
│   ├── PairwiseRanking.java
│   ├── Prompt.java
│   ├── SftDataset.java
│   └── User.java
├── repository/
├── security/
│   ├── JwtAuthFilter.java
│   └── JwtUtils.java
└── serviceimpl/
```

---

## 👨‍💻 Author

**Joydev Chand**
- GitHub: [@meJoydev](https://github.com/meJoydev)
- LinkedIn: [joydevchand](https://www.linkedin.com/in/mejoydevchand/)
- Email: joy682dev@gmail.com

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
