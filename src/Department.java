import java.util.ArrayList;
import java.util.List;

public class Department {
    private String departmentName;
    private List<Doctor>doctors;
    private List<Patient>patients;
    private int capacity;
    private CostDeManager costDeManager;
    private double totalIncome;

    public Department(String departmentName,int capacity,CostDeManager costDeManager,double totalIncome){
        this.departmentName=departmentName;
        this.capacity=capacity;
        this.doctors=new ArrayList<>();
        this.patients=new ArrayList<>();
        this.costDeManager=costDeManager;
        this.totalIncome=totalIncome;

    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Doctor> getDoctors(){
        return doctors;
    }
    public List<Patient> getPatients(){
        return patients;
    }


    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public CostDeManager getCostDeManager() {
        return costDeManager;
    }

    public void setCostDeManager(CostDeManager costDeManager) {
        this.costDeManager = costDeManager;
    }

    public void addDoctor(Doctor doctor){
        if (!doctors.contains(doctor)){
            doctors.add(doctor);
            doctor.setDepartment(this);
        }

    }

    public void removeDoctor(Doctor doctor){
        doctors.remove(doctor);
    }

    public boolean isFull(){
       if(patients.size()>=capacity){
           return true;
       }
       return false;
    }

    public boolean addPatient(Patient patient){
       if(isFull()){
           return false;
       }
       patients.add(patient);
       patient.setAssignedDepartment(departmentName);
       return true;
    }
    public void removePatient(Patient patient){
        patients.remove(patient);
        for(Doctor d: doctors){
            d.removePatient(patient);
        }
    }
    public boolean assignPatientToDoctor(Patient patient, Doctor doctor, int hour) {

        if (patient==null || doctor==null){
            return false;
        }
        if (isFull()){
            return false;
        }
        if (!doctor.isAvailableInShift(hour)) {
            return false;
        }

        if (!doctor.hasAvailableAppointment()) {
            return false;
        }
        if (!patients.contains(patient)) {
            if (!addPatient(patient)) {
                return false;
            }
        }

        if (doctor.addPatient(patient)) {
            patient.setDoctor(doctor);
            return true;
        }
        return false;
    }
    public void addIncome(double amount){
        totalIncome+=amount;
    }
    public double getBonus(){
        if (patients.isEmpty()){
            return 500;
        }
        return 0;
    }
    public double serviceCost(){
        return costDeManager.calculateCost(patients.size());
    }
    public String getInfo(){
        return "Department:"+departmentName+" "+"Patients:"+patients.size()+" "+"Doctors:"+doctors.size()+" "+"Capacity:"+capacity+" "+"TotalIncome:"+totalIncome;
    }
}
