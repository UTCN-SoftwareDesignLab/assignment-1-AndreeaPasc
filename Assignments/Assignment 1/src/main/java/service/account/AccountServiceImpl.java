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
        List<Account> accounts = accountRepository.findAll();
        Notification<Boolean> accountNotification = new Notification<>();
        if(!accounts.isEmpty()){
            accountNotification.setResult(Boolean.TRUE);
        }else{
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
    public void delete(Account account) {
        accountRepository.delete(account);
    }

    @Override
    public Notification<Boolean> findById(Long id) throws EntityNotFoundException {
        Account account = accountRepository.findById(id);
        Notification<Boolean> accountNotification = new Notification<>();
        List<Account> accounts = null;
        accounts.add(account);
        if(!accounts.isEmpty()){
            accountNotification.setResult(Boolean.TRUE);
        }else{
            accountNotification.setResult(Boolean.FALSE);
        }
        return accountNotification;
    }

    @Override
    public Notification<Boolean> transferMoney(Long money, Account account1, Account account2) {
        Long oldMoneyAcc1 = account1.getMoneyAmount();
        Long oldMoneyAcc2 = account2.getMoneyAmount();

        account1.setMoneyAmount(account1.getMoneyAmount() - money);
        account2.setMoneyAmount(account2.getMoneyAmount() + money);
        accountRepository.save(account1);
        accountRepository.save(account2);

        Notification<Boolean> accountNotification = new Notification<>();
        if((account1.getMoneyAmount() == oldMoneyAcc1 - money) && (account2.getMoneyAmount() == oldMoneyAcc2 + money))
            accountNotification.setResult(Boolean.TRUE);
        else accountNotification.setResult(Boolean.FALSE);
        return accountNotification;
    }
}
