# World of Tanks Statistics Tracker Application
The World of Tanks Statistics Tracker Application is a web-based tool that allows users to track their personal in-game statistics from the popular game "World of Tanks." The application is built using the Spring framework with a MySQL database and follows a microservices architecture. It consists of three main modules: Data, Provider, and Web, and uses ActiveMQ for message passing between the modules.
  
Currently, application supports EU server only.

### Features
- Track in-game statistics for World of Tanks players, including WN8, Winrate, WTR and others.
- Users can update their statistics by closing their World of Tanks client and opening their personal stat page, or by using the search field in the site's top navigation.
- Data updates are allowed every 12 hours to ensure the statistics are kept up-to-date.

### Architecture
The application is designed using the microservices architectural pattern, which divides the system into smaller, independent services, each responsible for specific functionality. The microservices communicate with each other through messaging with ActiveMQ, enabling loose coupling and scalability.

1. Data Module
The Data module is responsible for managing the MySQL database and handling the storage and retrieval of user statistics. It receives collected data while data updating, persisting the latest statistics to the database.

2. Provider Module
The Provider module acts as a data provider for the application. It is responsible for parsing player data and statistics from the World of Tanks API and sending this information to the Data module for storage. The Provider module also handles any necessary data transformations and implements error handling to ensure data integrity. This module contains Caffeine cache to store recent request, thus we exclude possibility of duplicating stat data collecting tasks.

3. Web Module
The Web module is the user-facing part of the application. It provides a web interface that users can access to view and track their in-game statistics. The module communicates with the Data module to fetch and display user-specific data. Users can update their statistics by following the prescribed steps (closing the World of Tanks client and accessing the stat page).

### Setup and Deployment
To deploy the World of Tanks Statistics Tracker Application, follow these steps:

1. Install and configure the required versions of Java, MySQL, and ActiveMQ on the server or hosting environment.

2. Create the MySQL database and configure the necessary tables. Files schema.sql and data.sql at wot-stat-tracker-data/resources may be helpful.

3. Deploy each module (Data, Provider, and Web) as individual microservices on the server.

4. Configure the application properties for each module to connect to the MySQL database and ActiveMQ message broker.

5. Set up proper security measures to protect user data and ensure secure communication between modules and the web interface.

### License
The World of Tanks Statistics Tracker Application is licensed under the [Apache License 2.0](LICENSE.md) License.

### Used technologies
- Java 17
- Spring Boot 3.1.1
- Spring Web (MVC & REST API)
- Spring Data & Validation
- Thymeleaf
- ActiveMQ
- Caffeine
- Mockito & JUnit
- OkHttp for Java requests
- Axios for JS requests
