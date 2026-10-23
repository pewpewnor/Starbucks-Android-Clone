# Starbucks Android Ordering Demo

An Android UI project inspired by a coffee-shop ordering app. Browse menu items, open product details, add items to a cart, confirm an order, review order history, and view store locations on a map. Products and orders are stored locally with SQLite.

## Stack

Java, Android SDK, AndroidX, Material Components, Navigation, View Binding, SQLite, and the Google Maps SDK.

## Run

Open the `Starbucks2` directory in Android Studio and let Gradle sync. Use an Android emulator or a device with Android 6.0 (API 23) or newer, then run the app configuration.

The map screen needs a Google Maps Android API key. Configure the Maps secrets Gradle plugin with a local `MAPS_API_KEY` value and replace the `YOUR_API_KEY` placeholder in the manifest with the Gradle placeholder `${MAPS_API_KEY}`. Do not commit a real API key. Without a valid key, map tiles will not load.
