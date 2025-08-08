# Laptop Shop - Backend

[![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)](https://www.oracle.com/java/) [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot) ![Database](https://img.shields.io/badge/Database-black)[![MySQL](https://img.shields.io/badge/MySQL-orange?logo=mysql)](https://www.mysql.com/)

---
## 📌 Description
___
`Laptop Shop`

### 🧠 Features
<hr style="border: 0px solid #ccc;height: 0.5px; margin: 4px 0;">


## 🚀 How to run project
___

### ⚙️Request
- Java 21
- MySQL
- IDE: IntelliJ / Eclipse / VSCode
<hr style="border: 0px solid #ccc;height: 0.5px; margin: 4px 0;">

### 📥 Clone & Build
```c
git clone https://github.com/quannh08/laptopshop.git
cd laptopshop
```

### <h3>Configure file application.yml</h3>
```c
server:
  port: 8080
  servlet:
    context-path: /

spring:
  datasource:
    url: "jdbc:mysql://localhost:3306/your_database_name"
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver

  hikari:
    maximum-pool-size: 10

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  
springdoc:
  api-docs:
    enabled: true
  swagger-ui:
    enabled: true

openapi:
  service:
    api-docs: laptopshop
    server: http://localhost:${server.port}
    title: Laptop Shop API
    version: 1.0.0

  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB

file:
  upload-dir: uploads

app:
  image-api-base-url: "http://localhost:8080/images/"


jwt:
  signerKey: "your_signerKey"
```

### ▶️ Run project
```c
mvn spring-boot:run
```
