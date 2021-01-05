# car-repair-shop-api

The application exposes backend API for a prototype of car repair shop, 
which was a theme of the project from university course.

### Project Vision

Nowadays, we can often experience as a customer lacks of visibility
in completed tasks in case of our cars in local mechanics. 
The vision of this project focuses on preparation a work supportive prototype
for these car repair shops within indroduced issue.

### Functionalities

 1. Customer registration **(anonymous)**
 2. Logging **(customer, employee, head)**
 3. Creating customer accounts **(employee, head)**
 4. Creating employee accounts **(head)**
 5. Creating tickets - acceptance protocol of given car including 
 estimated repair price, notes, customer story, brand, model etc. **(employee, head)**
 6. Editing tickets - status, final price, calculation note etc. **(employee, head)**
 7. View of a specific ticket **(customer, employee, head)**
 8. View of customer tickets - actual and historic **(customer, employee, head)**
 9. View of all tickets - actual and historic **(employee, head)**
 10. Search console for tickets - by brand, customerId, model etc. **(employee, head)**

### Hexagonal architecture

The concept of packaging and class access modifiers in project comes from 
**[Hexagonal Architecture](https://reflectoring.io/spring-hexagonal/)**.
The reason of this approach was to experiment with new layered architecture style,
besides well-known **controller -> facade -> service -> repository** layer.

## Technology stack

- Gradle 6.7.1
- OpenJDK 15 HotSpot
- Spring Boot 2.4.0 (web, security, data JPA)
- Lombok 1.18.12
- MapStruct 1.3.1.Final
- H2 Database 1.4.200
- Flyway 5.2.1
- SpringDoc OpenAPI 1.5.2
- Groovy 2.5.14 (tests)
- Spock Framework 1.3-groovy-2.5 (tests)

## Devs tools
### Swagger Doc

You can check current API documentation under following URL.

`http://localhost:8081/swagger-ui.html`

### H2 Database Console

You can connect to H2 database in runtime mode under following URL.

`http://localhost:8081/h2-console`

- Credentials are in `application.yml`
- In case **JDBC URL** provide absolute file path e.g. `jdbc:h2:/etc/car-repair-shop-api/db/memory`

## How to run it locally?

This service is based on **Spring Boot** and **Gradle** so after cloning repository we can simply run command.
```
./gradlew bootRun
```