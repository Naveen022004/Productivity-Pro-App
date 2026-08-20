# Productivity Pro App

A clean Android productivity starter app built with Kotlin, Jetpack Compose, and Material 3.

## Features

- Modern Jetpack Compose UI
- Material 3 components
- Edge-to-edge Android UI
- 24-hour time picker (`HH:mm`)
- 12-hour time picker with AM/PM
- Android API 24+ support

## Tech Stack

- Kotlin 2.0.21
- Jetpack Compose
- Material 3
- Android Gradle Plugin 8.9.2
- Gradle 8.11.1
- Java 17
- Compile/Target SDK 36
- Minimum SDK 24

## Project Structure

```text
Productivity-Pro-App/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml
│       └── java/com/example/productivityproapp/
│           ├── MainActivity.kt
│           ├── ProjectTimePicker.kt
│           └── ui/
│               ├── ProjectTimePicker2.kt
│               └── theme/Theme.kt
├── gradle/libs.versions.toml
├── build.gradle.kts
└── setting.gradle.kts
```

## Open in Android Studio

1. Clone the repository.
2. Open the repository root in Android Studio.
3. Allow Gradle to sync.
4. Install Android SDK 36 if prompted.
5. Run the `app` configuration on an emulator or Android device.

## Build from the command line

The repository includes a GitHub Actions build that validates the project with Gradle 8.11.1 and JDK 17.

If Gradle is installed locally:

```bash
gradle :app:assembleDebug
```

The generated APK is located at:

```text
app/build/outputs/apk/debug/app-debug.apk
```
