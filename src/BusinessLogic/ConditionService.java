package BusinessLogic;

public class ConditionService {
    private Hospital hospital;
    public ConditionService(Hospital hospital){
        this.hospital=hospital;
    }
    public boolean isCriticalCondition(Patient patient,Department department){
        if (patient==null || department==null){
            return false;
        }
      return hospital.isHospitalFull() && patient.isEmergency();

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

