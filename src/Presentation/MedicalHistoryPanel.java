package Presentation;

import BusinessLogic.Hospital;
import BusinessLogic.Patient;
import DataAccess.PatientDA;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MedicalHistoryPanel extends JPanel {
    private Hospital hospital;
    private PatientDA patientDA;
    private JTextField patientIdField;
    private JTextArea historyArea;

    public MedicalHistoryPanel(Hospital hospital) {
        this.hospital = hospital;
        this.patientDA = new PatientDA();
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new BorderLayout(10, 10));

       form.add(new JLabel("Patient ID:"), BorderLayout.WEST);
        patientIdField = new JTextField();

        form.add(patientIdField, BorderLayout.CENTER);

        JButton Button = new JButton("Search");

       form.add(Button, BorderLayout.EAST);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(historyArea);
        add(form, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Button.addActionListener(e -> searchPatient());
        patientIdField.addActionListener(e -> searchPatient());
    }

    private void searchPatient() {

        String id = patientIdField.getText().trim();

        if (id.isEmpty()) {
            historyArea.setText("Enter patient ID.");
            return;
        }

        Patient patient = hospital.findPatientById(id);

        if (patient != null) {
            showHistory(patient);
            return;
        }

        List<Patient> patients = patientDA.loadAllPatients();

        for (Patient p : patients) {
            if (p.getId().equals(id)) {
                showHistory(p);
                return;
            }
        }

        historyArea.setText("Patient not found.");
    }

    private void showHistory(Patient patient) {

        StringBuilder sb = new StringBuilder();

        sb.append("Patient Information\n");
        sb.append("-------------------------\n");
        sb.append("Name: ").append(patient.getName()).append("\n");
        sb.append("ID: ").append(patient.getId()).append("\n");
        sb.append("Age: ").append(patient.getAge()).append("\n");
        sb.append("\nMedical History\n");
        sb.append("-------------------------\n");
        if (patient.getMedicalHistory() == null || patient.getMedicalHistory().isEmpty()) {
            sb.append("No medical history recorded.");

        } else {
            for (String history : patient.getMedicalHistory()) {
                sb.append("- ").append(history).append("\n");
            }
        }
        historyArea.setText(sb.toString());
    }
}
