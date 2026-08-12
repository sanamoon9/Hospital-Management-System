import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Hospital {

    private String hospitalName;
    private List<Department> departments;
    private List<Appointment>appointments;
    private FinanceManager financeManager;
    private int capacity;
    private ConditionService conditionService ;
    private PatientDA patientDA=new PatientDA();
    private AppointmentDA appointmentDA=new AppointmentDA();
    private FinanceDA financeDA=new FinanceDA();

    public Hospital(String hospitalName,FinanceManager financeManager,int capacity){
       this.hospitalName=hospitalName;
       this.financeManager=financeManager;
        this.departments=new ArrayList<>();
        this.appointments=new ArrayList<>();
        this.capacity=capacity;
        conditionService=new ConditionService(this);

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

    public void dischargePatient(Patient patient,Department department){
        department.removePatient(patient);
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

    public boolean isHospitalFull(){
        int totalPatient=0;
        for (Department d:departments){
            totalPatient+=d.getPatients().size();
        }
        return totalPatient>=capacity;
    }
    public boolean createAppointment(Patient patient, Doctor doctor, Department department, Appointment appointment) {
        if (patient==null || doctor==null|| department==null||appointment==null){
            return false;
        }
        if (!doctor.isAvailableInShift(appointment.getAppointmentTime().getHour())) {
            return false;
        }

        if (!doctor.hasAvailableAppointment()) {
            return false;
        }

        if (patient.getWallet() == null || patient.getWallet().getBalance() < appointment.getCost()) {
            return false;
        }
        Patient patient1=findPatientById(patient.getId());
        boolean patientInDepartment=patient1!=null;
        if (isHospitalFull()&& !patientInDepartment) {
            return false;
        }
        if (patientInDepartment){
            patient=patient1;
        }
        if (!department.getPatients().contains(patient)&& department.isFull()){
            return false;
        }
        boolean assigned = department.assignPatientToDoctor(patient, doctor, appointment.getAppointmentTime().getHour());
        if (assigned) {
            boolean paid=financeManager.appointmentRevenue(patient,appointment);
            if (!paid){
                department.removePatient(patient);
                return false;
            }
            appointments.add(appointment);
            appointmentDA.insert(appointment);
            if (findPatientById(patient.getId())==null){
                patientDA.insert(patient);
            }
            financeDA.addDailyIncome(LocalDate.now().toString(), appointment.getCost());
            return true;
        }

        return false;
    }
    public Department findDepartmentByName(String name){
        for (Department dep :departments){
            if (dep.getDepartmentName().equalsIgnoreCase(name)){
                return dep;
            }
        }
        return null;
    }
    public Patient findPatientById(String id){
        for (Department dep:departments){
            for (Patient p: dep.getPatients()){
                if (p.getId().equals(id)){
                    return p;
                }
            }
        }
        return null;
    }
    public ConditionService getConditionService(){
        return conditionService;
    }
}

