package repository.pizza;

import model.Pizza;
import model.builder.PizzaBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PizzaRepositoryMySQL implements PizzaRepository {

    private Connection connection;

    public PizzaRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Pizza> findAll() {

        List<Pizza> pizzas = new ArrayList<>();

        String sql = "SELECT * FROM pizza;";
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                pizzas.add(getPizzaFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pizzas;
    }
    @Override
    public Optional<Pizza> findById(Long id) {
        String sql = "SELECT * FROM pizza WHERE id = ?";
        Optional<Pizza> pizza = Optional.empty();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                pizza = Optional.of(getPizzaFromResultSet(resultSet));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return pizza;
    }

    @Override
    public boolean save(Pizza pizza) {
        String preparedStatementSql = "INSERT INTO pizza (chef, name, deliveryDateTime) VALUES (?, ?, ?);";
        String statementSql = "INSERT INTO pizza VALUES(null, '" + pizza.getChef() + "', '" + pizza.getName() + "', '" + pizza.getDeliveryDateTime() + "');";

        try {
            // Use PreparedStatement for the primary save
            PreparedStatement preparedStatement = connection.prepareStatement(preparedStatementSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pizza.getChef());
            preparedStatement.setString(2, pizza.getName());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(pizza.getDeliveryDateTime()));

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long generatedId = generatedKeys.getLong(1);
                        pizza.setId(generatedId);
                    }
                }
            }
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM pizza WHERE id >= 0;";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Pizza getPizzaFromResultSet(ResultSet resultSet) throws SQLException {
        return new PizzaBuilder()
                .setId(resultSet.getLong("id"))
                .setChef(resultSet.getString("chef"))
                .setName(resultSet.getString("name"))
                .setDeliveryDateTime(resultSet.getTimestamp("deliveryDateTime").toLocalDateTime())
                .build();
    }

}
