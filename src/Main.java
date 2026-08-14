
import BusinessLogic.*;
import DataAccess.DatabaseManager;
import DataAccess.PatientDA;
import Presentation.MainFrame;
import java.util.List;

public class Main {
    public static void main(String[] args){
        DatabaseManager.createTables();
        FinanceManager financeManager=new FinanceManager("Ali","F1","09198888",1000);
        Hospital hospital=new Hospital("Gold",financeManager,16);
        Department internalDep=new Department("Internal",3,new InternalCost(),0);
        Department surgeryDep=new Department("Surgery",10,new SurgeryCost(),0);
        Department emergencyDep=new Department("Emergency",3,new EmergencyCost(),0);
        hospital.addDepartment(emergencyDep);
        hospital.addDepartment(surgeryDep);
        hospital.addDepartment(internalDep);
        Doctor emergencyDoc=new Doctor("Nill Ahmadi","D1","0987777",70,"Emergency",8,22);
        Doctor internalDoc=new Doctor("Reza hoseiny","D2","098779",70,"Internal",10,18);
        Doctor surgeryDoc=new Doctor("Nafas Akbari","D3","09655",70,"Surgery",9,22);
        hospital.addDoctor(emergencyDoc,emergencyDep);
        hospital.addDoctor(internalDoc,internalDep);
        hospital.addDoctor(surgeryDoc,surgeryDep);
        PatientDA patientDA=new PatientDA();
        List<Patient> patientList=patientDA.loadAllPatients();
        if (patientList.isEmpty()){
            Patient patient1=new Patient("Sana Hasani","p1","097776",20,false,new Wallet(300),new String[]{"Fever,Headache"});
            Patient patient2=new Patient("Samira Ahmadi","p2","0977888",40,false,new Wallet(500),new String[]{"Diabetes"});
            Patient patient3=new Patient("Salar rezai","p3","09767",16,false,new Wallet(300),new String[]{"Cold"});
            emergencyDep.addPatient(patient1);
            internalDep.addPatient(patient3);
            surgeryDep.addPatient(patient2);
            patient1.setAssignedDepartment(emergencyDep.getDepartmentName());
            patient2.setAssignedDepartment(surgeryDep.getDepartmentName());
            patient3.setAssignedDepartment(internalDep.getDepartmentName());
            emergencyDoc.addPatient(patient1);
            surgeryDoc.addPatient(patient2);
            internalDoc.addPatient(patient3);
            patientDA.insert(patient1);
            patientDA.insert(patient2);
            patientDA.insert(patient3);
        }
        else {
            for (Patient p:patientList){
                String departmentName=p.getAssignedDepartment();
                if ("Emergency".equals(departmentName)) {
                    emergencyDep.addPatient(p);
                    emergencyDoc.addPatient(p);
                }
                else if ("Surgery".equals(departmentName)) {
                    surgeryDep.addPatient(p);
                    surgeryDoc.addPatient(p);
                }
                else if ("Internal".equals(departmentName)) {
                    internalDep.addPatient(p);
                    internalDoc.addPatient(p);
                }
            }
        }
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(hospital, financeManager, hospital.getConditionService());
            mainFrame.setVisible(true);
        });

    }
}