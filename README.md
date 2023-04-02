# Car Rental
Example Java Spring Boot application. This project shows my code style, implementation of some technologies and approaches. There is no emphasis on domain logic, details or test coverage.

## Architecture
There are two microservices:
- **Car MS** - stores cars in the stock and keeps information about car availability.
- **User MS** - stores users in the apllication.

For client communication there is REST interface. Microservices communicate using gRPC. Spring Data JPA is used for data persistence. DB migration is managed using Liquibase. For an easy app run there are Dockerfiles and docker compose.

Technologies: Java, Spring Boot, Spring Data JPA, Liquibase, gRPC, REST, Docker, PostgreSQL, JUnit, Docker container integration tests.

## Features
Some features that can be interesting are mentioned in this section.

### gRPC
For comunication between microservices gRPC is used. THe Car microservice represents the [server](/car-ms/src/main/java/com/example/carms/common/grpc/server) 
and User microservice represents [client](/user-ms/src/main/java/com/example/userms/common/integrator/car). 
Communication is defined in the [.proto](/car-ms/src/main/proto/CarService.proto) files.

### Docker
To run application easily there are *Dockerfile* for both microservices. These dockerfiles serve to generate docker image, by typing `docker build -t <image-name> .` command. When both images are built, and postgresql image is pulled from [dockerhub](https://hub.docker.com/_/postgres) all three containers can be run by [docker-compose.yml](/docker-compose.yml) and typed `docker compose up` command. This command runs all three containers in the same network and exposes ports for microservices on the localhost. `docker compose down` command can be used to shutdown all containers.

### Liquibase
For DB migration Liquibase is used. Migration is defined in [/resources/db.changelog/](/car-ms/src/main/resources/db.changelog)

### PostgreSQL lock
Concurrency is solved using Postgres advisory lock. That means there can be more instances of the application based on one DB. This lock is implemented in [/common/service](/car-ms/src/main/java/com/example/carms/common/service). An example of its usage is in [CarService](/car-ms/src/main/java/com/example/carms/module/car/service/CarService.java#L50).

### Transaction propagation
There is also an example of usage of different transaction propagation and not of the default one as is REQUIRED. There is a case where we need to save *n* images to specific car and upload them to the cloud image storage. But if creationg of any image failed, it is not allowed to cancle entire algorithm and lose already uploaded images to the cloud. User will be informated which image was successfuly uploaded and which failed. 
This is solved using transaction propagation REQUIRED_NEW which creates new transaction for each service upload call. It is necessary to place this method in the different service because new proxy for that is needed. The logic for creating report is in [CarImageService](/car-ms/src/main/java/com/example/carms/module/carimage/service/CarImageService.java) and the method, which is uploads and saves image records in the new transactions is in [CarImageUploaderService](/car-ms/src/main/java/com/example/carms/module/carimage/service/CarImageUploaderService.java).

### Cron task
Cron task runs at the end of each day and calculates following statistics:
- number of the cars which were rented that day
- maximum price of the car for that day
- average price of the car for that day
For simplicity this cron task only logs this information to the console.

### Exception handling
For API exceptions there is unchcecked exception *ApiException* which is implementing all custom exceptions in the aplication. Each exception has *message* and corresponding *response status*.

To handle all these exceptions there is [ExceptionConfig](/car-ms/src/main/java/com/example/carms/common/config/ExceptionConfig.java), which manages the error response for API exceptions.

### Strategy pattern
To show implementation of the strategy pattern there is an endpoint which allows to calculate leasing for specific car. Calculation of each car depends on car's type. Each car type has different complex algorithm for leasing calculation. The strategy pattern is used in [CarService#calculateLeasing](https://github.com/marikja/spring-boot-example/blob/9f771146806b493b804d935a36ba95f19fde92a4/car-ms/src/main/java/com/example/carms/module/car/service/CarService.java#L68) and implemented in folder [/car/service/leasing](/car-ms/src/main/java/com/example/carms/module/car/service/leasing).

### N+1 problem
How to solve this common problem is shown in the [UserResponseMapper](/user-ms/src/main/java/com/example/userms/module/user/controller/dto/mapper/UserResponseMapper.java). When the list of users should be mapped to the response, all cars for all users are retrived by only one request and then mapped to the `Map<UserId, List<userCars>>`. Then in each loop it is easy to get user's cars from this map. That means the car microservice is not called each time in the loop, but only once at the begining.
