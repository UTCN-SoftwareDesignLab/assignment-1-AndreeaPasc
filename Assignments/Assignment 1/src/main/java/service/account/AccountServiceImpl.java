package service.account;

import model.Account;
import model.validation.AccountValidator;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;

import java.util.List;

public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Notification<Boolean> findAll() {
        AccountValidator accountValidator = null;
        boolean valid = false;
        List<Account> accounts = accountRepository.findAll();
        for(Account account: accounts) {
            accountValidator = new AccountValidator(account);
            valid = accountValidator.validate(account.getMoneyAmount());
        }
        Notification<Boolean> accountNotification = new Notification<>();
        if(valid){
            accountNotification.setResult(!accountRepository.findAll().isEmpty());
        }else{
            assert accountValidator != null;
            accountValidator.getErrors().forEach(accountNotification::addError);
            accountNotification.setResult(Boolean.FALSE);
        }
        return accountNotification;
    }

    @Override
    public Notification<Boolean> save(Account account) {
        AccountValidator accountValidator = new AccountValidator(account);
        boolean valid = accountValidator.validate(account.getMoneyAmount());
        Notification<Boolean> accountNotification = new Notification<>();
        if(valid){
            accountNotification.setResult(accountRepository.save(account));
        }else{
            accountValidator.getErrors().forEach(accountNotification::addError);
            accountNotification.setResult(Boolean.FALSE);
        }
        return accountNotification;
    }

    @Override
    public Notification<Boolean> removeAll() {
        accountRepository.removeAll();
        Notification<Boolean> accountNotification = new Notification<>();
        List<Account> accounts = accountRepository.findAll();
        if(accounts.isEmpty()){
            accountNotification.setResult(Boolean.TRUE);
        }else{
            accountNotification.setResult(Boolean.FALSE);
        }
        return accountNotification;
    }

    @Override
    public void update(Account oldAccount, Account newAccount) {
        accountRepository.update(oldAccount, newAccount);
    }

    @Override
    public Notification<Boolean> delete(Account account) {
        AccountValidator accountValidator = new AccountValidator(account);
        boolean valid = accountValidator.validate(account.getMoneyAmount());
        Notification<Boolean> accountNotification = new Notification<>();
        if(valid){
            accountNotification.setResult(accountRepository.delete(account));
        }else{
            accountValidator.getErrors().forEach(accountNotification::addError);
            accountNotification.setResult(Boolean.FALSE);
        }
        return accountNotification;
    }

    @Override
    public Notification<Boolean> findById(Account account) throws EntityNotFoundException {
        AccountValidator accountValidator = new AccountValidator(account);
        boolean valid = accountValidator.validate(account.getMoneyAmount());
        Notification<Boolean> accountNotification = new Notification<>();
        List<Account> accounts = null;
        if(valid){
            accounts.add(accountRepository.findById(account));
            accountNotification.setResult(!accounts.isEmpty());
        }else{
            accountValidator.getErrors().forEach(accountNotification::addError);
            accountNotification.setResult(Boolean.FALSE);
        }
        return accountNotification;
    }

    @Override
    public Notification<Account> transferMoney(Long money, Account account1, Account account2) {
        account1.setMoneyAmount(account1.getMoneyAmount() - money);
        account2.setMoneyAmount(account2.getMoneyAmount() + money);
        return null;
    }
}
