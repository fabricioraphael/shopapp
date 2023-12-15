<h4> shopapp </h4>

<hr>

<h3 align="center">
    Shop Manager
    <br>
    Project to manager the shop&apos;s purchases
    <br><br>
</h3>

<hr>

## 🔖 About

Accept and store (i.e., persist) a purchase transaction & provide a way to retrieve the stored purchase transactions converted to currencies supported by the [Treasury Reporting Rates of Exchange API](https://fiscaldata.treasury.gov/datasets/treasury-reporting-rates-exchange/treasury-reporting-rates-of-exchange)


---

## 👨‍💻 Author

* [Fabrício Raphael](https://www.linkedin.com/in/fabricioraphael/)

---

## 🚀 Tecnologies

- Backend:
    - [Java 17](https://openjdk.org/projects/jdk/17/)
    - [Spring Boot 3.2.0](https://spring.io/blog/2023/11/23/spring-boot-3-2-0-available-now)
    - [Apache Maven](https://maven.apache.org/)
- Design
  - [DDD - Domain Driven Design](https://en.wikipedia.org/wiki/Domain-driven_design)
  - [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- Database:
    - [MySQL Server](https://www.mysql.com/downloads/)
- Tools
    - [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/)
    - [Postman](http://www.postman.com/downloads/)
    - [Git](https://git-scm.com/downloads)
    - [Docker](https://docs.docker.com/desktop/install/mac-install/)

---

## ⤵ Quick Setup Instructions

Install prerequisites:
- Docker (Docker-Compose)

--- 

<br>

- Step 1: Clone the repository:
  ```bash
  $ git clone https://github.com/fabricioraphael/shopapp.git
  ```

<br>

- Step 2: Clone the repository:
    - Step 2.1: access docker-local dir
  ```bash
  $ cd shopapp/docker-local/
  ```
    - Step 2.2: run services
  ```bash
  $ docker-compose up -d
  ```

---
## Usage:
- Import ShopApp.postman_collection.json in Postman
- See documentation in [swagger](http://127.0.0.1:8080/swagger-ui/index.html)
- Or with CUrl
  - Store a purchase
    ```bash
    $ curl --location 'http://127.0.0.1:8080/purchases' \
    --header 'Content-Type: application/json' \
    --data '{
    "description": "a purchase description",
    "purchaseDate": "2023-12-15",
    "amount": 47.34
      }'
    ```
  - Retrieve the purchase
    ```bash
    $ curl --location 'http://127.0.0.1:8080/purchases/9198e3d4f62949c1a1d5a7d6b89f4385?currencyConversion=Real'
    ```

---
## Requirements:

- Requirement #1: Store a Purchase Transaction
Your application must be able to accept and store (i.e., persist) a purchase transaction with a description, transaction date, and a purchase amount in United States dollars. When the transaction is stored, it will be assigned a unique identifier.
  - Field requirements
    - Description: must not exceed 50 characters
    - Transaction date: must be a valid date format
    - Purchase amount: must be a valid positive amount rounded to the nearest cent
    - Unique identifier: must uniquely identify the purchase



- Requirement #2: Retrieve a Purchase Transaction in a Specified Country’s Currency
  Based upon purchase transactions previously submitted and stored, your application must provide a way to retrieve the stored purchase transactions converted to currencies supported by the [Treasury Reporting Rates of Exchange API](https://fiscaldata.treasury.gov/datasets/treasury-reporting-rates-exchange/treasury-reporting-rates-of-exchange) based upon the exchange rate active for the date of the purchase.
  The retrieved purchase should include the identifier, the description, the transaction date, the original US dollar purchase amount, the exchange rate used, and the converted amount based upon the specified currency’s exchange rate for the date of the purchase.
  - Currency conversion requirements
    - When converting between currencies, you do not need an exact date match, but must use a currency conversion rate less than or equal to the purchase date from within the last 6 months.
    - If no currency conversion rate is available within 6 months equal to or before the purchase date, an error should be returned stating the purchase cannot be converted to the target currency.
    - The converted purchase amount to the target currency should be rounded to two decimal places (i.e., cent).
---

## 📝 License

[MIT License](https://opensource.org/license/mit/) 

---