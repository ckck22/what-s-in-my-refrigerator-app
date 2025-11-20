# 🍎 What's in My Refrigerator? - AI Language Learning App

> **A context-aware language learning application that turns everyday objects into micro-learning opportunities.**

This project transforms your refrigerator inventory into personalized language learning content. By leveraging **Computer Vision** to identify objects and **Generative AI (LLM)** to create context-specific sentences, it bridges the gap between the physical world and language education.

It features an **On-demand Caching System** to optimize performance, reduce latency, and minimize API costs.

---

## 🏗️ System Architecture

The core strength of this project is its **Hybrid AI Architecture** combined with a smart caching strategy.

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
