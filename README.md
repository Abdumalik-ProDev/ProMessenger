**ProMessenger** by Abdumalik-ProDev aka **ProDev**.
**📨 ProMessenger** — Real-Time Messaging Platform
ProMessenger is a scalable, real-time messaging platform for both private and group communication. Built with modern microservice architecture, it leverages Spring Boot, Spring Security, Spring Cloud, WebSocket, RESTful APIs, MongoDB, Redis, PostgreSQL, Grafana (Loki), Kafka, Docker, and OAuth 2.0 security to deliver a seamless and secure chat experience.

⚙️ Tech Stack
Layer	Technology
🧠 Backend:	Java 21, Spring Boot, Spring Security, OAuth2, Spring Cloud
💬 Messaging:	WebSocket, STOMP, Kafka
🛢️ Databases:	PostgreSQL (SQL), MongoDB (NoSQL), Redis(Caches)
🐳 DevOps:	Docker, Docker Compose, Jib
🔐 Security:	JWT, OAuth2, IAM, RBAC
🧪 Docs & Tools:	Swagger/OpenAPI, Lombok, MapStruct

🚀 Features
✅ Real-time Chat (Private & Group)
✅ WebSocket Integration with fallback handling
✅ JWT & OAuth2 Authorization
✅ MongoDB for message history
✅ PostgreSQL for user auth & metadata
✅ Role-based access for Admin/User/Moderator
✅ Kafka support for future scalability
✅ Full Dockerized Setup for production-readiness
✅ API Gateway & Service Discovery Ready

🛠️ Installation

# 1. Clone the project
git clone https://github.com/ProDev/ProMessenger.git
cd ProMessenger

# 2. Start the platform using Docker
docker-compose up --build
Alternatively, if you prefer to run it manually:

# PostgreSQL
docker run -d -p 5432:5432 --name postgres -e POSTGRES_PASSWORD=postgres postgres

# MongoDB
docker run -d -p 27017:27017 --name mongo mongo

# Then start the Spring Boot services from your IDE
🔐 Authentication
OAuth2 Login via GitHub, Google, etc.

JWT token issued post-OAuth login

IAM support for role-based restrictions

📬 Messaging Flow
Client connects via WebSocket to /ws

Authenticated via JWT

Sends message to /app/chat.sendMessage

Backend relays it to appropriate topic (/topic/groupId)

Kafka is used for async message persistence or queueing

MongoDB stores chat history, searchable via REST


👨‍💻 Author
Abdumalik-ProDev aka ProDev
🔗 GitHub • LinkedIn • Telegram • Instagram

🤝 Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you’d like to change.

# PRs must pass all tests and code quality checks
📜 License
This project is licensed under the MIT License — feel free to fork, clone, and innovate.
