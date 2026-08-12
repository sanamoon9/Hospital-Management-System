package BusinessLogic;

public class EmergencyCost implements CostDeManager {
    public double calculateCost(int patientCount) {
        return patientCount*200;
    }
}
