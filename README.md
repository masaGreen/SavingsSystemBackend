# SavingsSystemBackend


 
# Overview

This Api provides necessary endpoints that a client needs to manage a savings System. Customers get to register, get accounts, pin and they can choose different accounttypes with different interest rates as well as make deposits and withdrwals as well as check balances.


# Requirements

- Java 17 or later
- MySql server running on port 3306

# Set-up

- Clone the repository:
 
         

            git clone https://github.com/yourusername/SavingsSystemBackend.git

            
- create database savings
- **NB** make sure no application is running on port 8080 otherwise change port before running the application
            

            cd SavingsSystemBackend
            mvnw clean package

            
- The application will start at http://localhost:8080
# Swagger Api documentation 
- For detailed documentation on endpoints and payloads visit "http://localhost:8080/swagger-ui.html" after your app starts.

  

