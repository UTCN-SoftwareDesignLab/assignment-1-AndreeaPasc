package repository.account;

import model.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    boolean save(Account account);

    void removeAll();

    public boolean update(Account account);

    public boolean delete(Account account);

    public Account findById(Account account);
}
