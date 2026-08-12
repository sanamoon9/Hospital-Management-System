package DataAccess;
import BusinessLogic.Patient;
import BusinessLogic.Wallet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDA {
    public boolean existsById(String id) {

        String sql = "SELECT 1 FROM patients WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean insert(Patient patient) {
        if (patient==null){
            return false;
        }
        if (existsById(patient.getId())){
            return false;
        }
        String sql = "INSERT INTO patients(name,id,phoneNumber,age,isEmergency,isAdmitted,wallet,medicalHistory,assignedDepartment) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getId());
            stmt.setString(3, patient.getPhoneNumber());
            stmt.setInt(4, patient.getAge());
            stmt.setInt(5, patient.isEmergency() ? 1 : 0);
            stmt.setInt(6, patient.isAdmitted() ? 1 : 0);
            stmt.setDouble(7, patient.getWallet().getBalance());
            String medicalHistory = String.join(",", patient.getMedicalHistory());
            stmt.setString(8, medicalHistory);
            stmt.setString(9,patient.getAssignedDepartment());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Patient> loadAllPatients() {

        List<Patient> list = new ArrayList<>();

        String sql = "SELECT * FROM patients";

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                String name = rs.getString("name");
                String id = rs.getString("id");
                String phoneNumber = rs.getString("phoneNumber");
                int age = rs.getInt("age");
                boolean isEmergency = rs.getInt("isEmergency") == 1;
                boolean isAdmitted = rs.getInt("isAdmitted") == 1;
                double wallet = rs.getDouble("wallet");
                Wallet wallet1 = new Wallet(wallet);
                String historyStr = rs.getString("medicalHistory");
                String[] medicalHistory = historyStr != null ? historyStr.split(",") : new String[0];
                String departmentName=rs.getString("assignedDepartment");
                Patient p = new Patient(name, id, phoneNumber, age, isEmergency, isAdmitted, wallet1,medicalHistory);
                p.setEmergency(isEmergency);
                p.setAdmitted(isAdmitted);
                p.getWallet().setBalance(wallet);
                p.setAssignedDepartment(departmentName);

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
