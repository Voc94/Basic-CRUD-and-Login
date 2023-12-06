package repository.record;

import model.SalesRecord;
import model.builder.SalesRecordBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesRecordRepositoryMySQL implements SalesRecordRepository {
    private Connection connection;

    public SalesRecordRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<SalesRecord> findAll() {
        List<SalesRecord> salesRecords = new ArrayList<>();

        String sql = "SELECT * FROM sales_record;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                salesRecords.add(getSalesRecordFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesRecords;
    }

    @Override
    public boolean save(SalesRecord salesRecord) {
        String preparedStatementSql = "INSERT INTO sales_record (employee_id, buyer_id, pizza_id) VALUES (?, ?, ?);";

        try {
            // Use PreparedStatement for the primary save
            PreparedStatement preparedStatement = connection.prepareStatement(preparedStatementSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, salesRecord.getEmployeeId());
            preparedStatement.setLong(2, salesRecord.getBuyerId());
            preparedStatement.setLong(3, salesRecord.getPizza());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM sales_record WHERE id >= 0;";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private SalesRecord getSalesRecordFromResultSet(ResultSet resultSet) throws SQLException {
        long employeeId = resultSet.getLong("employee_id");
        long buyerId = resultSet.getLong("buyer_id");
        long pizzaId = resultSet.getLong("pizza_id");

        // Build and return a SalesRecord object
        return new SalesRecordBuilder()
                .setEmployeeId(employeeId)
                .setBuyer(buyerId)
                .setPizzaId(pizzaId)
                .build();
    }
}
