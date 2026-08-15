package DataAccess;

import BusinessLogic.Appointment;
import BusinessLogic.Department;
import BusinessLogic.Doctor;
import BusinessLogic.Patient;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDA {
    public void insert(Appointment appointment) {
        String sql = "INSERT INTO appointments(date,doctorId,patientId,status,appointmentNum,isEmergency,departmentName) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, appointment.getAppointmentTime().toString());
            stmt.setString(2, appointment.getDoctor().getId());
            stmt.setString(3, appointment.getPatient().getId());
            stmt.setString(4, appointment.getStatus());
            stmt.setInt(5, appointment.getAppointmentNum());
            stmt.setInt(6, appointment.isEmergency() ? 1 : 0);
            stmt.setString(7, appointment.getDepartment().getDepartmentName());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getNextAppointmentNumber(){
        String sql = "SELECT COALESCE(MAX(appointmentNum), 0) + 1 AS nextNumber " +
                "FROM appointments";

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("nextNumber");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

    public List<Appointment> loadAllAppointments() {
        List<Appointment> list = new ArrayList<>();

        String sql = "SELECT * FROM appointments";

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String dateStr = rs.getString("date");
                String doctorId = rs.getString("doctorId");
                String patientId = rs.getString("patientId");
                String status = rs.getString("status");
                int num = rs.getInt("appointmentNum");
                boolean isEmergency=rs.getInt("isEmergency")==1;
                String departmentName=rs.getString("departmentName");
                LocalDateTime date = LocalDateTime.parse(dateStr);
                Doctor doctor = new Doctor();
                doctor.setId(doctorId);
                Patient patient = new Patient();
                patient.setId(patientId);
                Department department = new Department(departmentName,0,null,0);
                Appointment a = new Appointment(date,doctor,patient,status, num,isEmergency,department);
                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public void removePatientAppointment(String patientId) {

        String deleteSql = "DELETE FROM appointments WHERE patientId = ?";
        String selectSql = "SELECT id FROM appointments ORDER BY appointmentNum ASC";
        String updateSql = "UPDATE appointments SET appointmentNum = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.connect()) {

            conn.setAutoCommit(false);

            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setString(1, patientId);
                deleteStmt.executeUpdate();
            }


            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql);
                 ResultSet rs = selectStmt.executeQuery();
                 PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

                int number = 1;

                while (rs.next()) {
                    int appointmentId = rs.getInt("id");

                    updateStmt.setInt(1, number);
                    updateStmt.setInt(2, appointmentId);
                    updateStmt.executeUpdate();

                    number++;
                }
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

