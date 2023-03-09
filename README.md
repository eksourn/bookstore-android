# bookstore-android
## Phase 3 Mobile Bookstore
### Ehan Sourn Kyaw-Han Ryan Ritchie

This is phase 3 of our 3 part semester long group project for UML COMP.3100 Database II. Created using Android Studio and the prexisting bookstoredatabase we made.

# Requirements
-Android Studio
-Xampp
-JDK Mininum 1.8

# How to Run
## Server: 
    - Copy the bookstore-api folder into the htdocs folder in Xampp
    - Start Xampp server and enable Apache + MySql

## Android:
    - Open android studio > open project > open Bookstore
    - In strings.xml, change the url value to your local wifi IPv4 address. It should looks like this -
        http://[your local ip address]/bookstore-api/
