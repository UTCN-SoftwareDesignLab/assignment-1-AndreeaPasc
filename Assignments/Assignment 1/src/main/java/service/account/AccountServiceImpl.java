package service.account;

import model.Account;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.util.List;

public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public boolean save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void removeAll() {
        accountRepository.removeAll();
    }

    @Override
    public void update(Account oldAccount, Account newAccount) {
        accountRepository.update(oldAccount, newAccount);
    }

    @Override
    public boolean delete(Account account) {
        return accountRepository.delete(account);
    }

    @Override
    public Account findById(Account account) throws EntityNotFoundException {
        return accountRepository.findById(account);
    }

    @Override
    public void transferMoney(Long money, Account account1, Account account2) {
        account1.setMoneyAmount(account1.getMoneyAmount() - money);
        account2.setMoneyAmount(account2.getMoneyAmount() + money);
    }
}
