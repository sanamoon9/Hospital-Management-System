package BusinessLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Patient extends Person {
    private int age;
    private List<String> medicalHistory;
    private String assignedDepartment;
    private Doctor doctor;
    private boolean isEmergency;
    private int queueNumber;
    private boolean isAdmitted;
    private Wallet wallet;
    public Patient(String name, String id, String phoneNumber, int age, boolean isEmergency, boolean isAdmitted, Wallet wallet, String[]medicalHistory){
        super(name,id,phoneNumber);
        this.age=age;
        this.medicalHistory=new ArrayList<>(Arrays.asList(medicalHistory));
        this.isEmergency=isEmergency;
        this.isAdmitted=isAdmitted;
        this.wallet=wallet;
    }
    public Patient(){
        this.medicalHistory=new ArrayList<>();
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
    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
    public String getInfo(){
        return super.getInfo()+" "+"Age:"+age+" "+"medicalHistory:"+medicalHistory+" "+"assignedDepartment:"+assignedDepartment+" "+"BusinessLogic.Doctor name:"+(doctor !=null ? doctor.getName() : "Null") +" " + "isEmergency:"+isEmergency()+" "+"queueNumber:"+queueNumber+" "+"isAdmitted:"+isAdmitted;
    }
    public void addMedicalHistory(String history){
        medicalHistory.add(history);
    }

}
