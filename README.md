# Proyecto-Data-Structures
# 🏅 Sports Connection System – Universidad Nacional

## 📌 Project Description

This project implements a system that identifies **direct and indirect connections between students** based on the sports they practice or are interested in.

At Universidad Nacional, many students participate in different sports, but they may not always know someone who practices a sport they want to explore. However, they might still be indirectly connected through other students who share sports in common.

This system models those relationships and allows:

- Automatic grouping of students who share at least one sport.
- Determining whether a student is directly or indirectly connected to someone who practices a sport of interest.
- Indicating when no such connection exists.
- Direct access to student information using their ID.
- Removing a student from the system.
- Tracking how many students practice each sport and listing them in order.

The solution is implemented using fundamental data structures such as:

- Dynamic Arrays
- Linked Lists

## 🧩 Solution Overview

The system is based on two main entities:

Student

Each student is represented by:

- An ID

- A name

- A dynamic array storing the sports they currently practice

- A dynamic array storing the sports they are interested in

This allows the system to track both active participation and potential future interests.

Sport

Each sport is represented by:

- Its name

- A dynamic array containing the students who practice it

This structure enables the system to:

Count how many students practice a sport

- List participants

- Establish connections between students through shared activities

- Global Sport Collection

In addition to individual student records, the system maintains a dynamic array containing all available sports.
This collection allows efficient organization, lookup, and management of the sports registered in the system.

Together, these structures create a bidirectional relationship between students and sports, enabling the detection of shared interests and the analysis of direct or indirect connections.

---

## 👥 Team Members

- Sebastián González Giraldo  
- Juan Diego Cardona Cortes 
- Jeronimo Quinones Rueda
- Jesus David Pinillos

---

## 💻 Programming Language

- Java

---

## ⚙️ Installation & Execution

### 1. Clone the repository

```bash
git clone https://github.com/your-username/your-repository.git
