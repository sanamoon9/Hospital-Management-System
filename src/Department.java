import java.util.ArrayList;
import java.util.List;

public class Department {
    private String departmentName;
    private List<Doctor>doctors;
    private List<Patient>patients;
    private int capacity;
    private double departmentCost;
    public Department(String departmentName,int capacity,double departmentCost){
        this.departmentName=departmentName;
        this.capacity=capacity;
        this.departmentCost=departmentCost;
        this.doctors=new ArrayList<>();
        this.patients=new ArrayList<>();

    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getDepartmentCost() {
        return departmentCost;
    }

    public void setDepartmentCost(double departmentCost) {
        this.departmentCost = departmentCost;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public void addDoctor(Doctor doctor){
        doctors.add(doctor);
    }
    public void removeDoctor(Doctor doctor){
        doctors.remove(doctor);
    }
    public List<Doctor> getDoctors(){
        return doctors;
    }
    public List<Patient> getPatients(){
        return patients;
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

        if (doctor.addPatient(patient)){
            patient.setDoctor(doctor);
            patients.add(patient);
            return true;
        }
        return false;
    }
    public double totalCost(){
        double total=0;
        for (Patient p:patients){
            total+=p.getInvoice();
        }
        return total+getBonus();
    }
    public double getBonus(){
        if (patients.isEmpty()){
            return 500;
        }
        return 0;
    }
    public String getInfo(){
        return "Department:"+departmentName+" "+"Patients:"+patients.size()+" "+"Doctors:"+doctors.size()+" "+"Capacity:"+capacity+" "+"TotalCost:"+totalCost();
    }
}
