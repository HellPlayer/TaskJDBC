package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Util dataSource = new Util();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.execute("CREATE TABLE IF NOT EXISTS usr (id BIGINT NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(45) NOT NULL," +
                    "  lastname VARCHAR(45) NOT NULL," +
                    "  age TINYINT NOT NULL," +
                    "  PRIMARY KEY (id))");
        } catch (SQLException e) {
            throw new IllegalStateException("SQL Exception " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.execute("DROP TABLE IF EXISTS usr");
        } catch (SQLException e) {
            throw new IllegalStateException("SQL Exception " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO usr (name, lastname, age) VALUES (?, ?, ?);";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("SQL Exception " + e.getMessage());
        }

    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM usr WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("SQL Exception " + e.getMessage());
        }

    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM usr;";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)
        ) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));

                list.add(user);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("SQL Exception " + e.getMessage());
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.execute("TRUNCATE TABLE usr");
        } catch (SQLException e) {
            throw new IllegalStateException("SQL Exception " + e.getMessage());
        }
    }
}
