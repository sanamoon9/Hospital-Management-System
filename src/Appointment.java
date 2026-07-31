import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private LocalDateTime appointmentTime;
    private Doctor doctor;
    private Patient patient;
    private String status;
    private int appointmentNum;
    private static final double visitCost=100.0;
    private boolean isEmergency;
    private Department department;
    private double cost;

    public Appointment(LocalDateTime appointmentTime,Doctor doctor,Patient patient,String status,int appointmentNum ,boolean isEmergency,Department department){
        this.appointmentTime=appointmentTime;
        this.doctor=doctor;
        this.patient=patient;
        this.status=status;
        this.appointmentNum=appointmentNum;
        this.cost=visitCost;
        if (isEmergency){
            this.cost+=50;
        }
        this.isEmergency=isEmergency;
        this.department=department;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }


    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAppointmentNum() {
        return appointmentNum;
    }

    public void setAppointmentNum(int appointmentNum) {
        this.appointmentNum = appointmentNum;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    public boolean isEmergency() {
        return isEmergency;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public String getInfApp(){
        return "Doctor:"+doctor.getName()+" "+"Patient:"+patient.getName()+" "+"AppointmentTime:"+appointmentTime+" "+" "+"DepartmentName:"+department.getDepartmentName()+"AppointmentNumber:"+appointmentNum+"Status:"+status+" "+" "+"Cost:"+cost+" "+" "+"IsEmergency:"+isEmergency;
    }
    public void cancel(){
        this.status="Cancelled";
    }
    public void complete(){
        this.status="Completed";
    }
    public boolean isToday(){
        if(appointmentTime==null){
            return false;
        }
        else {
            return appointmentTime.toLocalDate().equals(LocalDate.now());
        }
    }

    public boolean isInDoctorShift(){
        return doctor.isAvailableInShift(appointmentTime.getHour());
    }
    public boolean bookAnAppointment() {
        if (department==null){
            return false;
        }
      return department.assignPatientToDoctor(patient,doctor,appointmentTime.getHour());
    }
}
