import java.util.ArrayList;
import java.util.List;

public class Patient extends Person{
    private int age;
    private List<String> medicalHistory;
    private String assignedDepartment;
    private Doctor doctor;
    private boolean isEmergency;
    private double invoice;
    private int queueNumber;
    private boolean isAdmitted;
    public Patient(String name,String id,String phoneNumber,int age,String assignedDepartment,boolean isEmergency,Doctor doctor,double invoice,int queueNumber,boolean isAdmitted ){
        super(name,id,phoneNumber);
        this.age=age;
        this.medicalHistory=new ArrayList<>();
        this.assignedDepartment=assignedDepartment;
        this.doctor=doctor;
        this.isEmergency=isEmergency;
        this.invoice=invoice;
        this.queueNumber=queueNumber;
        this.isAdmitted=isAdmitted;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<String> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }


    public String getAssignedDepartment() {
        return assignedDepartment;
    }

    public void setAssignedDepartment(String assignedDepartment) {
        this.assignedDepartment = assignedDepartment;
    }


    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


    public boolean isEmergency() {
        return isEmergency;
    }

    public void setEmergency(boolean emergency) {
        isEmergency = emergency;
    }


    public double getInvoice() {
        return invoice;
    }

    public void setInvoice(double invoice) {
        this.invoice = invoice;
    }
    public int getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }

    public boolean isAdmitted() {
        return isAdmitted;
    }

    public void setAdmitted(boolean admitted) {
        isAdmitted = admitted;
    }

    public String getInfo(){
        return "Age:"+age+" "+"medicalHistory:"+medicalHistory+" "+"assignedDepartment:"+assignedDepartment+" "+"doctorsname:"+doctor.getName()+" "+"isEmergency:"+isEmergency()+" "+"invoice:"+invoice+" "+"queueNumber:"+queueNumber+" "+"isAdmitted:"+isAdmitted;
    }
    public void addCost(double amount){
        this.invoice+=amount;
    }
    public void addMedicalHistory(String history){
        medicalHistory.add(history);
    }



}
