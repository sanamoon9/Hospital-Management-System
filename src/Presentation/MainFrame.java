package Presentation;

import BusinessLogic.ConditionService;
import BusinessLogic.FinanceManager;
import BusinessLogic.Hospital;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(Hospital hospital, FinanceManager financeManager, ConditionService conditionService) {
        setTitle("________GOLD HOSPITAL________");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JTabbedPane menu = new JTabbedPane();
        menu.add("Add Patient", new AddPatientPanel(hospital));
        menu.add("Appointment", new AppointmentPanel(hospital, financeManager));
        menu.add("Reports", new ReportPanel(hospital, financeManager, conditionService));
        menu.add("Doctors", new DoctorPanel(hospital));
        add(menu, BorderLayout.CENTER);
        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        JPanel bottom = new JPanel();
        bottom.add(exit);
        add(bottom, BorderLayout.SOUTH);
        setVisible(true);
    }
}


