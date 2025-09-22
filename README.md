# Java Banking System

This repository contains my implementation of a simple Java banking system. I implemented the core classes and logic required for account management, basic transactions, and PIN-based authorization. The code is written to be clear, testable, and aligned with the project's unit tests.


## Implemented components
- `Bank` / `BankInterface` -> account creation (consumer & commercial), lookup, balance checks, credit/debit operations.
- `Account` (abstract) / `AccountInterface` -> represents an account with account holder, PIN validation, and balance operations.
- `AccountHolder` (abstract) and concrete implementations `Person` and `Company`.
- `ConsumerAccount` and `CommercialAccount` -> ConsumerAccount has a `Person` owner; CommercialAccount has a `Company` owner and an authorized-users list.
- `Transaction` / `TransactionInterface` -> encapsulates operations (credit/debit) and validation where appropriate.
- Unit tests in `src/test/java` exercise concurrency, basic functionality, and edge cases.

## Features
- Create consumer and commercial accounts
- PIN-based authentication and basic authorization
- Balance inquiry, deposit (credit), and withdrawal (debit)
- Simple transaction model with checks for sufficient funds and PIN validation
- Thread-safety considerations for concurrent operations (where applicable)

## Requirements
- Java 8+ (project was written to run on Java 8, but newer JDKs should work)
- Maven (or use the included Maven wrapper `mvnw`)

## How to run locally

Clone the repo and run the tests:

```bash
git clone https://github.com/mertkaracamm/Java_Banking_System.git
cd JavaBankingSystem

# Run all tests
./mvnw clean test

# Or with system-installed Maven
mvn clean test
```

Run a single test class:

```bash
./mvnw -Dtest=SampleTest test
```

## Test coverage
I examined the project structure and test classes and implemented the code to satisfy the following main checks (this section explains the intent of each test area and what I implemented to satisfy them):

1. **Basic account lifecycle**
   - Create consumer and commercial accounts.
   - Verify account numbers are returned and can be used to query balances.
   - I implemented account creation methods that return a unique `long` account number and store accounts in an internal map.

2. **Authentication & PIN validation**
   - Tests validate that correct PINs allow debit operations and incorrect PINs are rejected.
   - My `validatePin` / `debit` implementations check the provided PIN and return a boolean indicating success/failure.

3. **Balance and transaction operations**
   - Tests perform credit (deposit) and debit (withdrawal) and assert resulting balances.
   - Debit should fail if insufficient funds.
   - I ensured `creditAccount` and `debitAccount` update balances atomically for the account instance.

4. **Commercial account authorization**
   - Commercial accounts allow authorized users (Person objects) to perform operations; tests check authorized vs. unauthorized users.
   - I implemented an `authorizedUsers` list on commercial accounts and an `isAuthorizedUser(Person)` check used by debit/credit methods.

5. **Concurrency / thread-safety**
   - Some tests run concurrent operations (e.g., concurrent deposits or transfers) and assert final balances remain consistent.
   - I added synchronization (or used `synchronized` blocks) on account-level operations to avoid race conditions in balance updates.

6. **Edge cases**
   - Negative amounts, null inputs, and missing accounts are checked.
   - Methods return safe defaults or false when operations are invalid; exceptions are avoided in typical checks unless specified.

## Quick API examples
```java
Bank bank = new Bank();
long acc1 = bank.openConsumerAccount(new Person("Mert", "K", 12345), 1234, 100.0);
long acc2 = bank.openCommercialAccount(new Company("Acme", 999), 4321, 500.0);

bank.credit(acc1, 50.0); // deposit
boolean ok = bank.debit(acc1, 30.0, 1234); // withdraw with PIN

double bal = bank.getBalance(acc1);
```
