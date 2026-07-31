public class FinanceManager extends Person{
    private double hospitalBudget;

    public FinanceManager(String name,String id,String phoneNumber,double hospitalBudget){
        super(name,id,phoneNumber);
        this.hospitalBudget=hospitalBudget;
    }

    public double getHospitalBudget() {
        return hospitalBudget;
    }

    public void setHospitalBudget(double hospitalBudget) {
        this.hospitalBudget = hospitalBudget;
    }
    public void addBudget(double amount){
        hospitalBudget+=amount;
    }
    public void appointmentRevenue(Patient patient,Appointment appointment){
        double cost=appointment.getCost();
        if (patient.getWallet().withdraw(cost)){
            hospitalBudget+=cost;
            appointment.getDepartment().totalIncome(cost);
        }
    }
    public void serviceRevenue(Patient patient,Department department){
        double cost=department.getServiceCost();
        if (patient.getWallet().withdraw(cost)){
            hospitalBudget+=cost;
            department.totalIncome(cost);
        }
    }
    public void addBonus(Department department){
        double cost=department.getBonus();
        hospitalBudget+=cost;
        department.totalIncome(cost);
    }
    public String finalInvoice(Patient patient,Department department,Appointment appointment){
        double total=appointment.getCost()+department.getServiceCost();
        return "Patient:"+patient.getName()+" "+"Department:"+department.getDepartmentName()+" "+"TotalCost:"+total;
    }
}
