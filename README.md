Hospital Management System – Java Project A Java-based console application designed to manage hospital operations such as adding doctors, registering patients, booking appointments, canceling appointments, and generating appointment slips. This project uses Object-Oriented Programming, Java File Handling (CSV), and Java Time API. Features ✔ Add Doctor

Stores doctor details (name, specialization, phone, timing, room) in doctors.csv.

✔ Register Patient

Stores patient details (name, gender, age, phone, problem) in patients.csv.

✔ Book Appointment

Select patient → doctor → date → timeslot. Prevents slot duplication and saves data in appointments.csv.

✔ Cancel Appointment

Shows all appointments and allows cancellation (status updated in CSV).

✔ View Appointments by Doctor

Displays all appointments booked for a selected doctor.

✔ Generate Appointment Slip

Shows patient + doctor + date + time + room details in a formatted slip.

Technologies Used

Java (Core Programming)

OOP Concepts (Classes, Objects, Encapsulation)

Java I/O (File Handling) – CSV storage

Java Time API – Date and time calculations

Collections (ArrayList) – Dynamic record storage

✔ Loading Animation + Logo

Uses UIEffects class for better console experience.

├── MedicalSystem.java ├── doctors.csv ├── patients.csv ├── appointments.csv ├── (Screenshots/images) └── README.md

Data Flow Diagram User → Main Menu → Hospital System → File Storage

User → Add Doctor → doctors.csv User → Add Patient → patients.csv User → Book Appointment → appointments.csv User → Cancel Appointment → appointments.csv User → View Lists → Read CSV files

Purpose of the Project

This project demonstrates the use of Object-Oriented Programming, file handling, and real-world system design in Java. It simulates basic hospital operations in a structured, automated way.# Medical-System
