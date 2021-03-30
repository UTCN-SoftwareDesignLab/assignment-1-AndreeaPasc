package controller;

import model.Account;
import model.builder.AccountBuilder;
import model.builder.ActivityLogBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.account.AccountService;
import service.activity.ActivityLogService;
import service.user.UserService;
import view.AccountView;
import view.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

public class AccountController {
    private final AccountView accountView;
    private final AccountService accountService;

    private final AdminView adminView;
    private final UserService userService;
    private final ActivityLogService activityLogService;

    public AccountController(AccountView accountView, AccountService accountService, AdminView adminView, UserService userService, ActivityLogService activityLogService) {
        this.accountView = accountView;
        this.accountService = accountService;
        this.adminView = adminView;
        this.userService = userService;
        this.activityLogService = activityLogService;

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
                try {
                    activityLogService.save(new ActivityLogBuilder().setActivity("new account saved by user").setDate(new Date()).setUser(userService.findByUsername(adminView.getUsername())).build());
                } catch (EntityNotFoundException entityNotFoundException) {
                    entityNotFoundException.printStackTrace();
                }

            }
        }
    }

    public class DeleteAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Account account = null;
            try {
                account = accountService.findByIdNumber(accountView.getIdNumber());
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

            if(account == null){
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Could find account to delete!");
            } else {
                accountService.delete(account);
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Deleted account successfully!");
                try {
                    activityLogService.save(new ActivityLogBuilder().setActivity("account deleted by user").setDate(new Date()).setUser(userService.findByUsername(adminView.getUsername())).build());
                } catch (EntityNotFoundException entityNotFoundException) {
                    entityNotFoundException.printStackTrace();
                }

            }
        }
    }

    public class UpdateAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Account newAccount = null;
            try {
                newAccount = createAccount();
            } catch (ParseException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

            if(newAccount == null){
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Could not find account to update!");
            } else {
                Account oldAccount = null;
                try {
                    oldAccount = accountService.findByIdNumber(accountView.getIdNumber());
                } catch (EntityNotFoundException entityNotFoundException) {
                    entityNotFoundException.printStackTrace();
                }
                accountService.update(oldAccount, newAccount);
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Updated account successfully!");
                try {
                    activityLogService.save(new ActivityLogBuilder().setActivity("account updated by user").setDate(new Date()).setUser(userService.findByUsername(adminView.getUsername())).build());
                } catch (EntityNotFoundException entityNotFoundException) {
                    entityNotFoundException.printStackTrace();
                }

            }
        }
    }

    public class FindByIdAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> accountNotification = null;
            try {
                accountNotification = accountService.findById(accountView.getId());
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
                try {
                    activityLogService.save(new ActivityLogBuilder().setActivity("all accounts removed by user").setDate(new Date()).setUser(userService.findByUsername(adminView.getUsername())).build());
                } catch (EntityNotFoundException entityNotFoundException) {
                    entityNotFoundException.printStackTrace();
                }
            }
        }
    }

    public class TransferMoneyButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Account account = null;
            try {
                account = accountService.findByIdNumber(accountView.getIdNumber());
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

            Account account2 = null;
            try {
                account2 = accountService.findByIdNumber(accountView.getIdNumber());
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
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

        return new AccountBuilder()
                .setClientID(accountView.getClientId())
                .setCreationDate(accountView.getCreationDate())
                .setType(accountView.getAccountType())
                .setIdentificationNumber(accountView.getIdNumber())
                .setMoneyAmount(accountView.getMoneyAmount())
                .build();
    }
}
