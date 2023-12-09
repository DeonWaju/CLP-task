# ImageApp

## Overview
ImageApp is an Android application built with Kotlin, featuring user authentication using dummyjson.com, image loading, persistent user login, image uploading, and storage using Room Database. The app keeps the user logged in until explicitly logged out, and it also ensures the user's uploaded images persist across app sessions.

## Features
User Authentication: Login using dummy data from dummyjson.com for testing purposes.  
Image Loading: Display images fetched from various sources or URLs.  
Persistent User Login: Keep the user logged in across app sessions.  
Image Uploading: Allow users to upload images, which are saved locally in the database.  
Local Database: Utilize Room Database to store user data and uploaded images.  
Secure Preferences: Use secure preferences to store sensitive information.  
Glide Image Loading: Efficiently load and display images using Glide.  

## Tech Stack
User Authentication: Utilize dummy data from dummyjson.com for testing.  
Image Loading: Glide for efficient image loading and caching.  
Persistent User Login: Securely store user credentials using Secure Preferences.  
Image Uploading: Allow users to upload images, which are saved locally using Room Database.  
Local Database: Room Database for efficient local storage.  
Coroutines: Use Kotlin coroutines for asynchronous programming.  
Dagger Hilt: Dependency injection for a modular and maintainable codebase.  
Networking: Retrofit for making network requests.  
Security: Utilize security-crypto from AndroidX for secure data storage.  
Permissions Handling: Easy Permissions for handling app permissions.  

## Getting Started  
Clone the Repository:

``` git clone https://github.com/your-username/ImageApp.git  ```

## Build and Run:
Open the project in Android Studio and build/run the app on an emulator or physical device.

Dummy Data for Testing
For user authentication, the app uses dummy data provided by dummyjson.com. Update the login API endpoint in the codebase accordingly.

Keep in Mind
Persistent Login: User login is maintained until the user explicitly logs out.
Image Persistence: Uploaded images are stored locally and persist across app sessions.
Logout: Logging out will clear user data, requiring re-authentication.
Contributing
If you'd like to contribute to this project, please follow the contribution guidelines.

License
This project is licensed under the MIT License - see the LICENSE file for details.


##To Do
1. Arrange gradle file dependencies in an abstracted folder from gradle.
2. Modularize the project completely.
3. Write tests.

