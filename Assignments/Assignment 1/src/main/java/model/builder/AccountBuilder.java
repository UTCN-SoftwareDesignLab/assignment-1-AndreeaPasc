package model.builder;

import model.Account;
import model.User;

import java.util.Date;

public class AccountBuilder {

    private Account account;

    public AccountBuilder(){
        account = new Account();
    }

    public AccountBuilder setId(Long id){
        account.setId(id);
        return this;
    }

    public AccountBuilder setClientID(Long clientID){
        account.setClientId(clientID);
        return this;
    }

    public AccountBuilder setIdentificationNumber(Long idNumber){
        account.setIdentificationNumber(idNumber);
        return this;
    }

    public AccountBuilder setType(String type){
        account.setType(type);
        return this;
    }

    public AccountBuilder setMoneyAmount(Long moneyAmount){
        account.setMoneyAmount(moneyAmount);
        return this;
    }

    public AccountBuilder setCreationDate(Date creationDate){
        account.setCreationDate(creationDate);
        return this;
    }
    public Account build() {
        return account;
    }
}
