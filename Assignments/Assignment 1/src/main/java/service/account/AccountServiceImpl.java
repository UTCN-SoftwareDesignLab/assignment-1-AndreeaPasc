package service.account;

import model.Account;
import model.Bill;
import model.builder.AccountBuilder;
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
        boolean valid = accountValidator.validate(0.0);
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
        List<Account> accounts = accountRepository.findAll();
        accounts.add(account);
        if(!accounts.isEmpty()){
            accountNotification.setResult(Boolean.TRUE);
        }else{
            accountNotification.setResult(Boolean.FALSE);
        }
        return accountNotification;
    }

    @Override
    public Notification<Boolean> transferMoney(Double money, Account account1, Account account2) {
        Double oldMoneyAcc1 = account1.getMoneyAmount();
        Double oldMoneyAcc2 = account2.getMoneyAmount();

        //account1.setMoneyAmount(account1.getMoneyAmount() - money);
        //account2.setMoneyAmount(account2.getMoneyAmount() + money);

        Account newAccount1 = new AccountBuilder()
                .setId(account1.getId())
                .setClientID(account1.getClientId())
                .setCreationDate(account1.getCreationDate())
                .setIdentificationNumber(account1.getIdentificationNumber())
                .setType(account1.getType())
                .setMoneyAmount(account1.getMoneyAmount() - money)
                .build();

        Account newAccount2 = new AccountBuilder()
                .setId(account2.getId())
                .setClientID(account2.getClientId())
                .setCreationDate(account2.getCreationDate())
                .setIdentificationNumber(account2.getIdentificationNumber())
                .setType(account2.getType())
                .setMoneyAmount(account2.getMoneyAmount() + money)
                .build();

        accountRepository.update(account1, newAccount1);
        accountRepository.update(account2, newAccount2);

        Notification<Boolean> accountNotification = new Notification<>();
        if((newAccount1.getMoneyAmount() == oldMoneyAcc1 - money) && (newAccount2.getMoneyAmount() == oldMoneyAcc2 + money))
            accountNotification.setResult(Boolean.TRUE);
        else accountNotification.setResult(Boolean.FALSE);
        return accountNotification;
    }

    @Override
    public Notification<Boolean> payBill(Double billAmount, Account account){
        Double oldMoneyAcc1 = account.getMoneyAmount();
        //account.setMoneyAmount(account.getMoneyAmount() - billAmount);
        Account newAccount = new AccountBuilder()
                .setId(account.getId())
                .setClientID(account.getClientId())
                .setCreationDate(account.getCreationDate())
                .setIdentificationNumber(account.getIdentificationNumber())
                .setType(account.getType())
                .setMoneyAmount(account.getMoneyAmount() - billAmount)
                .build();
        accountRepository.update(account, newAccount);

        Notification<Boolean> accountNotification = new Notification<>();
        if((newAccount.getMoneyAmount() == oldMoneyAcc1 - billAmount))
            accountNotification.setResult(Boolean.TRUE);
        else accountNotification.setResult(Boolean.FALSE);
        return accountNotification;
    }

    @Override
    public Account findByIdNumber(Long idNumber) throws EntityNotFoundException {
        return accountRepository.findByIdNumber(idNumber);
    }
}
