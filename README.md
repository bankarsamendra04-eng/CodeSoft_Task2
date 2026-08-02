# 🎓 Student Performance Evaluator

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Java_Swing-blue?style=for-the-badge)

A modern, visually appealing desktop application built with **Java Swing** that calculates and evaluates student academic performance. The system accepts dynamic subject inputs, performs robust data validation, and generates an official report card featuring total scores, averages, letter grades, and a color-coded academic status (Pass / A.T.K.T. / Fail).

## ✨ Features

* **Modern UI/UX:** Custom-themed interface with interactive buttons, hover effects, and modern color palettes overriding default Swing themes.
* **Dynamic Data Entry:** Add an unlimited number of subjects dynamically using the "+ Add Another Subject" button inside a scrollable view.
* **Robust Form Validation:** 
  * Prevents empty submissions.
  * Validates numeric inputs (marks must be between 0.0 and 100.0).
  * Prevents duplicate subject names.
* **Advanced Academic Logic:** Automatically calculates total scores (out of max possible) and average percentages.
* **A.T.K.T. System Logic:** Specifically handles the "Allowed To Keep Terms" (ATKT) system based on the number of failed subjects.
* **Color-Coded Status Badge:** Visual indicators for performance (🟩 Pass, 🟧 A.T.K.T., 🟥 Fail).

## 🧠 Grading & Status Logic

The application uses the following logic to evaluate the student based on a passing mark of **35/100**:

### Academic Status
* **PASS:** 0 subjects failed.
* **A.T.K.T.:** 1 or 2 subjects failed.
* **FAIL:** 3 or more subjects failed.

### Letter Grades

| Average Percentage | Grade |
| :--- | :--- |
| 90% - 100% | **A+** |
| 80% - 89.9% | **A** |
| 70% - 79.9% | **B** |
| 60% - 69.9% | **C** |
| 50% - 59.9% | **D** |
| 35% - 49.9% | **E** |
| *Failed (3+ subjects)* | **F** |
| *A.T.K.T.* | **N/A** *(Grade withheld)* |

## 🏗️ Project Structure (MVC Architecture)

The project is structured using the Model-View-Controller design pattern for clean, maintainable code:

```text
src/com/gradecalculator/
│
├── Main.java                       # Application entry point (EDT Thread Safe)
│
├── model/
│   ├── Student.java                # Data model and core grading/math logic
│   └── SubjectInput.java           # Data structure mapping text fields
│
├── view/
│   └── CalculatorFrame.java        # Main GUI, form layout, and event listeners
│
└── util/
    └── Theme.java                  # Centralized UI styling (Colors, Fonts, Borders)

```

<img width="225" height="225" alt="image" src="https://github.com/user-attachments/assets/096533a9-c490-4ecc-8727-4b4f3b4380da" />

