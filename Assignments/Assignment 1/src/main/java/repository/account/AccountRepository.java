package repository.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    boolean save(Account account);

    void removeAll();

    boolean update(Account account);

    boolean delete(Account account);

    Account findById(Account account) throws EntityNotFoundException;
}
