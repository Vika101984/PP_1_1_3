package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    private Connection connection = Util.getInstance().getConnection();

    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Users (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(50),
                    lastName VARCHAR(50),
                    age TINYINT)
                """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Всё прошло успешно!Таблица юзеров создана!");
    }

    public void dropUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Всё прошло успешно!Таблица юзеров удалена!");
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            int userCount = preparedStatement.executeUpdate();

            if (userCount > 0) {
                System.out.println("User с именем - " + name + " добавлен в базу данных.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Всё прошло успешно!Юзер добавлен в таблицу!");
    }

    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM Users WHERE id = ?")) {

            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Всё прошло успешно!User с id - " + id + " удален с таблицы.");
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Все прошло успешно, лист юзеров получен");
        return users;
    }

    public void cleanUsersTable() {
        try(Statement statement = connection.createStatement()){
        statement.executeUpdate( "DELETE FROM Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Все записи в таблице удалены, таблица пустая");
    }
}
