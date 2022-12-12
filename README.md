
# Yoga Android App

Android App for booking Yoga Classes

### Approach -

- Hosted 3 fragments onto the Base Activity for the Login/Signup, Registration and Yoga Screens.
- The user is navigated to the appropriate screens after he tries to login/register/sign out based on the jwtAuthToken and isRegistered boolean which depicts whether the user is registered for the yoga class.
- The user will only be navigated to the Yoga screen if he's still registered in the backend and thus can only change slots on re registration after his current registration expires at the end of the month.
- Appropriate API calls are made using the Retrofit2 library and the response is parsed using Moshi.
- Implemented multithreading using Kotlin Coroutines.

### Scalability & Concepts Used -

The app is built strictly using the Clean Architecture guidelines with MVVM and demonstrates the following concepts -
- Dependency Injection using Dagger Hilt
- Separation of concerns
- Singleton and DAO design patterns.
- Observer Pattern using LiveData and Kotlin-Flow
- SOLID principles

It also uses and builds on the following techniques:

- Transformation map
- View Binding in XML files
- Kotlin Delegates
- Using Backing Properties to protect MutableLiveData
- Observable state LiveData and Flow variables to trigger navigation and observe API response.

### Tools & Technologies Used -
- Frontend: XML, Kotlin, Android Navigation Components
- DI: Dagger Hilt
- IDE: Android Studio
- API Calls: Retrofit2, Moshi
- Build System: Gradle

## Demo

- [Apetize link](https://appetize.io/app/lscr3t3p327ofknzdsyiaaeega?device=pixel4&osVersion=11.0&scale=75)
- [Debug APK](https://drive.google.com/drive/folders/1pXqYDPhowaVMHW5A4Ye_IKv2csD9d12p?usp=sharing)


## Troubleshooting

- Note: Appetize.io does not allow concurrent viewing of the app hosted on the website and thus might not work in certain cases.
- Thus, if the appetize link doesn't work, try using this google drive [link](https://drive.google.com/drive/folders/1pXqYDPhowaVMHW5A4Ye_IKv2csD9d12p?usp=sharing) to get the debug APK of the app which can be installed on any android device.
## My Learnings

- Learnt to build a highly scalable android application using the Clean architecture with MVVM and dependency injection.
- Learnt to apply SOLID principles in practicality.
## Authors

- [@Lord-Lava](https://github.com/Lord-Lava/)

