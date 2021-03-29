package controller;

import model.Account;
import model.Bill;
import model.builder.AccountBuilder;
import model.builder.BillBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.account.AccountService;
import view.AccountView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

public class AccountController {
    private AccountView accountView;
    private AccountService accountService;

    public AccountController(AccountView accountView, AccountService accountService) {
        this.accountView = accountView;
        this.accountService = accountService;

        accountView.setSaveAccountButtonListener(new SetSaveAccountButtonListener());
        accountView.setRemoveAccountButtonListener(new DeleteAccountButtonListener());
        accountView.setUpdateAccountButtonListener(new UpdateAccountButtonListener());
        accountView.setFindByIdAccountButtonListener(new FindByIdAccountButtonListener());
        accountView.setFindAllAccountButtonListener(new FindAllAccountButtonListener());
        accountView.setRemoveAllAccountButtonListener(new RemoveAllAccountButtonListener());
        accountView.setTransferMoneyButtonListener(new TransferMoneyButtonListener());
        accountView.setPayBillButtonListener(new PayBillButtonListener());
    }

    public class SetSaveAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Account account = null;
            try {
                account = createAccount();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }

            Notification<Boolean> clientNotification = accountService.save(account);
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(accountView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Saved account successfully!");
            }
        }
    }

    public class DeleteAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Account account = null;
            try {
                account = createAccount();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            List<Account> accounts = null;
            accounts.add(account);
            accountService.delete(account);

            if(!accounts.isEmpty()){
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Could not delete account!!");
            } else {
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Deleted account successfully!");
            }
        }
    }

    public class UpdateAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Account newAccount = null;
            try {
                newAccount = createAccount();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            Notification<Boolean> accountNotification = null;
            try {
                accountNotification = accountService.findById(accountView.getId());
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }
            if(accountNotification.hasErrors()){
                JOptionPane.showMessageDialog(accountView.getContentPane(), accountNotification.getFormattedErrors());
            } else {
                Account oldAccount = new AccountBuilder().setId(accountView.getId()).build();
                accountService.update(oldAccount, newAccount);
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Updated client successfully!");
            }
        }
    }

    public class FindByIdAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Account account = null;
            Notification<Boolean> accountNotification = null;
            try {
                account = createAccount();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            try {
                accountNotification = accountService.findById(account.getId());
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }
            if(accountNotification.hasErrors()){
                JOptionPane.showMessageDialog(accountView.getContentPane(), accountNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Found id account successfully!");
            }
        }
    }

    public class FindAllAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> accountNotification = accountService.findAll();
            if(accountNotification.hasErrors()){
                JOptionPane.showMessageDialog(accountView.getContentPane(), accountNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Found all accounts successfully!");
            }
        }
    }

    public class RemoveAllAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> accountNotification = accountService.removeAll();
            if(accountNotification.hasErrors()){
                JOptionPane.showMessageDialog(accountView.getContentPane(), accountNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Removed all accounts successfully!");
            }
        }
    }

    public class TransferMoneyButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Account account = null;
            try {
                account = createAccount();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            Account account2 = null;
            try {
                account2 = createAccount();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }

            Double money = accountView.getTransferMoney();
            Notification<Boolean> accountNotification = accountService.transferMoney(money, account, account2);
            if(accountNotification.hasErrors()){
                JOptionPane.showMessageDialog(accountView.getContentPane(), accountNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Transferred money successfully!");
            }
        }
    }

    public class PayBillButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Account account = null;
            try {
                account = createAccount();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }

            Double billAmount = accountView.getBillAMount();
            Notification<Boolean> accountNotification = accountService.payBill(billAmount, account);
            if(accountNotification.hasErrors()){
                JOptionPane.showMessageDialog(accountView.getContentPane(), accountNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Paid bill successfully!");
            }
        }
    }

    private Account createAccount() throws ParseException {

        Account account = new AccountBuilder()
                .setId(accountView.getId())
                .setClientID(accountView.getClientId())
                .setCreationDate(accountView.getCreationDate())
                .setType(accountView.getAccountType())
                .setMoneyAmount(accountView.getMoneyAmount())
                .build();

        return account;
    }
}
