package service.account;

import model.Account;
import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;

public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Notification<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Notification<Client> save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Notification<Account> removeAll() {
        accountRepository.removeAll();
        return null;
    }

    @Override
    public void update(Account oldAccount, Account newAccount) {
        accountRepository.update(oldAccount, newAccount);
    }

    @Override
    public Notification<Account> delete(Account account) {
        return accountRepository.delete(account);
    }

    @Override
    public Notification<Account> findById(Account account) throws EntityNotFoundException {
        return accountRepository.findById(account);
    }

    @Override
    public Notification<Account> transferMoney(Long money, Account account1, Account account2) {
        account1.setMoneyAmount(account1.getMoneyAmount() - money);
        account2.setMoneyAmount(account2.getMoneyAmount() + money);
        return null;
    }
}
