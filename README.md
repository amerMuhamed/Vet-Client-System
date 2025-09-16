# Vet Clinic System 🐾

## Overview
This project is a **Vet Clinic Management System** implemented using **Spring Boot (MVC)** and **Hibernate (JPA Annotations)**.  
It follows the **MVC design pattern** and exposes REST APIs for managing clinic operations such as Owners, Pets, Clinics, Doctors, and Visits.  

---

## Features
- Manage **Owners** and their **Pets**.
- Manage **Clinics** and assign/de-assign Doctors.
- Record **Visits** to Clinics with optional Doctor selection.
- Search for Clinics by **phone number** or **address**.
- REST APIs for:
  - Creating entities.
  - Getting entities by ID.
  - Listing all owner’s pets by owner ID.
  - Listing all clinic’s doctors by clinic ID.
  - Searching clinics.
  - Assigning/de-assigning a doctor to a clinic.

---
ERD
<img width="1166" height="764" alt="image" src="https://github.com/user-attachments/assets/65ab3a7b-ab25-498a-bc09-3c118a71abdf" />


## Technologies Used
- **Java 17+**
- **Spring Boot**
- **Spring MVC**
- **Spring Data JPA (Hibernate)**
- **mysql database**
- **Maven**

---

## Installation & Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/amerMuhamed/Vet-Client-System.git
   cd Vet-Client-System
