package Presentation;
import BusinessLogic.Department;
import BusinessLogic.Doctor;
import BusinessLogic.Hospital;
import javax.swing.*;
import java.awt.*;

public class DoctorPanel extends JPanel {
    private Hospital hospital;
    private JTextArea area;
    public DoctorPanel(Hospital hospital) {
        this.hospital = hospital;
        setLayout(new BorderLayout());
        area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        add(scroll, BorderLayout.CENTER);
        refresh();
    }
    public void refresh() {
        StringBuilder sb = new StringBuilder();
        for (Department d : hospital.getDepartments()) {
            sb.append(d.getDepartmentName()).append("\n");
            if (d.getDoctors().isEmpty()) {
                sb.append("No doctors\n");
            } else {
                for (Doctor doc : d.getDoctors()) {
                    sb.append(" Name: ").append(doc.getName()).append("\n");
                    sb.append(" Specialty: ").append(doc.getSpecialty()).append("\n");
                    sb.append("  Shift: ").append(doc.getStartHour()).append(" - ").append(doc.getEndHour()).append("\n");
                    sb.append(" Patients: ").append(doc.getPatients().size()).append("\n\n");
                }
            }
            sb.append("\n");
        }
        area.setText(sb.toString());
    }
}

