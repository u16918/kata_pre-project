package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserServiceImpl userService = new UserServiceImpl();
        User user1 = new User("Igor", "Ivanov", (byte) 20);
        User user2 = new User("Alex", "Ivanov", (byte) 25);
        User user3 = new User("Elena", "Ivanova", (byte) 35);
        User user4 = new User("Daria", "Ivanova", (byte) 15);
        userService.createUsersTable();
        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.removeUserById(1);
        userService.cleanUsersTable();
        userService.dropUsersTable();
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
       //userDaoHibernate.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        //userDaoHibernate.removeUserById(2);
        // System.out.println(userDaoHibernate.getAllUsers());
        // userDaoHibernate.createUsersTable();
        //userDaoHibernate.cleanUsersTable();
        //userDaoHibernate.createUsersTable();
        //userDaoHibernate.dropUsersTable();

    }
}

