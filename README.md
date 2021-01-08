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
 5. View all users **(employee, head)**
 6. Reset user password **(employee, head)**
 7. Change user self-password **(customer, employee, head)**
 8. Creating tickets - acceptance protocol of given car including 
 estimated repair price, notes, customer story, brand, model etc. **(employee, head)**
 9. Editing tickets - status, final price, calculation note etc. **(employee, head)**
 10. View of a specific ticket **(customer, employee, head)**
 11. View of customer tickets - actual and historic **(customer, employee, head)**
 12. View of all tickets - actual and historic **(employee, head)**
 13. Search console for tickets - by brand, model, email, firstname etc. **(employee, head)**

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
- Flyway 7.4.0
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

#### As a docker container
Firstly make sure that application `jar` is created.
```
./gradlew clean build
```
now you can build docker image
```
docker build --build-arg JAR_FILE='build/libs/car-repair-shop-api-*-SNAPSHOT.jar' -t car-repair-shop-api:snapshot .
```
and now you can run it
```
docker run -p 8081:8081 --name car-repair-shop-api car-repair-shop-api:snapshot -jar service.jar
```