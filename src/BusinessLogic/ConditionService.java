package BusinessLogic;

public class ConditionService {
    private Hospital hospital;
    public ConditionService(Hospital hospital){
        this.hospital=hospital;
    }
    public boolean isCriticalCondition(){
      return hospital.isHospitalFull();

    }
    public boolean isSuccessCondition(){
        for (Department department: hospital.getAllDepartments()){
            if (!department.getPatients().isEmpty()){
                return false;
            }
        }
        return true;
    }

}

