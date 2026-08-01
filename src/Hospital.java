import java.util.ArrayList;
import java.util.List;

public class Hospital {

    private String hospitalName;
    private List<Department> departments;
    private List<Appointment>appointments;
    private FinanceManager financeManager;
    private int capacity;
    private ConditionService conditionService=new ConditionService();

    public Hospital(String hospitalName,FinanceManager financeManager,int capacity){
       this.hospitalName=hospitalName;
       this.financeManager=financeManager;
        this.departments=new ArrayList<>();
        this.appointments=new ArrayList<>();
        this.capacity=capacity;

    }
    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public FinanceManager getFinanceManager() {
        return financeManager;
    }

    public void setFinanceManager(FinanceManager financeManager) {
        this.financeManager = financeManager;
    }
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

   public void addDoctor(Doctor doctor ,Department department){
           department.addDoctor(doctor);
   }
   public void removeDoctor(Doctor doctor){
        for (Department dep:departments){
            dep.removeDoctor(doctor);
        }
   }
   public List<Doctor> getAllDoctors(Department department){
        return new ArrayList<>(department.getDoctors());
   }

   public boolean addEmergencyPatient(Patient patient ,Department department){
     if (!patient.isEmergency()){
         return false;
     }
     department.addPatient(patient);
     return true;
   }
    public boolean addPatient(Patient patient ,Department department ,Doctor doctor,Appointment appointment){
      if (isHositalFull()) {
          return false;
      }
      return department.assignPatientToDoctor(patient,doctor,appointment.getAppointmentTime().getHour());
    }
    public void dischargePatient(Patient patient,Department department){
        department.removePatient(patient);
        if (conditionService.isSuccessCondition(department)){
        }

    }
    public List<Patient> getAllPatient(Department department){
        return new ArrayList<>(department.getPatients());
    }
    public void addDepartment(Department department){
        departments.add(department);
    }
    public List<Department> getAllDepartments(){
        return departments;
    }

    public boolean isHositalFull(){
        int totalPatient=0;
        for (Department d:departments){
            totalPatient+=d.getPatients().size();
        }
        return totalPatient>=capacity;
    }
}

