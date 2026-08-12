import javax.swing.*;
import java.awt.*;

public class AddPatientPanel extends JPanel {
    private PatientDA patientDA=new PatientDA();
    public AddPatientPanel(Hospital hospital){
        setLayout(new BorderLayout(10,10));
        JPanel form=new JPanel(new GridLayout(8,2,10,10));
        JTextField nameT =new JTextField();
        JTextField ageT=new JTextField();
        JTextField phonT=new JTextField();
        JTextField idT=new JTextField();
        JTextField walletT=new JTextField();
        JComboBox<String> dep=new JComboBox<>();
        if (hospital.getDepartments().isEmpty()){
            JOptionPane.showMessageDialog(this,"No department available");
        }
        for (Department d :hospital.getDepartments()){
            dep.addItem(d.getDepartmentName());
        }
        JCheckBox emergency=new JCheckBox("Emergency");
        JCheckBox admitted=new JCheckBox("Admitted");
        form.add(new JLabel("Name:"));
        form.add(nameT);
        form.add(new JLabel("Age:"));
        form.add(ageT);
        form.add(new JLabel("PhoneNumber:"));
        form.add(phonT);
        form.add(new JLabel("Id:"));
        form.add(idT);
        form.add(new JLabel("Wallet:"));
        form.add(walletT);
        form.add(new JLabel("Department:"));
        form.add(dep);
        form.add(new JLabel("Emergency:"));
        form.add(emergency);
        form.add(new JLabel("Admitted:"));
        form.add(admitted);
        JPanel but=new JPanel();
        JButton btn=new JButton("Add Patient");
        JLabel result = new JLabel();
        but.add(btn);
        but.add(result);
        add(form,BorderLayout.CENTER);
        add(but,BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        btn.addActionListener(e -> {
            String name = nameT.getText();
            String id=idT.getText();
            String phoneNumber=phonT.getText();
            int age;
            try {
                age=Integer.parseInt(ageT.getText());
            }
            catch (Exception exception){
                result.setText("Age must be number");
                return;
            }
           double balance;
            try {
                balance=Double.parseDouble(walletT.getText());
            }
            catch (Exception ex){
                result.setText("Wallet must be number");
                return;
            }
            Wallet wallet=new Wallet(balance);
            if (name.isEmpty()||phoneNumber.isEmpty()||id.isEmpty()) {
                result.setText(" Fill all items");
                return;
            }
            String depName = (String) dep.getSelectedItem();
            Department department = hospital.findDepartmentByName(depName);
            if (department==null){
                result.setText("Department not found");
                return;
            }
            boolean isEmergency=emergency.isSelected();
            boolean isAdmitted=admitted.isSelected();

            Patient p = new Patient(name,id,phoneNumber,age,isEmergency,isAdmitted,wallet,new String[0]);
            if (patientDA.existsById(id)) {
                result.setText("Patient with this ID already exists!");
                return;
            }

            boolean added = department.addPatient(p);

            if (added) {
                p.setAssignedDepartment(department.getDepartmentName());
                boolean saved = patientDA.insert(p);
                if (saved) {
                    result.setText("Patient added successfully");
                } else {
                    department.removePatient(p);

                    result.setText("Error saving patient");
                }
            } else {
                result.setText("Department is full!");
            }
            nameT.setText("");
            idT.setText("");
            phonT.setText("");
            ageT.setText("");
            walletT.setText("");

        });
    }
}


