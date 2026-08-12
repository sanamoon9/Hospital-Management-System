import javax.swing.*;
import java.awt.*;

public class DoctorPanel extends JPanel {
    public DoctorPanel(Hospital hospital){
        setLayout(new BorderLayout());
        JTextArea area=new JTextArea();
        area.setEditable(false);
        JScrollPane scroll=new JScrollPane(area);
        StringBuilder sb=new StringBuilder();
        for (Department d :hospital.getDepartments()){
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
        add(scroll, BorderLayout.CENTER);
    }
}

