public class Wallet {
    private double balance;

    public Wallet(double balance){
        this.balance=balance;
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void deposit(double amount){
        if (amount>0){
            balance+=amount;
        }
    }
    public boolean withdraw(double amount){
        if (amount>0 && balance>=amount){
            balance-=amount;
            return true;
        }
        return false;
    }

}
