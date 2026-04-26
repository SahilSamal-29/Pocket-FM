# 🎧 Pocket FM Clone – Audio Streaming App

A modern Android application inspired by Pocket FM, designed for seamless audio browsing and high-quality streaming. Built using **Kotlin**, **MVVM Architecture**, and **ExoPlayer**, this app emphasizes clean code, modular design, and a Netflix-style user experience.

---

## 📽 Live Demo

[**Watch the Demo Video**](https://drive.google.com/file/d/1Zz24UNFGNYLREm_IJS3P3fQa4skb0vL4/view?usp=drivesdk)

---

## 🚀 Features

### **Core Functionality**
* **Seamless Navigation:** Intuitive Home → Detail → Audio Player flow.
* **Audio Player Pro:**
    * Real-time seek bar progress.
    * Forward/Backward navigation (10-second intervals).
    * Playback speed control for personalized listening.
* **Library Management:** "Add to Library" feature to save favorite tracks.

### **User Experience & UI**
* **Modern UI:** Netflix-inspired horizontal scrolling for content discovery.
* **Dynamic Profiles:** Displays user initials (e.g., "SS" for Sahil Samal) and account details.
* **Modular Components:** Reusable UI components for scalability.

### **Security & Authentication**
* **Firebase Auth:** Secure login via Google Sign-In and Phone OTP.

---

## 🛠 Tech Stack

| Category | Technology |
| :--- | :--- |
| **Language** | Kotlin |
| **UI** | XML (Material Design) |
| **Architecture** | MVVM (Model-View-ViewModel) |
| **Networking** | Retrofit |
| **Streaming** | ExoPlayer |
| **Backend/Auth** | Firebase (Authentication) |
| **Concurrency** | Coroutines & LiveData |

---

## 📂 Project Structure

The project follows clean architecture principles to ensure separation of concerns:

```text
com.example.pocketfm
├── data
│   ├── model       # Data classes (POJOs)
│   ├── mapper      # Data transformation logic
│   ├── remote      # API interfaces & network clients
│   └── repository  # Data source orchestration
├── ui
│   ├── home        # Home screen components
│   ├── detail      # Track detail screen
│   ├── player      # Audio player logic & UI
│   ├── auth        # Authentication (Google/OTP)
│   └── profile     # User settings & profile
└── viewmodel       # Shared and specific ViewModels
