package Presentation;
import BusinessLogic.*;

import javax.swing.*;
import java.awt.*;

public class ReportPanel extends JPanel {
    private Hospital hospital;
    private FinanceManager financeManager;
    private ConditionService conditionService;
    private JTextArea area;
    public ReportPanel(Hospital hospital, FinanceManager financeManager, ConditionService conditionService) {
        this.hospital = hospital;
        this.financeManager = financeManager;
        this.conditionService = conditionService;
        setLayout(new BorderLayout());
        area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        add(scroll, BorderLayout.CENTER);
        refresh();
    }
    public void refresh(){
        StringBuilder sb = new StringBuilder();
        for (Department d : hospital.getDepartments()) {
            sb.append("departmentName: ").append(d.getDepartmentName()).append("\n");
            sb.append(" Patients: ").append(d.getPatients().size()).append("\n");
            sb.append("Capacity: ").append(d.getCapacity()).append("\n");
            sb.append("\n");
        }
        sb.append(" Budget: ").append(financeManager.getHospitalBudget()).append("\n");
        if (hospital.isCriticalCondition()) {
            sb.append("The situation is critical, and the hospital is full.\n");
        }
        else if (hospital.isSuccessCondition()) {
            sb.append("The situation is successful, a department has been completely cleared.\n");
        }
        else {
            sb.append(" Hospital Status: NORMAL\n");
        }
        area.setText(sb.toString());

    }
}

