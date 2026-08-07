import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:hospital.db";

    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(URL);
    }
    public static void createTables(){
        String patientTable = "CREATE TABLE IF NOT EXISTS patients (" +
                "id TEXT PRIMARY KEY," +
                "name TEXT," +
                "age INTEGER" +
                ");";

        String appointmentTable = "CREATE TABLE IF NOT EXISTS appointments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "patientId TEXT," +
                "doctorId TEXT," +
                "date TEXT" +
                ");";

        String financeTable = "CREATE TABLE IF NOT EXISTS finance (" +
                "date TEXT PRIMARY KEY," +
                "amount REAL" +
                ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(patientTable);
            stmt.execute(appointmentTable);
            stmt.execute(financeTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


