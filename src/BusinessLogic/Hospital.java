package BusinessLogic;

import DataAccess.AppointmentDA;
import DataAccess.FinanceDA;
import DataAccess.PatientDA;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

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

    public Hospital(String hospitalName, FinanceManager financeManager, int capacity){
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
    public List<Patient> getAllPatient(Department department){
        return new ArrayList<>(department.getPatients());
    }
    public List<Department> getAllDepartments(){
        return departments;
    }

    public void addDoctor(Doctor doctor , Department department){
           if (doctor!=null&& department!=null) {
               department.addDoctor(doctor);
           }
   }
   public void removeDoctor(Doctor doctor){
        for (Department dep:departments){
            dep.removeDoctor(doctor);
        }
   }
   public List<Doctor> getAllDoctors(Department department){
        return new ArrayList<>(department.getDoctors());
   }

   public boolean addEmergencyPatient(Patient patient , Department department){
     if (!patient.isEmergency()){
         return false;
     }
     department.addPatient(patient);
     return true;
   }

    public boolean dischargePatient(Patient patient, Department department){
        if (patient==null || department==null){
            return false;
        }
        if (!department.getPatients().contains(patient)) {
            return false;
        }

        boolean removed = department.removePatient(patient);

        if (!removed) {
            return false;
        }

        patient.setAdmitted(false);
        patient.setAssignedDepartment(null);

        patientDA.updatePatient(patient);

        if (department.getPatients().isEmpty()) {

            financeManager.addBonus(department);

            financeDA.addDailyIncome(LocalDate.now().toString(), 500);
        }
        department.removePatient(patient);
        return true;

    }
    public void addDepartment(Department department){
        if (department!=null && !departments.contains(department)) {
            departments.add(department);
        }
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
        if (!department.getDoctors().contains(doctor)){
            return false;
        }
        if (!doctor.isAvailableInShift(appointment.getAppointmentTime().getHour())) {
            return false;
        }
        int appointmentHour=appointment.getAppointmentTime().getHour();

        if (!doctor.hasAvailableAppointment() && !doctor.getPatients().contains(patient)) {
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
        if (patientInDepartment ){
            patient=patient1;
        }
        if (!department.getPatients().contains(patient)&& department.isFull()){
            return false;
        }
        double visitCost = appointment.getCost();
        double serviceCost = 0;

        if (department.getCostDeManager() != null) {
            serviceCost = department.getCostDeManager().calculateCost(1);
        }

        double totalCost = visitCost + serviceCost;
        if (patient.getWallet() == null || patient.getWallet().getBalance() < totalCost) {
            return false;
        }

        boolean assigned = department.assignPatientToDoctor(patient, doctor, appointmentHour);

        if (!assigned) {
            return false;
        }

        boolean visitPaid = financeManager.appointmentRevenue(patient, appointment);
        if (!visitPaid) {
            department.removePatient(patient);
            return false;
        }

        boolean servicePaid = financeManager.serviceRevenue(patient, department);

        if (!servicePaid) {
            patient.getWallet().deposit(visitCost);
            financeManager.setHospitalBudget(financeManager.getHospitalBudget() - visitCost);
            department.setTotalIncome(department.getTotalIncome() - visitCost);
            department.removePatient(patient);
            return false;
        }

        appointments.add(appointment);
        appointmentDA.insert(appointment);
        if (patientDA.existsById(patient.getId())) {
            patientDA.updatePatient(patient);

        } else {
            patientDA.insert(patient);
        }
        financeDA.addDailyIncome(LocalDate.now().toString(), totalCost);
        return true;
    }

    public Department findDepartmentByName(String name){
        if (name==null){
            return null;
        }
        for (Department dep :departments){
            if (dep.getDepartmentName().equalsIgnoreCase(name)){
                return dep;
            }
        }
        return null;
    }
    public Patient findPatientById(String id){
        if (id==null){
            return null;
        }
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

