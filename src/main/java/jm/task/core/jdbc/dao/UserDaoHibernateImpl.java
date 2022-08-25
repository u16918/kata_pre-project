package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.exception.SQLGrammarException;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Session session;
    public UserDaoHibernateImpl() {

    }
    public void getSession() {
        session = Util.getConnectHibernate().getCurrentSession();
        session.beginTransaction();
    }
    public void sessionClose() {
        assert session != null;
        session.close();
    }

    @Override
    public void createUsersTable() {
       String sql = "CREATE TABLE IF NOT EXISTS user " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        try {
            getSession();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица в базе данных создана");
        } finally {
            sessionClose();
        }


    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        try {
            getSession();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица users удалена");
        } finally {
            sessionClose();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try {
            getSession();
            session.save(user);
            session.getTransaction().commit();
        } finally {
            sessionClose();
        }

        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        try {
            getSession();
            User user = session.get(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
            System.out.println("User с id – " + id + " удален из базы");
        } catch (IllegalArgumentException e) {
            System.out.println("User с id – " + id + " нет в базе");
        } finally {
            sessionClose();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List list;
        try {
            getSession();
            list = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        } finally {
            sessionClose();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "delete User";
        try {
            getSession();
            session.createQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена");
        } catch (IllegalArgumentException e) {
            System.out.println("В таблице User нет записей");
        } finally {
            sessionClose();
        }

    }
}
