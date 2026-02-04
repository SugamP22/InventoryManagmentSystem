Inventory Management System (Android) 📱

A native Android application designed for efficient stock tracking and inventory management. This app leverages Firebase for real-time data persistence and secure user authentication.

🚀 Tech Stack

Platform: Android (Native)

Language: Java

UI Framework: XML (Layouts & Widgets)

Backend-as-a-Service: Firebase

Firebase Authentication: Secure user sign-up and login.

Firebase Realtime Database: Instant data synchronization across devices.

Build System: Gradle

✨ Key Features

Real-time Inventory Sync: View and update stock levels that sync instantly with the cloud.

Secure Authentication: User-specific accounts to protect inventory data.

Product Operations: Add new items, edit existing details, and remove stock from the list.

Image Support: (If applicable) Integration with Firebase Storage for product imagery.

Search & Filter: Quickly locate specific items within the inventory.

🛠️ Installation & Setup

Prerequisites

Android Studio (Electric Eel or newer recommended)

Java Development Kit (JDK) 11 or 17

A Firebase Project (Google Services JSON file)

Setup Instructions

Clone the repository:

git clone [https://github.com/SugamP22/InventoryManagmentSystem.git](https://github.com/SugamP22/InventoryManagmentSystem.git)


Open in Android Studio:
Select the app folder or the root project.

Firebase Configuration:

Go to the Firebase Console.

Create a new project and add an Android app with the package name found in AndroidManifest.xml.

Download the google-services.json file.

Place google-services.json inside the app/ directory of your project.

Build & Run:

Sync Gradle files.

Run the app on a physical device or emulator (API 24+ recommended).

📁 Project Structure

app/src/main/java: Contains Java classes for Activities, Adapters, and Firebase logic.

app/src/main/res/layout: XML layout files defining the User Interface.

app/google-services.json: (User-provided) Firebase configuration file.

🤝 Contributing

Contributions are welcome! Please fork the repo and submit a pull request for any features or bug fixes.

Developed by SugamP22
