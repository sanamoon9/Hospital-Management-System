import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person{
    private String department;
    private int maxAppointments;
    private String specialty;
    private int currentAppointments;
    private List<String> shifts;

    public Doctor(String name,String id,String phoneNumber,String department,int maxAppointments,int currentAppointments,String specialty ){
        super(name, id, phoneNumber);
        this.department=department;
        this.maxAppointments=maxAppointments;
        this.specialty=specialty;
        this.currentAppointments=currentAppointments;
        this.shifts=new ArrayList<>();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        department = department;
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


    public int getCurrentAppointments() {
        return currentAppointments;
    }

    public void setCurrentAppointments(int currentAppointments) {

        this.currentAppointments = currentAppointments;
    }

    public List<String> getShifts() {
        return shifts;
    }

    public void setShifts(List<String> shifts) {
        this.shifts = shifts;
    }

    public String getInfo(){
        return "specialty:"+specialty+"Department: "+department+" "+"currentAppointments:"+currentAppointments+" "+"shift:"+shifts;

    }
    public boolean hasAvailableAppointment(){
        return currentAppointments<maxAppointments;
    }

    public boolean addPatient(){
        if(currentAppointments<maxAppointments){
            currentAppointments++;
            return true;
        }
        else {
            return false;
        }
    }
    public boolean removePatient(){
        if(currentAppointments>0){
            currentAppointments--;
            return true;
        }
        else {
            return false;
        }
    }
    public void addShifts(String shift){
        shifts.add(shift);

    }
    public boolean isAvailableInShift(String currentShift){
        return shifts.contains(currentShift);
    }


}
