package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.management.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        String SQL = "CREATE TABLE  User" +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " name VARCHAR(255), " +
                " lastName VARCHAR(255), " +
                " age INTEGER, " +
                " PRIMARY KEY ( id ))";
        session.beginTransaction();
        session.createSQLQuery(SQL).addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS User ").addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try( Session session = Util.getSessionFactory().getCurrentSession();) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
        }

    }

    @Override
    public void removeUserById(long id) {
        try( Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            User userFromDB = session.get(User.class, id);
            session.remove(userFromDB);
            session.getTransaction().commit();
        }

    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT a FROM User a", User.class).getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session =  Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
