public class SurgeryCost implements CostDeManager{
    public double calculateCost(int patientCount) {
        return patientCount*300;
    }
}
