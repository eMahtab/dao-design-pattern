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
