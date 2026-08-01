import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person{

    private Department department;
    private int maxAppointments;
    private String specialty;
    private int startHour;
    private int endHour;
    private List<Patient>patients;

    public Doctor(String name,String id,String phoneNumber,int maxAppointments,String specialty,int startHour,int endHour){
        super(name, id, phoneNumber);
        this.maxAppointments=maxAppointments;
        this.specialty=specialty;
        this.startHour=startHour;
        this.endHour=endHour;
        this.patients=new ArrayList<>();

    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    public int getMaxAppointments() {
        return maxAppointments;
    }

    public void setMaxAppointments(int maxAppointments) {
        this.maxAppointments = maxAppointments;
    }


    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }



    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
    public String getInfo(){
        return super.getInfo()+" "+"specialty:"+specialty+"Department: "+department.getDepartmentName()+" "+" "+"shift:"+startHour+"_"+endHour;

    }
    public boolean hasAvailableAppointment(){
        return patients.size()<maxAppointments;
    }

    public boolean addPatient(Patient patient){
        if(!hasAvailableAppointment()){
            return false;
        }
        patients.add(patient);
        return true;
    }
    public boolean removePatient(Patient patient){
      return patients.remove(patient);
    }
    public boolean isAvailableInShift(int hour){
        return hour>=startHour && hour<endHour;
    }


}
