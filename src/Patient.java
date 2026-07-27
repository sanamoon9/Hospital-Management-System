import java.util.ArrayList;
import java.util.List;

public class Patient extends Person{
    private int age;
    private List<String> medicalHistory;
    private String assignedDepartment;
    private Doctor doctor;
    private boolean isEmergency;
    private double invoice;
    private int queneNumber;
    public Patient(String name,String id,String phoneNumber,int age,String assignedDepartment,boolean isEmergency,Doctor doctor,double invoice,int queneNumber ){
        super(name,id,phoneNumber);
        this.age=age;
        this.medicalHistory=new ArrayList<>();
        this.assignedDepartment=assignedDepartment;
        this.doctor=doctor;
        this.isEmergency=isEmergency;
        this.invoice=invoice;
        this.queneNumber=queneNumber;
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
    public int getQueneNumber() {
        return queneNumber;
    }

    public void setQueneNumber(int queneNumber) {
        this.queneNumber = queneNumber;
    }


    public String getInfo(){
        return "Age:"+age+" "+"medicalHistory:"+medicalHistory+" "+"assignedDepartment:"+assignedDepartment+" "+"doctorsname:"+doctor+" "+"isEmergency:"+isEmergency()+" "+"invoice:"+invoice+" "+"queneNumber:"+queneNumber;
    }
    public void addCost(double amount){
        this.invoice+=amount;
    }
    public void addMedicalHistory(String history){
        medicalHistory.add(history);
    }


}
