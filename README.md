# Restaurant Management System Backend

**Project name:** restaurant-management-system
**Names:** Sugi Gisele
**Student ID:** 27595

## Description

Spring Boot backend for the front of house of a restaurant. A guest makes a reservation
that is handled by a waiter assigned to a numbered dining table. Each waiter is trained
for several stations (bar, grill, ...). A reservation moves from BOOKED to ARRIVED and
ends SEATED or CANCELLED. Once the guests are seated, the waiter records order lines.

## Technologies

- Java 17
- Spring Boot, Spring Web, Spring Data JPA
- PostgreSQL
- Maven

## Entities

Station, DiningTable, Waiter, Guest, Reservation, OrderLine

## Steps to run the API

1. Install Java 17 and PostgreSQL.
2. Create the database in PostgreSQL:
   `CREATE DATABASE restaurant_db;`
3. Clone the repository:
   `git clone https://github.com/sugigisele/restaurant-management-system-backend.git`
4. Open `src/main/resources/application.properties` and write your own PostgreSQL
   password in `spring.datasource.password`.
5. Start the API from the project folder:
   - Windows: `.\mvnw.cmd spring-boot:run`

6. The API runs on `http://localhost:8080`.
7. Hibernate creates the tables automatically (`ddl-auto=update`).
8. Enter the seed data through the save endpoints, in this order:
   stations, dining tables, waiters, guests, reservations.
