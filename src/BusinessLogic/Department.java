package BusinessLogic;
import java.util.ArrayList;
import java.util.List;

public class Department {
    private String departmentName;
    private List<Doctor> doctors;
    private List<Patient> patients;
    private int capacity;
    private CostDeManager costDeManager;
    private double totalIncome;

    public Department(String departmentName, int capacity, CostDeManager costDeManager, double totalIncome) {
        this.departmentName = departmentName;
        this.capacity = capacity;
        this.doctors = new ArrayList<>();
        this.patients = new ArrayList<>();
        this.costDeManager = costDeManager;
        this.totalIncome = totalIncome;

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

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<Patient> getPatients() {
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

    public void addDoctor(Doctor doctor) {
        if (!doctors.contains(doctor) && doctor != null) {
            doctors.add(doctor);
            doctor.setDepartment(this);
        }

    }

    public void removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
    }

    public boolean isFull() {
        if (patients.size() >= capacity) {
            return true;
        }
        return false;
    }

    public boolean addPatient(Patient patient) {
        if (patient == null) {
            return false;
        }
        if (patients.contains(patient)) {
            return true;
        }
        if (isFull()) {
            return false;
        }
        patients.add(patient);
        patient.setAssignedDepartment(departmentName);
        return true;
    }

    public boolean addEmergencyPatient(Patient patient) {

        if (patient == null || !patient.isEmergency()) {
            return false;
        }

        if (patients.contains(patient)) {
            return true;
        }

        patients.add(patient);

        patient.setAssignedDepartment(departmentName);

        return true;
    }

    public boolean removePatient(Patient patient) {
        if (patient == null) {
            return false;
        }
        boolean removed = patients.remove(patient);
        if (removed) {
            for (Doctor d : doctors) {
                d.removePatient(patient);
            }
            patient.setDoctor(null);
            patient.setAssignedDepartment(null);
        }
        return removed;
    }

    public boolean assignPatientToDoctor(Patient patient, Doctor doctor, int hour) {

        if (patient == null || doctor == null) {
            return false;
        }
        if (isFull()&& !patient.isEmergency()&&!patients.contains(patient)){
            return false;
        }
        if (!doctor.isAvailableInShift(hour)) {
            return false;
        }
        if (!doctor.hasAvailableAppointment() ) {
            return false;
        }
        if (!patients.contains(patient)) {
           if (!addPatient(patient)){
                    return false;

            }
        }
        if (doctor.getPatients().contains(patient)) {
            patient.setDoctor(doctor);
            return true;
        }

        if (!doctor.addPatient(patient)) {
            return false;
        }
        patient.setDoctor(doctor);

        return true;
    }

    public void addIncome(double amount){
        if (amount>0) {
            totalIncome += amount;
        }
    }
    public double getBonus(){
        if (patients.isEmpty()){
            return 500;
        }
        return 0;
    }
    public double serviceCost(){
        if (costDeManager==null){
            return 0;
        }
        return costDeManager.calculateCost(patients.size());
    }
    public String getInfo(){
        return "Department:"+departmentName+" "+"Patients:"+patients.size()+" "+"Doctors:"+doctors.size()+" "+"Capacity:"+capacity+" "+"TotalIncome:"+totalIncome;
    }
}
