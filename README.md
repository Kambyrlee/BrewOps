# BrewOps – Mobile Operations App for Sleepy Seal Coffee Co.

**BrewOps** is an Android mobile application built for Sleepy Seal Coffee Co. to centralize recipe management, ingredient tracking, and operational workflows.

Developed using Java, Room Database, and activity-based navigation, BrewOps is an offline-first application designed for practical use in a real coffee shop environment. It demonstrates skills in Android development, database modeling, validation, multi-threaded processing, and structured software design.

---

## Overview

BrewOps enables staff to manage recipes, ingredients, and operational data efficiently through a clean and intuitive interface. The app uses an embedded Room Database to store information locally, ensuring speed and reliability without requiring an internet connection.

The project showcases:

- Android UI development with XML layouts  
- Room persistence with entities, DAOs, and repository classes  
- ExecutorService for background threading  
- RecyclerView for dynamic lists  
- Custom validation and data-integrity safeguards  
- Activity-based navigation without LiveData or ViewModel patterns  

---

## Tech Specs

- **Java (Android)**
- **Android Studio**
- **Room Database (SQLite)**
- **DAO + Repository Pattern**
- **ExecutorService (background threading)**
- **RecyclerView with custom adapters**
- **Material Design components**

---

## Features

### Recipe Management
- Create, view, edit, and delete recipes  
- Store ingredient lists, instructions, and metadata  
- Efficient list rendering with RecyclerView  
- Input validation to ensure complete and accurate entries  

### Ingredient Management
- Add, modify, and remove ingredients  
- Track units, quantities, and descriptions  
- Prevent deletion of ingredients referenced by active recipes  

### Background Processing
- Thread-safe database operations using ExecutorService  
- Non-blocking UI interactions  
- Graceful error handling for invalid or failed operations  

### User Interface
- Clean and simple UI based on Material Design  
- Activity-based navigation for clarity and maintainability  
- Organized layouts for intuitive user flow  

### Data Integrity & Validation
- Guard clauses to prevent illegal deletions  
- Required-field validation for forms  
- Room-level consistency through annotations and constraints  

---

## Future Enhancements

- Low-stock alert system for ingredients  
- Cloud synchronization (Firebase, AWS, etc.)  
- User authentication and role-based access control  
- Automated cost calculation per recipe  
- PDF export of recipe details  
- Search and filtering capabilities  

---

## Attributions

Elements of this project incorporate or were informed by:

- Official Android Developer Documentation (developer.android.com)  
- Example implementations of Room Database and RecyclerView  
- Material Design guidelines  
- General open-source patterns for repository and DAO architecture
- <a href="https://www.flaticon.com/free-icons/book" title="book icons">Book icons created by Freepik - Flaticon</a>  
- <a href="https://www.flaticon.com/free-icons/pencil" title="pencil icons">Pencil icons created by Anggara - Flaticon</a>
- <a href="https://www.flaticon.com/free-icons/coffee" title="coffee icons">Coffee icons created by Freepik - Flaticon</a>  

All business concepts and names related to **Sleepy Seal Coffee Co.** are fictional and created solely for academic and portfolio purposes.

---

## Author

**Kambyrlee Heath**  
*Software Engineer*  
Developed as part of a professional software engineering portfolio.
