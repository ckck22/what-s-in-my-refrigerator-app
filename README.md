# What's in my Refrigerator? - AI Vocabulary App

A mobile application designed to make foreign language learning practical and engaging. Users take a photo of the inside of their refrigerator, and the app uses AI to identify the food items present (e.g., "apple," "milk," "cabbage"). Based on these recognized items, the app dynamically generates personalized vocabulary quizzes, allowing users to learn words for the things they *actually* own.

## 🚀 Core Features

* **Scan Your Fridge:** Take a photo of your refrigerator's contents.
* **AI Object Detection:** Automatically identifies food items in the photo.
* **Dynamic Quizzes:** Generates vocabulary quizzes based on the *specific items* you have.
* **Multi-Language Support:** Learn translations for your food in various languages.

## 🛠️ Technology Stack

The project is built with the following technologies:

### 1. Frontend (Mobile Client)
* **React Native**: A cross-platform framework for building the mobile app UI, managing camera access, and handling user interaction.
* **Axios**: Used for making HTTP requests to the backend REST API.

### 2. Backend (Server)
* **Spring Boot (Java)**: Provides a robust REST API for all client-server communication.
* **Spring Data JPA**: Manages database operations and object-relational mapping (ORM).
* **Spring Web**: Handles incoming HTTP requests and image uploads (`MultipartFile`).

### 3. Database
* **MySQL**: A relational database used to store all core application data, including:
    * `Word` table (mapping AI labels to common names).
    * `Translation` table (storing translations for each word).
    * `User` table (for user accounts and progress).

### 4. AI (Image Recognition)
* **Google Cloud Vision API**: A managed cloud service leveraged for its powerful `Label Detection` feature. It analyzes images sent by the Spring Boot server and returns a list of identified objects.

### 5. Communication
* **REST API (JSON)**: Serves as the standard communication protocol between the React Native frontend and the Spring Boot backend.

## ⚙️ Getting Started

*(This section will be updated with instructions on how to build and run the project locally.)*

1.  Clone the repository.
2.  Configure backend (Spring Boot) and database (MySQL).
3.  Configure frontend (React Native) with API keys.
4.  Run the backend server.
5.  Run the React Native app.

## 📄 License

This project is licensed under the **MIT License**. See the `LICENSE` file for details.
