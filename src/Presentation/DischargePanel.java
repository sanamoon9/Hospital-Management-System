package Presentation;

import BusinessLogic.Department;
import BusinessLogic.Hospital;
import BusinessLogic.Patient;
import javax.swing.*;
import java.awt.*;

public class DischargePanel extends JPanel {
    private Hospital hospital;

    private JComboBox<String> departmentB;
    private JComboBox<String> patientB;
    private JButton dischargeButton;
    private JLabel result;
    public DischargePanel(Hospital hospital) {
        this.hospital = hospital;
        setLayout(new BorderLayout(10, 10));
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        departmentB = new JComboBox<>();
        patientB = new JComboBox<>();
        dischargeButton = new JButton("Discharge Patient");
        result = new JLabel();
        for (Department department : hospital.getDepartments()) {
            departmentB.addItem(department.getDepartmentName());
        }
        form.add(new JLabel("Department:"));
        form.add(departmentB);

        form.add(new JLabel("Patient:"));
        form.add(patientB);

        form.add(new JLabel("Discharge Button"));
        form.add(dischargeButton);

        add(form, BorderLayout.CENTER);
        add(result, BorderLayout.SOUTH);

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        departmentB.addActionListener(e -> loadPatients());
        dischargeButton.addActionListener(e -> dischargePatient());

        if (departmentB.getItemCount() > 0) {
            loadPatients();
        }
    }

    private void loadPatients() {

        patientB.removeAllItems();
        String departmentName = (String) departmentB.getSelectedItem();
        if (departmentName == null) {
            return;
        }

        Department department = hospital.findDepartmentByName(departmentName);

        if (department == null) {
            return;
        }

        for (Patient patient : department.getPatients()) {
            patientB.addItem(patient.getId() + " - " + patient.getName());
        }
    }

    private void dischargePatient() {

        String departmentName = (String) departmentB.getSelectedItem();

        String selectedPatient = (String) patientB.getSelectedItem();

        if (departmentName == null) {
            result.setText("Department not selected");
            return;
        }

        if (selectedPatient == null) {
            result.setText("No patient selected");
            return;
        }

        Department department = hospital.findDepartmentByName(departmentName);

        if (department == null) {
            result.setText("Department not found");
            return;
        }

        String patientId = selectedPatient.split(" - ")[0];

        Patient patient = hospital.findPatientById(patientId);

        if (patient == null) {
            result.setText("Patient not found");
            return;
        }

        boolean discharged = hospital.dischargePatient(patient, department);

        if (!discharged) {
            result.setText("Patient could not be discharged");
            return;
        }

        JOptionPane.showMessageDialog(this, "Patient discharged successfully.");

        result.setText("Patient discharged successfully.");

        loadPatients();
    }
}

