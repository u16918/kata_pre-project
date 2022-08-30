package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

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
        try (Session session = Util.getConnectHibernate().getCurrentSession()){
            session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица в базе данных создана");
        } catch (Exception e){
            System.out.println("Ошибка создания таблицы");
        }


    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        try (Session session = Util.getConnectHibernate().getCurrentSession()){
            session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица users удалена");
        } catch (Exception e){
            System.out.println("Ошибка удаления таблицы");
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getConnectHibernate().getCurrentSession()){
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            System.out.println("Ошибка добавления пользователя");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getConnectHibernate().getCurrentSession()){
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
            System.out.println("User с id – " + id + " удален из базы");
        } catch (IllegalArgumentException e) {
            System.out.println("User с id – " + id + " нет в базе");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List list = null;
        try (Session session = Util.getConnectHibernate().getCurrentSession()){
            session.beginTransaction();
            list = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Ошибка получения пользователей");
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "delete User";
        try (Session session = Util.getConnectHibernate().getCurrentSession()){
            session.beginTransaction();
            session.createQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена");
        } catch (IllegalArgumentException e) {
            System.out.println("В таблице User нет записей");
        }
    }
}
