import javax.swing.*;
import java.awt.*;

public class ReportPanel extends JPanel {

    public ReportPanel(Hospital hospital, FinanceManager financeManager,ConditionService conditionService) {

        setLayout(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        StringBuilder sb = new StringBuilder();
        for (Department d : hospital.getDepartments()) {
            sb.append("departmentName: ").append(d.getDepartmentName()).append("\n");
            sb.append(" Patients: ").append(d.getPatients().size()).append("\n");
            sb.append("Capacity: ").append(d.getCapacity()).append("\n");
            sb.append("\n");
        }
        sb.append(" Budget: ").append(financeManager.getHospitalBudget()).append("\n");
        if (hospital.isHospitalFull()) {
            sb.append("The situation is critical, and the hospital is full.\n");
        }
        else if (conditionService.isSuccessCondition()) {
            sb.append("The situation is successful, and all sections are free of patients.\n");
        }
        else {
            sb.append(" Hospital Status: NORMAL\n");
        }
        area.setText(sb.toString());
        add(scroll, BorderLayout.CENTER);
    }
}

