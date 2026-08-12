package DataAccess;

import java.sql.*;
public class FinanceDA {

    public void addDailyIncome(String date, double amount) {

        String sql = "INSERT INTO finance(date,amount) VALUES(?,?) " +
                "ON CONFLICT(date) DO UPDATE SET amount = amount + ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, date);
            stmt.setDouble(2, amount);
            stmt.setDouble(3, amount);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
