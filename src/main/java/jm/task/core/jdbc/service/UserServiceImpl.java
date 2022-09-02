package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoJDBCImpl();
    @Override
    public void createUsersTable() {
        try {
            userDao.createUsersTable();
        } catch (Exception e) {
            System.out.println("Ошибка создания таблицы");
        }
    }

    @Override
    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try {
            userDao.saveUser(name, lastName, age);
        } catch (Exception e) {
            System.out.println("Ошибка сохранения");
        }

    }

    @Override
    public void removeUserById(long id) {
        try {
            userDao.removeUserById(id);
        } catch (Exception e) {
            System.out.println("Ошибка удаление");
        }

    }

    @Override
    public List<User> getAllUsers() {
       return userDao.getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
