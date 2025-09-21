package banking;

/**
 * Abstract bank account class.<br>
 * <br>
 * <p>
 * Private Variables:<br>
 * {@link #accountHolder}: AccountHolder<br>
 * {@link #accountNumber}: Long<br>
 * {@link #pin}: int<br>
 * {@link #balance}: double
 */
public abstract class Account implements AccountInterface {
    private AccountHolder accountHolder;
    private Long accountNumber;
    private int pin;
    private double balance;

    protected Account(AccountHolder accountHolder, Long accountNumber, int pin, double startingDeposit) {
        this.accountHolder=accountHolder;
        this.accountNumber=accountNumber;
        this.pin=pin;
        this.balance=startingDeposit;
    }

    public AccountHolder getAccountHolder() {        
        return this.accountHolder;
    }

    public boolean validatePin(int attemptedPin) {
    	//validates the given pin against the account's pin
        return this.pin==attemptedPin;
    }

    public double getBalance() {        
        return this.balance;
    }

    public Long getAccountNumber() {        
        return this.accountNumber;
    }

    public synchronized void creditAccount(double amount) {
       // Credits the specified amount to the account
       // Method is synchronized to ensure that only one thread can execute this block as a time
       // It prevents race conditions by allowing only one thread to modify balance at a time
    	adjustBalance(amount);	
    }

    public synchronized boolean debitAccount(double amount) {        
        // Debits the specified amount from the account if the balance is sufficient
    	// Method is synchronized for thread safety
    	// It ensures that balance checking and detection happen automatically without interruption
    	if(hasSufficientBalance(amount)) {
    		adjustBalance(-amount);
    		return true;
    	}
    	return false;
    }
    
    private boolean hasSufficientBalance(double amount) {
    	// Check if the account has enough balance for the transaction
    	return this.balance>=amount;
    }
    
    private void adjustBalance(double amount) {
    	// Adjusts the balance by a given amount, positive for credit, negative for credit
    	this.balance += amount;
    }
    
    
    
}
