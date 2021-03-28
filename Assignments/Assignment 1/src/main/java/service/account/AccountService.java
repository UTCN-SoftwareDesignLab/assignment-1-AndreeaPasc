package service.account;

import model.Account;
import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountService {

    Notification<Boolean> findAll();

    Notification<Boolean> save(Account account);

    Notification<Boolean> removeAll();

    void update(Account oldAccount, Account newAccount);

    Notification<Boolean> delete(Account account);

    Notification<Boolean> findById(Account account) throws EntityNotFoundException;

    Notification<Account> transferMoney(Long money, Account account1, Account account2);

}
