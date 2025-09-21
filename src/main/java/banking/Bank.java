package banking;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Private Variables:<br>
 * {@link #accounts}: List&lt;Long, Account&gt;
 */
public class Bank implements BankInterface {
    
	//private Map<Long, Account> accounts;

	// To store all bank accounts with thread safety
	// Key : account number(long), value Account instance associated with that account number
	private final ConcurrentHashMap<Long, Account> accounts;
	
	// Atomic long to generate unique account numbers in a tgread-safe manner
	private final AtomicLong idCounter;
	
    public Bank() {
       this.accounts = new ConcurrentHashMap<>();
       this.idCounter= new AtomicLong(0);
    }

    public Account getAccount(Long accountNumber) {
    	// Helper method to retrieve an account by its number
        return this.accounts.get(accountNumber);
    }

    public Long openCommercialAccount(Company company, int pin, double startingDeposit) {
        // Creates a new commercial account with a unique account number
    	long accountNum= generateNewAccountNumber();
        accounts.put(accountNum, new CommercialAccount(company, accountNum, pin, startingDeposit));
        return accountNum;
    }

    public Long openConsumerAccount(Person person, int pin, double startingDeposit) {
        // Creates a new consumer account for an individual with a unique account number 
    	long accountNum= generateNewAccountNumber();
    	 accounts.put(accountNum, new ConsumerAccount(person, accountNum, pin, startingDeposit));
         return accountNum;
    }

    public boolean authenticateUser(Long accountNumber, int pin) {
       // Authenticates the user by matching the provided pin
    	Account account=getAccount(accountNumber);
    	return account!=null && account.validatePin(pin);
    }

    public double getBalance(Long accountNumber) {
        // Returns the balance for the specified account number
    	Account account=getAccount(accountNumber);
    	return account!=null ? account.getBalance() : 0.0;
    }

    public void credit(Long accountNumber, double amount) {
    	Account account=getAccount(accountNumber);
    	if(account!=null) {
    		account.creditAccount(amount);
    	}
    }

    public boolean debit(Long accountNumber, double amount) {
    	Account account=getAccount(accountNumber);
    	return account!=null && account.debitAccount(amount);
    }
    
    private long generateNewAccountNumber() {
    	// Generates a unique account number by incrementing the counter
    	return idCounter.incrementAndGet();    	    	
    } 
    
}
