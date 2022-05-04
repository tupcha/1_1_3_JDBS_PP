package jm.task.core.jdbc.util;

import jm.task.core.jdbc.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    public static final String userName = "root";
    public static final String password = "22051999";
    public static final String connectionUrl = "jdbc:mysql://localhost:3306/test";
    public static final String driverName = "com.mysql.cj.jdbc.Driver";


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
}

