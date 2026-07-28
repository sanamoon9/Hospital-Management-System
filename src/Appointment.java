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
    private double cost;
    private boolean isEmergency;
    private String departmentName;

    public Appointment(LocalDateTime appointmentTime,Doctor doctor,Patient patient,String status,int appointmentNum ,double cost,boolean isEmergency,String departmentName){
        this.appointmentTime=appointmentTime;
        this.doctor=doctor;
        this.patient=patient;
        this.status=status;
        this.appointmentNum=appointmentNum;
        this.cost=cost;
        this.isEmergency=isEmergency;
        this.departmentName=departmentName;
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

    public void setEmergency(boolean emergency) {
        isEmergency = emergency;
    }
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getInfApp(){
        return "Doctor:"+doctor.getName()+" "+"Patient:"+patient.getName()+" "+"AppointmentTime:"+appointmentTime+" "+"AppointmentNumber:"+appointmentNum+"Status:"+status+" "+" "+"Cost:"+cost+" "+" "+"IsEmergency:"+isEmergency+" "+"DepartmentName:"+departmentName;
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
    public void addExtraCost(double extraAmount){
        this.cost+=extraAmount;

    }
    public void addEmergencyCost(){
        this.isEmergency=true;
        this.cost+=50.0;
    }
    public boolean isInDoctorShift(){
        return doctor.isAvailableInShift(appointmentTime.getHour());
    }
    public boolean bookAnAppointment() {
        if ( isInDoctorShift()) {
            doctor.addPatient();
            return true;
        }
        return false;
    }
}
