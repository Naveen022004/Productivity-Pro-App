# Productivity Pro App

**Productivity Pro App** is a modern Android application built using **Jetpack Compose** and **Material 3**. It features edge-to-edge UI layouts and interactive time-picker components supporting both 12-hour (AM/PM) and 24-hour formats.

---

## 🚀 Features

* **Jetpack Compose UI**: Uses Modern Declarative UI components with Material 3 styling.
* **Time Selection Components**:
  * **24-Hour Time Picker**: Select and format time in a standard 24-hour layout (`HH:mm`).
  * **12-Hour AM/PM Time Picker**: Select and format time with 12-hour AM/PM support (`hh:mm AM/PM`).
* **Edge-to-Edge Layout**: Fully integrated edge-to-edge design using modern Android activity APIs.

---

## 🛠️ Project Configuration & Tech Stack

* **Language**: Kotlin
* **UI Toolkit**: Jetpack Compose
* **Design System**: Material Design 3
* **Minimum SDK**: 24 (Android 7.0)
* **Compile / Target SDK**: 36 (Android 14+)
* **Java Version Compatibility**: Java 11
* **Java Toolchain Version**: JDK 21
* **Build System**: Gradle with Version Catalogs (`libs.versions.toml`)

---

## 📂 Key File Architecture

```text
app/src/main/java/com/example/productivityproapp/
├── MainActivity.kt               # Entry point with default greeting screen
├── ProjectTimePicker.kt          # 24-Hour time picker component implementation
└── ui/
    └── ProjectTimePicker2.kt     # 12-Hour (AM/PM) time picker implementation

```

---

## 📋 Prerequisites

Before setting up the project, make sure you have:

* **Android Studio**: Ladybug / Iguana or newer recommended.
* **JDK**: JDK 21 configured (managed automatically via the Foojay Toolchain plugin).
* **Android SDK**: SDK Platform 36 installed.

---

## 🔧 Getting Started

### 1. Clone the Repository

```bash
git clone [https://github.com/your-username/Productivity-Pro-App.git](https://github.com/your-username/Productivity-Pro-App.git)
cd Productivity-Pro-App

```

### 2. Open in Android Studio

1. Launch Android Studio.
2. Select **Open** and choose the cloned repository folder.
3. Allow Gradle to synchronize dependencies.

### 3. Build & Run

* Connect a physical Android device or launch an Emulator running Android API 24 or higher.
* Click **Run** (`Shift + F10`) or select target activities (`MainActivity`, `ProjectTimePicker`, or `ProjectTimePicker2`).

---

## 🧪 Testing

The app comes configured with basic testing libraries:

* **Unit Tests**: JUnit
* **Instrumentation / UI Tests**: Espresso, AndroidX JUnit, and Compose UI Test JUnit4

Run unit tests via CLI:

```bash
./gradlew test

```
