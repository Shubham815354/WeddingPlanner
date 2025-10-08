Wedding Planning App (Prototype)

A prototype Android app to help users plan and manage weddings efficiently. Built using MVVM architecture, Kotlin, Room Database, Coroutines, and modern Android components.

📌 Features
1. User Registration / Login (Basic)

Users can sign up with email or mobile number.

User credentials are stored locally using Room Database or SharedPreferences.

Basic login/logout functionality is implemented.

2. Wedding Checklist Module

Displays a sample checklist for a wedding, including tasks like:

Venue booking

Photography

Catering

Mehendi

Sangeet

Honeymoon booking

Users can add new tasks, edit existing tasks, and mark tasks as completed.

Task data is stored locally using Room Database with MVVM + Coroutines for smooth UI updates.

3. Hotel / Venue Listing (Static Data)

Shows a list of 5–10 dummy wedding venues with details:

Name of venue

Location

Price range

Capacity

Users can filter venues by budget and capacity.

Venue list is implemented with RecyclerView for smooth scrolling and efficient display.

4. UI & Creativity

Clean, modern, and wedding-themed design with appealing colors, icons, and illustrations.

ViewPager for onboarding screens and smooth transitions.

Splash screen implemented for a professional app feel.

Bonus: Animations and transitions enhance the user experience.

🛠 Tech Stack

Kotlin – Primary programming language

MVVM Architecture – Separation of concerns for maintainable code

Room Database – Local storage for user data and checklist

Coroutines – Asynchronous operations

SharedPreferences – Storing user login info

RecyclerView – Displaying lists (venues, checklist)

ViewPager2 – Onboarding or guided screens

Splash Screen – First impression screen
📌 Notes

Currently, venue data is static (hardcoded).

User registration/login and checklist features are local-only (no cloud backend).

Designed as a prototype to demonstrate core wedding planning features.
