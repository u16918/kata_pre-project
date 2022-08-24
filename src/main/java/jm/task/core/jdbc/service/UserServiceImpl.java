package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    public Connection connector() throws SQLException, ClassNotFoundException {
        Util util = new Util();
        return util.getConnect();
    }

    @Override
    public void createUsersTable() throws ClassNotFoundException {
        String createTableSQL = "CREATE TABLE user"
                + "("
                + "id INT(5) AUTO_INCREMENT, "
                + "PRIMARY KEY (id), "
                + "name VARCHAR(20) NOT NULL, "
                + "lastname VARCHAR(20) NOT NULL, "
                + "AGE INT NOT NULL"
                + ")";
        try (Statement statement = connector().createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Таблица в базе данных создана");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE user";
        try (Statement statement = connector().createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица удалена");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Таблица не существует");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws ClassNotFoundException {
        String sql = "INSERT INTO user (name, lastname, age) VALUES(?, ?, ?)";
        try (PreparedStatement preparedStatement = connector().prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) throws ClassNotFoundException, SQLException {
        PreparedStatement prepared = null;
        String sql = "DELETE FROM user WHERE id = ?";
        try {
            prepared = connector().prepareStatement(sql);
            prepared.setLong(1, id);
            prepared.executeUpdate();
        } finally {
            connector().close();
        }
        System.out.println("Запись " + id + " удалена");
    }

    @Override
    public List<User> getAllUsers() throws ClassNotFoundException, SQLException {
        List<User> userList = new ArrayList<>();
        String str = "select * from user";
        try (Statement statement = connector().createStatement()) {
            ResultSet resultSet = statement.executeQuery(str);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                userList.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Нет данных в таблице");
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM user";
        try (Statement statement = connector().createStatement()){
                statement.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Таблица пуста");
        }
        System.out.println("Таблица Очищена");

    }
}
