# Banking System Management | Spring boot

## Table of Contents

- [Prerequisites](#prerequisites)
- [Run](#Run)
- [Benefits](#Benefits)
- [Used](#Used)
- [APIS](#APIS)
- [DataBase ](#DataBase )
- [Swagger](#Swagger)
- [Author ](#Author)




## Prerequisites

Java 8: Make sure you have Java 8 or a compatible version installed on your system.

Maven: Ensure that Maven is installed to manage the project's dependencies and build process.


- Second step install IDE
  - Intelij
  
## Run
- In another step you can Run or Debug the BankingSystemManagementApplication
- after running the application, you need to create a new customer, then you can create a new bank account


## Benefits

- Clean code
- Java 8
- TDD
- Exception Handling
- Used an Idea for easily  converting entities  and DTOs


## Used

This project is created for the Banking System Management


## APIS

Customer
- Post : http://localhost:8080/api/customer
- PUT : http://localhost:8080/api/customer
- Get : http://localhost:8080/api/customer

TransactionHistory
- Post : http://localhost:8080/api/deposit
- Post : http://localhost:8080/api/withdrawal
- Post : http://localhost:8080/api/transfer_funds
- Get : http://localhost:8080/api/transaction_history/{bankAccountId}

BankAccount
- Get : http://localhost:8080/api/bankAccount
- Get : http://localhost:8080/api/bankAccount/{id}
- Post : http://localhost:8080/api/bankAccount
- Put : http://localhost:8080/api/bankAccount



## DataBase 
you can go to Database console H2 by following like and use these credentials :

- Link : http://localhost:8080/h2-console/

- UserName : milad
- Password : milad
* the value of JDBC Driver is : jdbc:h2:mem:testdb


## Swagger

- Link http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#


## Author
Milad Ghasemzadeh



