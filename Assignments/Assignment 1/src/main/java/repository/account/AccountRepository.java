package repository.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    boolean save(Account account);

    void removeAll();

    void update(Account oldAccount, Account newAccount);

    boolean delete(Account account);

    Account findById(Long id) throws EntityNotFoundException;
}
