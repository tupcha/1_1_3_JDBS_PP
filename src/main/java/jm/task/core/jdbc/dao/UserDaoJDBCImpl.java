package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection con = Util.connectToDB()) {
            Statement statement = con.createStatement();
            String SQL = "CREATE TABLE  User" +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " name VARCHAR(255), " +
                    " lastName VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection con = Util.connectToDB();
             PreparedStatement preparedStatement = con.prepareStatement("DROP TABLE User")) {
            preparedStatement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection con = Util.connectToDB()) {
            PreparedStatement preparedStatement =
                    con.prepareStatement("INSERT INTO  User  VALUES (id, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в  базу");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection con = Util.connectToDB();
             PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM User WHERE id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        try (Connection con = Util.connectToDB();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM User");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                int age = resultSet.getInt(4);
                listUsers.add(new User(name, lastName, (byte) age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        try (Connection con = Util.connectToDB();
             PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM User")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
