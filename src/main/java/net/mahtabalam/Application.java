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
