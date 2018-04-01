package uk.co.bitcat.generator;

public class AccountBalance {

    private String accountId;
    private int amount;

    public AccountBalance(final String accountId, final int amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public int getAmount() {
        return amount;
    }
}
