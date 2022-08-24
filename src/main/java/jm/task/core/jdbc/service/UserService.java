package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void createUsersTable() throws SQLException, ClassNotFoundException;

    void dropUsersTable() throws SQLException, ClassNotFoundException;

    void saveUser(String name, String lastName, byte age) throws ClassNotFoundException, SQLException;

    void removeUserById(long id) throws ClassNotFoundException, SQLException;

    List<User> getAllUsers() throws ClassNotFoundException, SQLException;

    void cleanUsersTable() throws SQLException, ClassNotFoundException;
}
