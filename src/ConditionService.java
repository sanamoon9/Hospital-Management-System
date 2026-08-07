import java.util.ArrayList;
import java.util.List;

public class ConditionService {
    public boolean isCriticalCondition(Hospital hospital,Patient patient,Department department){
      return hospital.isHospitalFull();

    }
    public boolean isSuccessCondition(Department department){
        return department.getPatients().isEmpty();
    }

}

