Here is a professional, portfolio-ready README.md that reflects your current progress (Vision API + Gemini Integration + DB Caching + TTS).

You can copy and paste this directly into your project's root README.md file.

Markdown

# 🍎 What's in My Refrigerator? - AI Language Learning App

> **A context-aware language learning application that turns everyday objects into micro-learning opportunities.**

This project leverages **Computer Vision** to see what's in your fridge and **Generative AI (LLM)** to teach you relevant vocabulary and sentences in real-time. It features an **On-demand Caching System** to optimize performance and reduce API costs.

---

## 🏗️ System Architecture

The core strength of this project is its **Hybrid AI Architecture** combined with a caching strategy.

```mermaid
graph LR
    User[Mobile App] -- 1. Photo & Target Lang --> Server[Spring Boot Backend]
    Server -- 2. Image Analysis --> Vision[Google Cloud Vision API]
    Vision -- 3. Object Labels --> Server
    Server -- 4. Check Cache --> DB[(MySQL Database)]
    
    subgraph "Cache Miss (New Item)"
    DB -. Not Found .-> Server
    Server -- 5. Prompt Engineering --> Gemini[Google Gemini LLM]
    Gemini -- 6. Translation & Example Sentence --> Server
    Server -- 7. Save Data --> DB
    end
    
    subgraph "Cache Hit (Existing Item)"
    DB -- Found Data (0.01s) --> Server
    end
    
    Server -- 8. JSON Response --> User
Key Technical Features
Visual Recognition: Uses Google Cloud Vision API to detect objects from raw camera images.

Generative AI Integration: Uses Google Gemini 1.5 Flash to dynamically generate translations and context-appropriate example sentences (A1 level) for any detected object.

On-demand Caching Strategy: Implements a "Check-DB-First" logic.

Cache Miss: Calls the expensive LLM API only for new words, then saves the result.

Cache Hit: Returns data immediately from MySQL for previously scanned items, ensuring low latency and cost efficiency.

Text-to-Speech (TTS): Integrated audio playback for pronunciation practice.

Smart Filtering: Post-processing logic to filter out irrelevant labels (e.g., "Food", "Produce") and focus on specific ingredients.

🛠️ Technology Stack
Frontend (Mobile)
Framework: React Native (Expo)

Language: TypeScript / JavaScript

Network: Axios

Features: Expo Camera, Expo Speech (TTS)

Backend (Server)
Framework: Spring Boot 3.2.0

Language: Java 17

Database: MySQL (JPA/Hibernate)

Build Tool: Gradle

AI Clients: Google Cloud Vision SDK, Spring REST Client (for Gemini)

AI & Cloud Services
Google Cloud Vision API: Object detection.

Google Gemini API: LLM for translation and natural language generation.

🚀 Getting Started
1. Prerequisites
Node.js & npm

Java JDK 17

MySQL Server

Google Cloud Platform Account (with Vision API enabled)

Google AI Studio API Key (for Gemini)

2. Database Setup
Create a local MySQL database:

SQL

CREATE DATABASE my_fridge_db;
3. Backend Setup
Navigate to the backend directory:

Bash

cd backend
Add your GCP Service Account Key (gcp-key.json) to src/main/resources/.

Configure src/main/resources/application.properties:

Properties

# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/my_fridge_db
spring.datasource.username=root
spring.datasource.password=YOUR_DB_PASSWORD

# Google Cloud Vision
spring.cloud.gcp.credentials.location=classpath:gcp-key.json

# Google Gemini
gemini.api.key=YOUR_GEMINI_API_KEY
gemini.api.url=[https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent](https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent)
Run the server:

Bash

./gradlew bootRun
4. Frontend Setup
Navigate to the frontend directory:

Bash

cd frontend
Install dependencies:

Bash

npm install
Update API URL in app/(tabs)/index.tsx:

JavaScript

// Replace with your computer's local IP address
const BACKEND_URL = 'http://YOUR_LOCAL_IP:8080/api/quiz/generate';
Run the app:

Bash

npx expo start
📱 Usage
Select Language: Choose your target language (Spanish, French, German, Korean, etc.).

Snap: Take a photo of an ingredient in your fridge (e.g., an apple, milk carton).

Learn: The app displays the detected word, its translation, and a generated example sentence.

Listen: Tap the speaker icon to hear the pronunciation.

🔮 Future Improvements
Semantic Filtering: Replacing hardcoded blocklists with AI-based filtering for better accuracy.

Context-Awareness: Using previous scan history to generate sentences that combine multiple ingredients (e.g., "I eat eggs with milk").

User Accounts: Saving personal vocabulary lists.

📄 License
This project is licensed under the MIT License.
