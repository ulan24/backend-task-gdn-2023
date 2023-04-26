# Currency Exchange Rate API (Spring Boot)

This is a Spring Boot application that provides exchange rate information for different currencies.

## Prerequisites
- Java JDK 11 or higher
- Maven

## Getting Started
1. Clone the repository to your local machine:
```
https://github.com/ulan24/backend-task-gdn-2023.git
```

2. Navigate to the project directory:
```
cd backend-task-gdn-2023
```

3. Build the project using Maven:
```
mvn clean install
```

4. Start the server:
```
mvn spring-boot:run
```

5. Test the operations using a REST client or a tool like `curl` or `Postman`. For example:

- To query exchange rate for GBP on April 25, 2023, run the following command:

  ```
  curl http://localhost:8000/api/exchangerates/GBP/2023-04-25
  ```

  Expected response: `{"currencyCode":"GBP","date":"2023-04-25","exchangeRate":5,1958}`

- To get max and min values for exchange rates for a specific currency and number of quotations, run the following command:

  ```
  curl http://localhost:8000/api/maxminaverage/USD/last/18
  ```

  Expected response: `{"currencyCode":"USD","max_average_value":"4,3168","min_average_value":"4,1649"}`

## API Documentation
The API documentation is generated using Springdoc OpenAPI and can be accessed by navigating to `http://localhost:8000/swagger-ui.html` or `http://localhost:8000/swagger-ui/index.html` in your web browser.
