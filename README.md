# Productivity Pro App

A modern Android productivity application built with Kotlin, Jetpack Compose, and Material 3.

## Features

- Productivity dashboard
- Add tasks with title, category, and priority
- Complete and delete tasks
- Instant task search
- Completion statistics
- Material 3 interface
- Quick-add floating action button
- Release build configuration
- GitHub Actions automated APK build

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Android Gradle Plugin
- Gradle

## Run locally

1. Open the repository in Android Studio.
2. Use JDK 17.
3. Install Android SDK 36.
4. Allow Gradle to sync.
5. Select the `app` configuration and run it on an Android device or emulator.

## Build APK

```bash
./gradlew assembleDebug
```

The APK is generated at `app/build/outputs/apk/debug/`.

## GitHub Actions

Pushes to `main` or `improve-productivity-app` automatically build the Android project. The generated debug APK is uploaded as a workflow artifact.

## Minimum Android version

Android 7.0 (API 24) or newer.

## Planned extensions

Persistent Room storage, notifications/reminders, recurring tasks, calendar integration, analytics, authentication, and cloud synchronization can be added on top of the current foundation.
