# Data Access Object (DAO) Design Pattern

The Data Access Object (DAO) pattern is a structural pattern that allows us to isolate the application/business layer from the persistence layer (usually a relational database but could be any other persistence mechanism) using an abstract API.

The API hides from the application all the complexity of performing CRUD operations in the underlying storage mechanism. This permits both layers to evolve separately without knowing anything about each other.


## Project Structure
![Project Structure](project-structure.PNG?raw=true)

## Prerequisites :
This example uses MySQL as the database, specifically **8.0.29 version**. Create a database `evolution` and create a users table within `evolution` database.

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

## UserDAO
```java
package net.mahtabalam.dao;

import net.mahtabalam.model.User;
import java.util.List;

public interface UserDAO {
    User findById(String id);
    List<User> getAll();
    void create(User user);
    User update(User user);
    void delete(String id);
}
```

## Main Class
Below we create a User object using UserBuilder and save it to database, then we fetch all the user records from the db.
Next we update the `country` field for first record from the fetched records.
And finally we delete that first record from the database.

```java
package net.mahtabalam;

import net.mahtabalam.dao.UserDAO;
import net.mahtabalam.dao.UserDAOImpl;
import net.mahtabalam.db.DatabaseConnectionManager;
import net.mahtabalam.model.User;
import net.mahtabalam.model.User.UserBuilder;

import java.sql.Connection;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        Connection connection = new DatabaseConnectionManager().getConnection();
        UserBuilder userBuilder = new User.UserBuilder();
        User user = userBuilder.setFirstName("Mahtab")
                   .setLastName("Alam")
                   .setEmail("dummy@host.net")
                   .build();

        UserDAO userDAO = new UserDAOImpl(connection);
        userDAO.create(user);
        List<User> users = userDAO.getAll();
        User firstUserRecord = users.get(0);
        System.out.println("User Record : " + firstUserRecord);
        firstUserRecord.setCountry("India");
        User updatedRecord = userDAO.update(firstUserRecord);
        System.out.println("Updated User Record : " + updatedRecord);
        userDAO.delete(updatedRecord.getId());
    }
}

```
