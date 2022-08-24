package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    public Connection connectorDao() throws SQLException, ClassNotFoundException {
        Util util = new Util();
        return util.getConnect();
    }

    public void createUsersTable() throws ClassNotFoundException, SQLException {
        String createTableSQL = "CREATE TABLE userDao"
                + "("
                + "id INT(5) AUTO_INCREMENT, "
                + "PRIMARY KEY (id), "
                + "name VARCHAR(20) NOT NULL, "
                + "lastname VARCHAR(20) NOT NULL, "
                + "AGE INT NOT NULL"
                + ")";
        try (Statement statement = connectorDao().createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Таблица в базе данных создана");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void dropUsersTable() {
        String sql = "DROP TABLE userDao";
        try (Statement statement = connectorDao().createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица удалена");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Таблица не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO userDao (name, lastname, age) VALUES(?, ?, ?)";
        try (PreparedStatement preparedStatement = connectorDao().prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) throws SQLException, ClassNotFoundException {
        PreparedStatement prepared = null;
        String sql = "DELETE FROM userDao WHERE id = ?";
        try {
            prepared = connectorDao().prepareStatement(sql);
            prepared.setLong(1, id);
            prepared.executeUpdate();
        } finally {
            connectorDao().close();
        }
        System.out.println("Запись " + id + " удалена");
    }

    public List<User> getAllUsers() {
        List<User> userDaoList = new ArrayList<>();
        String str = "select * from userDao";
        try (Statement statement = connectorDao().createStatement()) {
            ResultSet resultSet = statement.executeQuery(str);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                userDaoList.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Нет данных в таблице");
        }
        return userDaoList;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM userDao";
        try (Statement statement = connectorDao().createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Таблица пуста");
        }
        System.out.println("Таблица Очищена");
    }
}
