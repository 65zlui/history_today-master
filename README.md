# History Today App

An Android application that displays traditional Chinese calendar information and historical events on this day.

## Main Features

1. **Lao Huang Li (Traditional Chinese Calendar)**
   - Displays today's traditional Chinese calendar information by default
   - Allows users to select any date to view corresponding auspicious and inauspicious information
   - Shows detailed information including lunar calendar, five elements, clash information, and more
   - Added hour information API support

2. **Today in History**
   - Swipe down on the homepage to view summary information
   - Click the bottom to load all historical events
   - Click on specific items to view detailed event information

## Responsibility Description

Complete development from requirements analysis to testing.

## Technical Description

### 1. Architecture
- Initially developed using traditional Android MVC (Model-View-Controller) pattern
- Upgraded to MVP (Model-View-Presenter) pattern to address tight coupling between View and Controller
- MVP allows View to focus on data visualization and user interaction
- Model only handles data processing
- Clear project structure with high decoupling
- Each feature is independent and can be tested separately

### 2. Network Handling
- Initially used outdated xutils framework for network requests
- Later switched to OkHttp for more efficient network handling
- Encapsulated network request module with an intermediate layer between app and framework
- Facilitates easy replacement of network request modules in the future
- All network requests are now asynchronous to avoid main thread blocking
- Fixed network security policy to support HTTP requests for all API domains

### 3. Structured Design
- Encapsulated BaseActivity base class to extract common methods and operations
- Initially used for network callbacks in MVC mode
- Removed network request methods from BaseActivity after network module encapsulation
- Uses Adapter pattern for ListView data binding
- Uses Singleton pattern for Model classes like HistoryDescModel
- Added necessary comments for better code maintainability

### 4. API Adaptation
- Adapted to the new Today in History API interface
- Updated API URLs in ContentURL class
- Added new API endpoints for hour information
- Optimized error handling for API calls
- Ensured compatibility with new API response formats

## API Information

### APIs Used

1. **Lao Huang Li API**
   - Endpoint: `http://v.juhe.cn/laohuangli/d`
   - Provides traditional Chinese calendar information
   - Requires date parameter in format "yyyy-MM-dd"

2. **Hour Information API**
   - Endpoint: `http://v.juhe.cn/laohuangli/h`
   - Provides detailed hour information for a specific date
   - Requires date parameter in format "yyyy-MM-dd"

3. **Today in History API**
   - Endpoint: `http://v.juhe.cn/todayOnhistory/queryEvent`
   - Provides historical events on a specific date
   - Requires date parameter in format "month/day"

4. **Historical Event Details API**
   - Endpoint: `http://api.juheapi.com/japi/tohdet`
   - Provides detailed information about a specific historical event
   - Requires event ID parameter

### Network Security Configuration

To support HTTP requests on Android 9+ devices, the app uses a network security configuration that allows cleartext communication to the following domains:
- `v.juhe.cn`
- `api.juheapi.com`

## Build and Run

### Prerequisites
- Android Studio 4.0+
- Android SDK 21+
- Gradle 7.0+

### Building the App

1. Clone the repository
2. Open the project in Android Studio
3. Sync the Gradle project
4. Build the project using `./gradlew assembleDebug`
5. Install the APK on an Android device or emulator

### Running the App

1. Connect an Android device to your computer or start an emulator
2. Run the app from Android Studio
3. Or install the APK using `adb install app/build/outputs/apk/debug/app-debug.apk`

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/animee/todayhistory/
│   │   │   ├── bean/             # Data models
│   │   │   ├── network/          # Network handling
│   │   │   ├── ui/               # UI components and activities
│   │   │   ├── ui/base/          # Base classes and configurations
│   │   │   ├── MainActivity.java # Main activity
│   │   │   ├── MainContract.java # MVP contract
│   │   │   ├── MainModel.java    # Data model
│   │   │   └── MainPresenter.java # Presenter
│   │   ├── res/                  # Resources
│   │   └── AndroidManifest.xml   # App manifest
│   └── test/                     # Unit tests
└── build.gradle                  # Module build configuration
```

## UI Components

### Main Activity
- Displays today's information including Lao Huang Li and historical events
- Contains a ListView to display historical events
- Allows users to select a date from a calendar dialog

### History Activity
- Displays all historical events for a selected date
- Uses a ListView with custom adapter

### History Description Activity
- Displays detailed information about a selected historical event
- Shows event title, date, and description

## License

This project is open source and available under the MIT License.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Acknowledgments

- Thanks to Juhe API for providing the historical and Lao Huang Li data
- Built with Android Studio and Gradle
- Uses OkHttp for network requests
- Uses AndroidX libraries for modern Android development

## Changelog

### Latest Updates
- Adapted to new API interfaces
- Added hour information support
- Fixed network security policy issues
- Optimized UI and performance
- Fixed various crash issues
- Improved code structure and maintainability
- Changed network requests to asynchronous mode
- Added boundary checks to prevent crashes

## Contact

For any questions or issues, please open an issue on GitHub.
