package Presentation;
import BusinessLogic.*;
import DataAccess.AppointmentDA;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class AppointmentPanel extends JPanel {
    private AppointmentDA appointmentDA=new AppointmentDA();
    private Hospital hospital;
    private FinanceManager financeManager;
    private DoctorPanel doctorPanel;
    public AppointmentPanel(Hospital hospital, FinanceManager financeManager) {
        this.hospital=hospital;
        this.financeManager=financeManager;
        setLayout(new BorderLayout(10, 10));
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        JComboBox<String> dep = new JComboBox<>();
        JComboBox<String> doc = new JComboBox<>();
        JTextField patientId = new JTextField();
        JCheckBox emergency = new JCheckBox("Emergency");
        for (Department department : hospital.getDepartments()) {
            dep.addItem(department.getDepartmentName());
        }
        if (dep.getItemCount() > 0) {
            dep.setSelectedIndex(0);
            loadDoctors(doc, hospital, (String) dep.getSelectedItem());
        }

        dep.addActionListener(e -> {
            String depName = (String) dep.getSelectedItem();
            loadDoctors(doc,hospital,(String) dep.getSelectedItem());

        });
        form.add(new JLabel("Department:"));
        form.add(dep);
        form.add(new JLabel("Doctor:"));
        form.add(doc);
        form.add(new JLabel("Patient ID:"));
        form.add(patientId);
        form.add(new JLabel("Emergency:"));
        form.add(emergency);
        JPanel but = new JPanel();
        JButton btn = new JButton("Add Appointment");
        JLabel result = new JLabel();
        but.add(btn);
        but.add(result);
        add(form, BorderLayout.CENTER);
        add(but, BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        btn.addActionListener(e -> {
            String depName = (String) dep.getSelectedItem();
            Department de = hospital.findDepartmentByName(depName);
            if (de == null) {
                result.setText("Department not found");
                return;
            }
            String docName = (String) doc.getSelectedItem();
            Doctor selectedDoctor = null;
            for (Doctor d : de.getDoctors()) {
                if (d.getName().equals(docName)) {
                    selectedDoctor = d;
                    break;
                }
            }

            if (selectedDoctor == null) {
                result.setText(" No doctor selected");
                return;
            }

            String patiId = patientId.getText().trim();
            if (patiId.isEmpty()){
                result.setText("Enter patient id");
                return;
            }
            Patient p = hospital.findPatientById(patiId);

            if (p == null) {
                result.setText("Patient not found");
                return;
            }
            if (p.getAssignedDepartment() != null && !p.getAssignedDepartment().equalsIgnoreCase(de.getDepartmentName())) {
                result.setText("Patient belongs to another department");
                return;
            }
            boolean isEmergency = emergency.isSelected();
            LocalDateTime now=LocalDateTime.now();
            int currentHour = now.getHour();

            if (!selectedDoctor.isAvailableInShift(currentHour)) {

                result.setText("Doctor is not in shift. Shift: " + selectedDoctor.getStartHour() + "-" + selectedDoctor.getEndHour());
                return;
            }

            int appointmentNumber=appointmentDA.getNextAppointmentNumber();
            Appointment ap = new Appointment(now, selectedDoctor, p, "Scheduled",appointmentNumber, isEmergency, de);
            double visitCost = ap.getCost();

            double serviceCost = 0;
            if (de.getCostDeManager() != null) {
                serviceCost = de.getCostDeManager().calculateCost(1);
            }

            double totalCost = visitCost + serviceCost;

            if (p.getWallet() == null || p.getWallet().getBalance() < totalCost) {

                result.setText("Not enough wallet balance. Total cost: " + totalCost);
                return;
            }

            boolean booked = hospital.createAppointment(p, selectedDoctor, de, ap);
            if (!booked) {
                result.setText(" Cannot book appointment");
                return;
            }

            JOptionPane.showMessageDialog(this, "Appointment Registered"+"Visit cost: " + visitCost + "\nService cost: " + serviceCost + "\nTotal: " + totalCost);
            result.setText(" TotalCost: " +totalCost);
        });
    }


        private void loadDoctors(JComboBox<String> doc, Hospital hospital, String depName) {
            doc.removeAllItems();
            if (depName==null){
                return;
            }
            Department d = hospital.findDepartmentByName(depName);
            if (d != null) {
                for (Doctor doctor : d.getDoctors()) {
                    doc.addItem(doctor.getName());
                }
            }
        }
}


