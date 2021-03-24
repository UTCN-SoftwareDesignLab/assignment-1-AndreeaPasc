package service.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    boolean save(Account account);

    void removeAll();

    boolean update(Account account);

    boolean delete(Account account);

    Account findById(Account account) throws EntityNotFoundException;

    void transferMoney(Long money, Account account1, Account account2);

}
