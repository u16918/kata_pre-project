package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {
       String sql = "CREATE TABLE IF NOT EXISTS user " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        Transaction transaction = null;
        Session session = null;
        try {
            session = Util.getConnectHibernate().openSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица в базе данных создана");
        } catch (Exception e){
            assert transaction != null;
            transaction.rollback();
            System.out.println("Ошибка создания таблицы");
        } finally {
            assert session != null;
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        Transaction transaction = null;
        Session session = null;
        try {
            session = Util.getConnectHibernate().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
            System.out.println("Таблица users удалена");
        } catch (Exception e){
            assert transaction != null;
            transaction.rollback();
            System.out.println("Ошибка удаления таблицы");
        } finally {
            assert session != null;
            session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;
        Session session = null;
        try {
            session = Util.getConnectHibernate().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
            System.out.println("Ошибка добавления пользователя");
        } finally {
            assert session != null;
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = Util.getConnectHibernate().openSession();
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            transaction.commit();
            System.out.println("User с id – " + id + " удален из базы");
        } catch (IllegalArgumentException e) {
            assert transaction != null;
            transaction.rollback();
            System.out.println("User с id – " + id + " нет в базе");
        } finally {
            assert session != null;
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List list = null;
        Transaction transaction = null;
        Session session = null;
        try {
            session = Util.getConnectHibernate().openSession();
            transaction = session.beginTransaction();
            list = session.createQuery("from User").getResultList();
            transaction.commit();
        } catch (Exception ex) {
            assert transaction != null;
            transaction.rollback();
            System.out.println("Ошибка получения пользователей");
        } finally {
            assert session != null;
            session.close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "delete User";
        Transaction transaction = null;
        Session session = null;
        try {
            session = Util.getConnectHibernate().openSession();
            transaction = session.beginTransaction();
            session.createQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (IllegalArgumentException e) {
            assert transaction != null;
            transaction.rollback();
            System.out.println("В таблице User нет записей");
        } finally {
            assert session != null;
            session.close();
        }
    }
}
