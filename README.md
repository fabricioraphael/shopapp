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
  $ cd cd shopapp/docker-local/
  ```
    - Step 2.2: run services
  ```bash
  $ docker-compose up -d
  ```

---
Usage:
- Import ShopApp.postman_collection.json in Postman
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

## 📝 License

[MIT License](https://opensource.org/license/mit/) 

---