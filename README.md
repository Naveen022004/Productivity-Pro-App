# Productivity Pro

A production-oriented Android productivity app built with Kotlin, Jetpack Compose, Material 3, Room, and Android notifications.

## Current features

- Create tasks with title and optional notes
- Mark tasks complete/incomplete
- Delete tasks
- Persist tasks locally with Room
- Schedule task reminders
- Restore scheduled reminders after device reboot
- Android 24+ support
- Material 3 UI with system light/dark theme support
- Notification permission handling for Android 13+

## Tech stack

- Kotlin 2.0.21
- Jetpack Compose + Material 3
- Android Gradle Plugin 8.9.2
- Gradle 8.11.1 (CI)
- Java 17
- Compile SDK 36
- Target SDK 36
- Minimum SDK 24
- Room 2.7.0
- Lifecycle ViewModel + StateFlow

## Project structure

```text
Productivity-Pro-App/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/naveen/productivitypro/
│       │   ├── MainActivity.kt
│       │   ├── data/
│       │   ├── notifications/
│       │   ├── ui/theme/
│       │   └── viewmodel/
│       └── res/
│           ├── drawable/ic_app_icon.xml
│           └── values/styles.xml
├── .github/workflows/android-build.yml
├── gradle/libs.versions.toml
├── build.gradle.kts
└── settings.gradle.kts
```

## Build locally

Open the repository root in Android Studio with Android SDK 36 installed. Sync Gradle and run the `app` configuration on an emulator or physical Android device.

The GitHub Actions workflow installs Gradle 8.11.1 and validates unit tests, lint, debug APK generation, and release App Bundle generation.

## Release checklist

1. Set the final Play Store application ID if you need a different permanent ID.
2. Create and securely store a release keystore outside Git.
3. Configure release signing through local/CI secrets; never commit the keystore or passwords.
4. Run `gradle :app:bundleRelease` locally with the signing configuration.
5. Test the signed AAB on a physical device through Play Console internal/closed testing.
6. Prepare Play Console privacy policy, Data Safety, content rating, target audience, screenshots, feature graphic, and store descriptions.

## Privacy

The current application is designed for local task storage and does not require an account or backend. Notifications are generated on-device. If future versions add analytics, advertising, cloud sync, or accounts, update the privacy policy and Data Safety declaration before release.

See `docs/PRIVACY_POLICY.md` for the current policy draft.
