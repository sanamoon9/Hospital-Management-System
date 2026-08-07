public class InternalCost implements CostDeManager{
    public double calculateCost(int patientCount) {
        return patientCount*100;
    }
}
