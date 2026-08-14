package Presentation;
import javax.swing.*;
import java.awt.*;
import BusinessLogic.ConditionService;
import BusinessLogic.FinanceManager;
import BusinessLogic.Hospital;
import Presentation.AddPatientPanel;
import Presentation.AppointmentPanel;
import Presentation.DoctorPanel;
import Presentation.ReportPanel;


public class MainFrame extends JFrame {
    public MainFrame(Hospital hospital, FinanceManager financeManager, ConditionService conditionService) {
        setTitle("________GOLD HOSPITAL________");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JTabbedPane menu = new JTabbedPane();
        AddPatientPanel addPatientPanel=new AddPatientPanel(hospital);
        AppointmentPanel appointmentPanel=new AppointmentPanel(hospital, financeManager);
        ReportPanel reportPanel= new ReportPanel(hospital, financeManager, conditionService);
        DoctorPanel doctorPanel= new DoctorPanel(hospital);
        DischargePanel dischargePanel=new DischargePanel(hospital);
        MedicalHistoryPanel medicalHistoryPanel=new MedicalHistoryPanel(hospital);
        menu.add("Add Patient",  addPatientPanel);
        menu.add("Appointment", appointmentPanel);
        menu.add("Reports",reportPanel);
        menu.add("Doctors", doctorPanel);
        menu.add("Discharge",dischargePanel);
        menu.add("Medical History",medicalHistoryPanel);
        menu.addChangeListener(e -> {
            reportPanel.refresh();
            doctorPanel.refresh();
        });
        add(menu, BorderLayout.CENTER);
        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        JPanel bottom = new JPanel();
        bottom.add(exit);
        add(bottom, BorderLayout.SOUTH);
        setVisible(true);
    }
}

