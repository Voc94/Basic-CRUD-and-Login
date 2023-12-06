package repository.user;
import model.Pizza;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {

            List<User> users = new ArrayList<>();

            String sql = "SELECT * FROM user;";
            try {
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    users.add(getUserFromResultSet(resultSet));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return users;
        }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            String fetchUserSql = "SELECT * FROM `" + USER + "` WHERE `username`=? AND `password`=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(fetchUserSql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet userResultSet = preparedStatement.executeQuery()) {
                    if (userResultSet.next()) {
                        User user = new UserBuilder()
                                .setUsername(userResultSet.getString("username"))
                                .setPassword(userResultSet.getString("password"))
                                .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                                .build();
                        findByUsernameAndPasswordNotification.setResult(user);
                    } else {
                        findByUsernameAndPasswordNotification.addError("Invalid username or password!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately based on your application's needs
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database!");
        }
        return findByUsernameAndPasswordNotification;
    }


    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    @Override
    public Long findByUsername(String username) {
        try {
            String fetchUserSql = "SELECT * FROM `" + USER + "` WHERE `username`=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(fetchUserSql)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // User found, you can retrieve data from the result set
                        return resultSet.getLong("id");
                    } else {
                        System.out.println("User not found with username: " + username);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException as needed
        }
        return null;
    }
    @Override
    public User findById(Long id) {
        try {
            String fetchUserSql = "SELECT * FROM `" + USER + "` WHERE `id`=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(fetchUserSql)) {
                preparedStatement.setLong(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        User user = new UserBuilder()
                                .setId(resultSet.getLong("id"))
                                .setUsername(resultSet.getString("username"))  // Fix here
                                .setPassword(resultSet.getString("password"))
                                .build();
                        return user;
                    } else {
                        System.out.println("User not found with id: " + id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean update(User user) {
        String sql = "UPDATE " + USER + " SET username = ?, password = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3, user.getId());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("User with ID " + user.getId() + " has been updated successfully.");
                return true;
            } else {
                System.out.println("No user found with ID: " + user.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existsByUsername(String email) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + USER + "` where `username`=\'" + email + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return userResultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean removeById(Long id) {
        String sql = "DELETE FROM user WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted == 0) {
                System.out.println("No User found with ID: " + id);
            } else {
                System.out.println("User with ID " + id + " has been deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean userExists(Long id) throws SQLException {
        String fetchUserSql = "SELECT * FROM `" + USER + "` WHERE `id`=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(fetchUserSql)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }

}