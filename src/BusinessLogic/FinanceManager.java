package BusinessLogic;

public class FinanceManager extends Person {
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
            if (amount>0) {
                hospitalBudget += amount;
            }
        }
        public boolean appointmentRevenue(Patient patient, Appointment appointment){
            if (patient==null || appointment==null){
                return false;
            }
            double cost=appointment.getCost();
            if (patient.getWallet()==null){
                return false;
            }
            if (!patient.getWallet().withdraw(cost)) {
                return false;
            }
                hospitalBudget+=cost;
            if (appointment.getDepartment() != null) {
                appointment.getDepartment().addIncome(cost);
            }
            return true;
        }

        public boolean serviceRevenue(Patient patient, Department department){
            if (patient==null || department==null){
                return false;
            }
            if (department.getCostDeManager() == null) {
                return true;
            }
            double cost=department.getCostDeManager().calculateCost(1);
            if (patient.getWallet()==null){
                return false;
            }
            if (!patient.getWallet().withdraw(cost)) {
                return false;
            }
                hospitalBudget+=cost;
                department.addIncome(cost);
                return true;
        }
        public void addBonus(Department department){
            if (department==null){
                return;
            }
            double cost=department.getBonus();
            if (cost>0) {
                hospitalBudget += cost;
                department.addIncome(cost);
            }
        }
        public String finalInvoice(Patient patient,Department department,Appointment appointment){
            double serviceCost = 0;
            if (department != null && department.getCostDeManager() != null) {
                serviceCost = department.getCostDeManager().calculateCost(1);
            }
            double total = appointment.getCost() + serviceCost;
            return "Patient:"+patient.getName()+" "+"Department:"+department.getDepartmentName()+" "+"TotalCost:"+total;
        }
}