# Law Enforcement App (UPS)

A modern Android application designed for law enforcement personnel in North Macedonia to access laws, track overtime, calculate bonuses, and manage reference information.

## Features

- **Laws & Legal Documents** - Browse and archive legal documents (PDF-based reference library)
- **Overtime Tracking** - Track daily overtime and work hours with detailed statistics
- **Bonus Salary Calculator** - Automatic bonus calculations based on overtime thresholds and parameters
- **Crime Reference** - Quick access to offense and crime classifications
- **Police Authorities** - Directory of police contacts and organizational information
- **Push Notifications** - Real-time updates through Firebase Cloud Messaging
- **Remote Configuration** - Dynamic feature flags and settings management
- **Offline Support** - Works without internet connection with local database caching

## Tech Stack

- **Language:** Kotlin 2.1.0
- **UI Framework:** Jetpack Compose with Material Design 3
- **Architecture:** MVVM + Clean Architecture
- **Dependency Injection:** Koin 3.5.6
- **Database:** Room 2.6.1 (SQLite)
- **Backend:** Firebase (Firestore, Cloud Messaging, Remote Config)
- **Async:** Kotlin Coroutines 1.10.1
- **Testing:** JUnit 4, MockK, Kotlin Coroutines Test

## Requirements

- Android 10+ (API 29)
- Kotlin 2.1.0+
- Java 11+

## Building & Running

### Prerequisites
1. Clone the repository
2. Create `google-services.json` from your Firebase project and place it in the `app/` directory
3. Open in Android Studio or build from command line

### Build Commands

```bash
# Debug build
./gradlew assembleDebug

# Release build (with ProGuard obfuscation)
./gradlew assembleRelease

# Install and run on device
./gradlew installDebug

# Run all tests
./gradlew test

# Run specific test
./gradlew test --tests com.boryanz.upszakoni.bonussalary.BonusSalaryViewModelTest
```

## Project Structure

```
app/src/main/java/com/boryanz/upszakoni/
├── data/               # Database, repositories, data sources
│   ├── local/          # Room database, SharedPreferences
│   └── model/          # Data models
├── domain/             # Business logic, repository interfaces
├── ui/                 # Jetpack Compose screens and components
│   ├── screens/        # Feature screens (laws, bonussalary, etc.)
│   ├── navigation/     # Type-safe navigation
│   ├── components/     # Reusable UI components
│   └── theme/          # Material Design 3 theme
├── di/                 # Dependency injection (Koin)
└── utils/              # Utility functions
```

## Key Components

### Database
- **BonusSalaryTreshold** - User salary parameters
- **MonthlyStats** - Monthly overtime and bonus aggregations
- **DayInMonth** - Daily overtime entries

### Firebase Integration
- Push notifications for app updates
- Remote configuration for feature management
- Cloud database for data synchronization

### Testing
The project includes comprehensive unit tests:
- ViewModel tests with isolated coroutine dispatchers
- Repository and use case tests with fake implementations
- Test coverage for bonus salary calculations, laws management, and overtime tracking

Run tests: `./gradlew test`

## Architecture

The app follows **MVVM + Clean Architecture** with three layers:

1. **UI Layer** - Composable screens, ViewModels, state management
2. **Domain Layer** - Business logic, repository interfaces, use cases
3. **Data Layer** - Local database (Room), remote sources (Firebase), data models

All data operations are asynchronous using Kotlin Coroutines and exposed through `StateFlow` for reactive UI updates.

## Configuration

### Android Manifest
- Min SDK: 29 (Android 10)
- Target SDK: 35 (Android 14)
- Compile SDK: 36
- Portrait orientation primary

### ProGuard
Release builds use ProGuard obfuscation for code protection. Rules defined in `app/proguard-rules.pro`.

### Dependencies
All dependencies managed in `gradle/libs.versions.toml` for centralized version management.

## License

Copyright 2022 Boris Janevski

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
