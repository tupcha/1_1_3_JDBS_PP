package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.util.Util;



public class Main {
    private static int id = 0;

    public static void main(String[] args) {
        //Util.connectToDB();
        // UserDao userDao = new UserDaoJDBCImpl();
        Util.getSessionFactory();
        UserDao userDao = new UserDaoHibernateImpl();
        userDao.createUsersTable();

        userDao.saveUser("Vasya", "Popov", (byte) 25);
        userDao.saveUser("Petya", "Ruzhkov", (byte) 18);
        userDao.saveUser("Elena", "Kataniva", (byte) 21);
        userDao.saveUser("Win", "Dizel", (byte) 35);

        userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
