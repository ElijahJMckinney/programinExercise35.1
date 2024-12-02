package progexec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInserter {
    private Connection connection;

    public DatabaseInserter(DatabaseConnection dbConnection) {
        this.connection = dbConnection.getConnection();
    }

    public long insertWithoutBatch() throws SQLException {
        String sql = "INSERT INTO Temp (num1, num2, num3) VALUES (?, ?, ?)";
        long startTime = System.currentTimeMillis();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < 1000; i++) {
                statement.setDouble(1, Math.random());
                statement.setDouble(2, Math.random());
                statement.setDouble(3, Math.random());
                statement.executeUpdate();
            }
        }
        return System.currentTimeMillis() - startTime;
    }

    public long insertWithBatch() throws SQLException {
        String sql = "INSERT INTO Temp (num1, num2, num3) VALUES (?, ?, ?)";
        long startTime = System.currentTimeMillis();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < 1000; i++) {
                statement.setDouble(1, Math.random());
                statement.setDouble(2, Math.random());
                statement.setDouble(3, Math.random());
                statement.addBatch();
                if (i % 100 == 0) { // Execute batch every 100 records
                    statement.executeBatch();
                }
            }
            statement.executeBatch(); // Execute remaining records
        }
        return System.currentTimeMillis() - startTime;
    }
}
