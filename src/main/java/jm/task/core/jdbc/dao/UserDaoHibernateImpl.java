package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    Session session;

    @Override
    public void createUsersTable() {
        try {
            session = Util.getSessionFactory().getCurrentSession();
            String SQL = "CREATE TABLE  User" +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " name VARCHAR(255), " +
                    " lastName VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";
            session.beginTransaction();
            session.createSQLQuery(SQL).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            session.getTransaction().rollback();
        } catch (Exception e) {
            session = Util.getSessionFactory().getCurrentSession();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User ").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            session.getTransaction().rollback();
        } catch (Exception e) {
            session = Util.getSessionFactory().getCurrentSession();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = Util.getSessionFactory().getCurrentSession();
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.getTransaction().rollback();
        } catch (Exception e) {
            session = Util.getSessionFactory().getCurrentSession();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            User userFromDB = session.get(User.class, id);
            session.remove(userFromDB);
            session.getTransaction().commit();
        } catch (Exception e) {
            session = Util.getSessionFactory().getCurrentSession();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            list = session.createQuery("SELECT a FROM User a", User.class).getResultList();
            session.beginTransaction().commit();
            session.getTransaction().rollback();
        } catch (Exception e) {
            session = Util.getSessionFactory().getCurrentSession();
            session.getTransaction().rollback();
        } finally {
            this.session.close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session = Util.getSessionFactory().getCurrentSession();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
