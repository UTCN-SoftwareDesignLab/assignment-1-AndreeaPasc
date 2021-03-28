package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.swing.BoxLayout.Y_AXIS;

public class AccountView extends JFrame {
    private JTextField tfAccountId;
    private JTextField tfAccountClientId;
    private JTextField tfAccountIdCardNumber;
    private JTextField tfAccountType;
    private JTextField tfAccountMoneyAmount;
    private JTextField tfAccountCreationDate;
    private JTextField tfAccountTransferMoney;

    private JButton btnFindByIdAccount;
    private JButton btnDeleteAccount;
    private JButton btnRemoveAllAccount;
    private JButton btnUpdateAccount;
    private JButton btnSaveAccount;
    private JButton btnFindAllAccount;
    private JButton btnTransferMoney;

    public AccountView() throws HeadlessException{
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(tfAccountId);
        add(tfAccountClientId);
        add(tfAccountIdCardNumber);
        add(tfAccountType);
        add(tfAccountMoneyAmount);
        add(tfAccountCreationDate);
        add(tfAccountTransferMoney);

        add(btnFindByIdAccount);
        add(btnDeleteAccount);
        add(btnRemoveAllAccount);
        add(btnUpdateAccount);
        add(btnSaveAccount);
        add(btnFindAllAccount);
        add(btnTransferMoney);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        btnSaveAccount = new JButton("Save new account");
        btnDeleteAccount = new JButton("Delete account");
        btnUpdateAccount = new JButton("Update existing account");
        btnFindAllAccount = new JButton("Show all accounts");
        btnFindByIdAccount = new JButton("Find account by Id");
        btnRemoveAllAccount = new JButton("Delete all accounts");
        btnTransferMoney = new JButton("Transfer money between 2 accounts");

        tfAccountClientId = new JTextField();
        tfAccountId = new JTextField();
        tfAccountCreationDate = new JTextField();
        tfAccountType = new JTextField();
        tfAccountIdCardNumber = new JTextField();
        tfAccountMoneyAmount = new JTextField();
        tfAccountTransferMoney = new JTextField();
    }

    public Long getId(){
        return Long.parseLong(tfAccountId.getText());
    }

    public Long getTransferMoney(){
        return Long.parseLong(tfAccountTransferMoney.getText());
    }

    public Long getClientId(){
        return Long.parseLong(tfAccountClientId.getText());
    }

    public Date getCreationDate() throws ParseException {
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(tfAccountCreationDate.getText());
        return date1;
    }

    public String getAccountType(){
        return tfAccountType.getText();
    }

    public Long getMoneyAmount(){
        return Long.parseLong(tfAccountMoneyAmount.getText());
    }

    public void setSaveAccountButtonListener(ActionListener saveAccountButtonListener){
        btnSaveAccount.addActionListener(saveAccountButtonListener);
    }

    public void setRemoveAccountButtonListener(ActionListener removeAccountButtonListener){
        btnDeleteAccount.addActionListener(removeAccountButtonListener);
    }

    public void setUpdateAccountButtonListener(ActionListener updateAccountButtonListener){
        btnUpdateAccount.addActionListener(updateAccountButtonListener);
    }

    public void setFindByIdAccountButtonListener(ActionListener findByIdAccountButtonListener){
        btnFindByIdAccount.addActionListener(findByIdAccountButtonListener);
    }

    public void setFindAllAccountButtonListener(ActionListener findAllAccountButtonListener){
        btnFindAllAccount.addActionListener(findAllAccountButtonListener);
    }

    public void setRemoveAllAccountButtonListener(ActionListener removeAllAccountButtonListener){
        btnRemoveAllAccount.addActionListener(removeAllAccountButtonListener);
    }

    public void setTransferMoneyButtonListener(ActionListener transferMoneyButtonListener){
        btnTransferMoney.addActionListener(transferMoneyButtonListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }
}
