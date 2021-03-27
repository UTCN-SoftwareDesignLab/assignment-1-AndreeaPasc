package service.account;

import model.Account;
import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;

public interface AccountService {

    Notification<Account> findAll();

    Notification<Client> save(Account account);

    Notification<Account> removeAll();

    void update(Account oldAccount, Account newAccount);

    Notification<Account> delete(Account account);

    Notification<Account> findById(Account account) throws EntityNotFoundException;

    Notification<Account> transferMoney(Long money, Account account1, Account account2);

}
