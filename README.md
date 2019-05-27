# Base64 comparator

## Run the app

* _gradle bootRun_    

**To Test the app**

* _gradle test_  

**The application has swagger on http://localhost:8080/**

**To use the application**

* post left side: **http://localhost:8080/v1/diff/ID/left**

* post right side: **http://localhost:8080/v1/diff/ID/right**

* get the differences result: **http://localhost:8080/v1/diff/ID**

## Stack
* Java 8
* Spring Boot/Stater
* JUnit + Spring Boot Test
* swagger + swagger-ui
* Unit and Integrated tests(below the spring context)

## Structure
It is composed for the:
* domain: basically the data and classes of the app domain as DDD.
* infrastructure: services injected such as services and database persistence (in memory).
* configuration: Beans and ExceptionHandlers configuration.
* api: api rest to consume the app.

## Coverage
95% line coverage, could have more but I covered just what I think is important.

## Future improvements

* Real persistence in relational database (or non realtional). As I thought that persistence and DB features were not the focus of this assessment, I decided to use a simple Map<> to manage it, and show more about a rich domain.
* Mutation coverage with Pitest for example.
* Performance tests to stress post endpoints.
* Any queue to manage large comparisons: When both sides were informed, put the comparison in a queue to process on demand and provide some insights in the /v1/diff/ID to show the status of the process, or return the result if the comparison process has finished.

