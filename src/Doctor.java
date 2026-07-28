import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person{
    private String department;
    private int maxAppointments;
    private String specialty;
    private int currentAppointments;
    private int startHour;
    private int endHour;

    public Doctor(String name,String id,String phoneNumber,String department,int maxAppointments,int currentAppointments,String specialty,int startHour,int endHour){
        super(name, id, phoneNumber);
        this.department=department;
        this.maxAppointments=maxAppointments;
        this.specialty=specialty;
        this.currentAppointments=currentAppointments;
        this.startHour=startHour;
        this.endHour=endHour;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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


    public String getInfo(){
        return "specialty:"+specialty+"Department: "+department+" "+"currentAppointments:"+currentAppointments+" "+"shift:"+startHour+"_"+endHour;

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
    public boolean isAvailableInShift(int hour){
        return hour>=startHour && hour<endHour;
    }


}
