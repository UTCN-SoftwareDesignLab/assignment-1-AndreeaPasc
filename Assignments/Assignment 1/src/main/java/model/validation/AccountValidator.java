package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountValidator {
    private final Account account;
    private final List<String> errors;

    final static String DATE_FORMAT = "dd/mm/yyyy hh:mm:ss";

    public AccountValidator(Account account) {
        this.account = account;
        errors = new ArrayList<>();
    }

    public List<String> getErrors() {
        return errors;
    }

    private void validateIdNumber(){
        int length = this.account.getIdentificationNumber().toString().length();
        if(length != 6)
            errors.add("Id number should have length 6");
    }

    private void validateMoneyAmount(Double money){
        if(this.account.getMoneyAmount() - money < 0)
            errors.add("Not enough money amount in account for transaction");
    }

    private void validateDate(){
        if(!this.account.getCreationDate().toString().equals(DATE_FORMAT))
            errors.add("Not the right date format");
    }

    public boolean validate(Double money){
        validateIdNumber();
        //validateDate();
        validateMoneyAmount(money);
        return errors.isEmpty();
    }
}
