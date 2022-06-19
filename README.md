# Data Access Object (DAO) Design Pattern

The Data Access Object (DAO) pattern is a structural pattern that allows us to isolate the application/business layer from the persistence layer (usually a relational database but could be any other persistence mechanism) using an abstract API.

The API hides from the application all the complexity of performing CRUD operations in the underlying storage mechanism. This permits both layers to evolve separately without knowing anything about each other.


## Project Structure
![Project Structure](project-structure.PNG?raw=true)

## Prerequisites :
This example uses MySQL as the database, specifically **8.0.29 version**. Create a database `evolution` adn create a users table within `evolution` database.

```sql
CREATE TABLE users(id VARCHAR(36) NOT NULL PRIMARY KEY DEFAULT (uuid()),
                   first_name VARCHAR(50) DEFAULT NULL,
                   last_name VARCHAR(50) DEFAULT NULL,
                   email VARCHAR(100) DEFAULT NULL,
                   phone_number VARCHAR(20) DEFAULT NULL,
                   country VARCHAR(100) DEFAULT NULL);
```

## resources/config.properties
```java
db.url = jdbc:mysql://localhost:3306/evolution
db.user = mahtab
db.password = SomePassword1!

```
