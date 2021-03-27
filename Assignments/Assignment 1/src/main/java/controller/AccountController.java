package controller;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.account.AccountService;
import view.AccountView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountController {
    private AccountView accountView;
    private AccountService accountService;

    public AccountController(AccountView accountView, AccountService accountService) {
        this.accountView = accountView;
        this.accountService = accountService;

        accountView.setSaveAccountButtonListener(new AccountController.SetSaveAccountButtonListener);
        accountView.setRemoveAccountButtonListeer(new AccountController.DeleteAccountButtonListener);
        accountView.setUpdateAccountButtonListener(new AccountController.UpdateAccountButtonListener);
        accountView.setFindByIdAccountButtonListener(new AccountController.FindByIdAccountButtonListener);
        accountView.setFindAllAccountButtonListener(new AccountController.FindAllAccountButtonListener);
        accountView.setRemoveAllAccountButtonListener(new AccountController.RemoveAllAccountButtonListener);
        accountView.setTransferMoneyButtonListener(new AccountController.TransferMoneyButtonListener);
    }


    public class SetSaveAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Account account = createAccount();

            Notification<Client> clientNotification = accountService.save(account);
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
            Account account = createAccount();

            Notification<Account> accountNotification = accountService.delete(account);
            if(accountNotification.hasErrors()){
                JOptionPane.showMessageDialog(accountView.getContentPane(), accountNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Deleted account successfully!");
            }
        }
    }

    public class UpdateAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Account newAccount = createAccount();

            Notification<Account> accountNotification = null;
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
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Updated account successfully!");
            }
        }
    }

    public class FindByIdAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Account account = createAccount();

            Notification<Account> accountNotification = null;
            try {
                accountNotification = accountService.findById(account);
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
            Notification<Account> accountNotification = accountService.findAll();
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
            Notification<Account> accountNotification = accountService.removeAll();
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
            Account account = createAccount();
            Account account2 = createAccount();

            Long money = accountView.getMoneyAmount();
            Notification<Account> accountNotification = accountService.transferMoney(money, account, account2);
            if(accountNotification.hasErrors()){
                JOptionPane.showMessageDialog(accountView.getContentPane(), accountNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Transferred money successfully!");
            }
        }
    }

    private Account createAccount(){

        Account account = new AccountBuilder()
                .setClientID(accountView.getCLientId())
                .setCreationDate(accountView.getCreationDate())
                .setPersonalNumericalCode(accountView.getPersonalNumericalCode())
                .setType(accountView.getType())
                .setMoneyAmount(accountView.getMoneyAmount)
                .build();

        return account;
    }
}
