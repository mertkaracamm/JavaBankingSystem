package banking;

/**
 * Private Variables:<br>
 * {@link #accountNumber}: Long<br>
 * {@link #bank}: Bank<br>
 */
public class Transaction implements TransactionInterface {
    private final Long accountNumber;
    private final Bank bank;

    /**
     * @param bank          The bank where the account is housed.
     * @param accountNumber The customer's account number.
     * @param attemptedPin  The PIN entered by the customer.
     * @throws Exception Account validation failed.
     */
    public Transaction(Bank bank, Long accountNumber, int attemptedPin) throws Exception {
        // Authenticates the user based on the account number and pin
    	if(!bank.authenticateUser(accountNumber,attemptedPin)) {
    		throw new Exception("Authenticated Failed");
    	}
    	this.bank=bank;
    	this.accountNumber=accountNumber;
    }

    public double getBalance() {
    	return bank.getBalance(accountNumber);
    }

    public void credit(double amount) {
        bank.credit(accountNumber,amount);
    }

    public boolean debit(double amount) {
        return bank.debit(accountNumber,amount);
    }
}
