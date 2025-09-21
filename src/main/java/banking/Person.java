package banking;

import java.util.Objects;

public class Person extends AccountHolder {
    private final String firstName;
    private final String lastName;

    public Person(String firstName, String lastName, int idNumber) {
        super(idNumber);
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public String getFirstName() {
       return this.firstName;
    }

    public String getLastName() {
    	return this.lastName;
    }
    
    @Override
    public boolean equals(Object o) {
    	// Compares this person with another person based on first and last time
    	if(!(o instanceof Person )) return false;
    	Person person =(Person) o;
    	return Objects.equals(firstName, person.firstName) &&
    			Objects.equals(lastName, person.lastName);
    }
    
    @Override
    public int hashCode() {
    	// Generates a hash code on the person's first and last name
    	return Objects.hash(firstName,lastName);
    }
    
    
}
