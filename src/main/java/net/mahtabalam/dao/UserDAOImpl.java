package net.mahtabalam.dao;

import net.mahtabalam.model.User;
import net.mahtabalam.model.User.UserBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO{
    Connection connection;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    };

    @Override
    public User findById(String id) {
        final String FIND_BY_ID_STATEMENT = "SELECT id, first_name, last_name, email, phone_number, country " +
                " FROM users WHERE id=?";
        User user = null;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(FIND_BY_ID_STATEMENT);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                UserBuilder userBuilder = new User.UserBuilder();
                user = userBuilder.setId(resultSet.getString("id"))
                           .setFirstName(resultSet.getString("first_name"))
                           .setLastName(resultSet.getString("last_name"))
                           .setEmail(resultSet.getString("email"))
                           .setPhoneNumber(resultSet.getString("phone_number"))
                           .setCountry(resultSet.getString("country"))
                           .build();
            }

        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        final String SELECT_ALL_STATEMENT = "SELECT id, first_name, last_name, email, phone_number, country " +
                                            " FROM users";
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ALL_STATEMENT);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserBuilder userBuilder = new User.UserBuilder();
            while(resultSet.next()) {
                User user = userBuilder.setId(resultSet.getString("id"))
                           .setFirstName(resultSet.getString("first_name"))
                           .setLastName(resultSet.getString("last_name"))
                           .setEmail(resultSet.getString("email"))
                           .setPhoneNumber(resultSet.getString("phone_number"))
                           .setCountry(resultSet.getString("country"))
                           .build();
                users.add(user);
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return users;
    }

    @Override
    public void create(User user) {
        final String CREATE_STATEMENT = "INSERT INTO users(first_name, last_name, " +
                                        " email, phone_number, country) " +
                                        " VALUES (?, ?, ?, ?, ?) ";
        try {
            PreparedStatement preparedStatement =
                    this.connection.prepareStatement(CREATE_STATEMENT);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getCountry());
            preparedStatement.execute();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    @Override
    public void delete(String id) {
        final String DELETE_STATEMENT = "DELETE FROM users WHERE id = ?";
        try {
             PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE_STATEMENT);
             preparedStatement.setString(1, id);
             preparedStatement.execute();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public User update(User user) {
        final String UPDATE_STATEMENT = "UPDATE users SET first_name = ?, last_name = ?," +
                "email = ?, phone_number = ?, country = ? WHERE id = ?";
        User updatedUser = null;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_STATEMENT);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getCountry());
            preparedStatement.setString(6, user.getId());
            preparedStatement.execute();
            updatedUser = findById(user.getId());
        } catch(SQLException sqlException) {
             sqlException.printStackTrace();
        }

        return updatedUser;
    }
}
