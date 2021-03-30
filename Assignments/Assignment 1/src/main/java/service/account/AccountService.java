package service.account;

import model.Account;
import model.Bill;
import model.validation.Notification;
import repository.EntityNotFoundException;

public interface AccountService {

    Notification<Boolean> findAll();

    Notification<Boolean> save(Account account);

    Notification<Boolean> removeAll();

    void update(Account oldAccount, Account newAccount);

    void delete(Account account);

    Notification<Boolean> findById(Long id) throws EntityNotFoundException;

    Notification<Boolean> transferMoney(Double money, Account account1, Account account2);

    Notification<Boolean> payBill(Double billAmount, Account account);

    Account findByIdNumber(Long idNumber) throws EntityNotFoundException;
}
