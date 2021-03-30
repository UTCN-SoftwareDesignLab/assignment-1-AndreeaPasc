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
    private JTextField tfFromAccountId;
    private JTextField tfToAccountId;
    private JTextField tfAccountClientId;
    private JTextField tfAccountIdCardNumber;
    private JTextField tfAccountType;
    private JTextField tfAccountMoneyAmount;
    private JTextField tfAccountCreationDate;
    private JTextField tfAccountTransferMoney;
    private JTextField tfBillAmount;
    private JTextField tfBillType;

    private JButton btnFindByIdAccount;
    private JButton btnDeleteAccount;
    private JButton btnRemoveAllAccount;
    private JButton btnUpdateAccount;
    private JButton btnSaveAccount;
    private JButton btnFindAllAccount;
    private JButton btnTransferMoney;
    private JButton btnPayBIll;

    public AccountView() throws HeadlessException{
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(new JLabel("Account Id to be found"));
        add(tfAccountId);
        add(new JLabel("Account Client Id"));
        add(tfAccountClientId);
        add(new JLabel("Account Identification Number (6 digits)"));
        add(tfAccountIdCardNumber);
        add(new JLabel("Account Type"));
        add(tfAccountType);
        add(new JLabel("Account Money Amount"));
        add(tfAccountMoneyAmount);
        add(new JLabel("Account Creation Date (dd/mm/yyyy)"));
        add(tfAccountCreationDate);
        add(new JLabel("Transfer from account idNumber"));
        add(tfFromAccountId);
        add(new JLabel("Transfer money to account idNumber"));
        add(tfToAccountId);
        add(new JLabel("Transfer money"));
        add(tfAccountTransferMoney);
        add(new JLabel("Bill amount"));
        add(tfBillAmount);
        add(new JLabel("Bill type"));
        add(tfBillType);

        add(btnFindByIdAccount);
        add(btnDeleteAccount);
        add(btnRemoveAllAccount);
        add(btnUpdateAccount);
        add(btnSaveAccount);
        add(btnFindAllAccount);
        add(btnTransferMoney);
        add(btnPayBIll);

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
        btnPayBIll = new JButton("Pay utility bills");

        tfAccountClientId = new JTextField();
        tfAccountId = new JTextField();
        tfAccountCreationDate = new JTextField();
        tfAccountType = new JTextField();
        tfAccountIdCardNumber = new JTextField();
        tfAccountMoneyAmount = new JTextField();
        tfFromAccountId = new JTextField();
        tfToAccountId = new JTextField();
        tfAccountTransferMoney = new JTextField();
        tfBillAmount = new JTextField();
        tfBillType = new JTextField();
    }

    public Long getId(){
        return Long.parseLong(tfAccountId.getText());
    }

    public Long getFromAccountId(){
        return Long.parseLong(tfFromAccountId.getText());
    }

    public Long getToAccountId(){
        return Long.parseLong(tfToAccountId.getText());
    }

    public Long getIdNumber(){
        return Long.parseLong(tfAccountIdCardNumber.getText());
    }

    public String getBillType(){
        return tfBillType.getText();
    }

    public Double getBillAMount(){
        return Double.parseDouble(tfBillAmount.getText());
    }

    public Double getTransferMoney(){
        return Double.parseDouble(tfAccountTransferMoney.getText());
    }

    public Long getClientId(){
        return Long.parseLong(tfAccountClientId.getText());
    }

    public Date getCreationDate() throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(tfAccountCreationDate.getText());
    }

    public String getAccountType(){
        return tfAccountType.getText();
    }

    public Double getMoneyAmount(){
        return Double.parseDouble(tfAccountMoneyAmount.getText());
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

    public void setPayBillButtonListener(ActionListener payBillButtonListener){
        btnPayBIll.addActionListener(payBillButtonListener);
    }
    public void setVisible() {
        this.setVisible(true);
    }
}
