package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {
    private final Account account;
    private final List<String> errors;

    public AccountValidator(Account account) {
        this.account = account;
        errors = new ArrayList<>();
    }

    public List<String> getErrors() {
        return errors;
    }

    private void validateIdNumber(Account account){
        int length = Long.toString(account.getIdentificationNumber()).length();
        if(length != 6)
            errors.add("Id number should have length 6");
    }

    private void validateMoneyAmount(Long money){
        if(this.account.getMoneyAmount() - money < 0)
            errors.add("Not enough money amount in account for transaction");
    }

    public boolean validate(Long money){
        validateIdNumber(account);
        validateMoneyAmount(money);
        return errors.isEmpty();
    }
}
