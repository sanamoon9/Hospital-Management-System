import java.sql.*;
public class AppointmentDA {
    public void insert(Appointment appointment) {
        String sql = "INSERT INTO appointments(patientId,doctorId,date) VALUES(?,?,?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, appointment.getPatient().getId());
            stmt.setString(2, appointment.getDoctor().getId());
            stmt.setString(3, appointment.getAppointmentTime().toString());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

