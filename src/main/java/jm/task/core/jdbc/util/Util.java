package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;


public class Util {
    public static SessionFactory sessionFactory;
    public static final String userName = "root";
    public static final String password = "22051999";
    public static final String connectionUrl = "jdbc:mysql://localhost:3306/test";
    public static final String driverName = "com.mysql.cj.jdbc.Driver";

    public static final String DIALECT = "org.hibernate.dialect.MySQLDialect";


    public static Connection connectToDB() {
        Connection connection = null;
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, driverName);
                settings.put(Environment.URL, connectionUrl);
                settings.put(Environment.USER, userName);
                settings.put(Environment.PASS, password);
                settings.put(Environment.DIALECT, DIALECT);
                settings.put(Environment.SHOW_SQL, "True");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }


}

