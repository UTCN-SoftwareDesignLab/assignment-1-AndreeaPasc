package view;

import controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class ClientView extends JFrame{
    private JTextField tfClientId;
    private JTextField tfClientName;
    private JTextField tfClientIdCardNumber;
    private JTextField tfClientPNC;
    private JTextField tfClientAddress;
    private JTextField tfClientPhoneNumber;

    private JButton btnFindByIdClient;
    private JButton btnDeleteClient;
    private JButton btnRemoveAllClient;
    private JButton btnUpdateClient;
    private JButton btnSaveClient;
    private JButton btnFindAllClient;

    public ClientView() throws HeadlessException{
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(tfClientId);
        add(tfClientName);
        add(tfClientAddress);
        add(tfClientPNC);
        add(tfClientIdCardNumber);
        add(tfClientPhoneNumber);
        add(tfClientAddress);

        add(btnDeleteClient);
        add(btnSaveClient);
        add(btnFindByIdClient);
        add(btnUpdateClient);
        add(btnRemoveAllClient);
        add(btnFindAllClient);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        btnSaveClient = new JButton("Save new client");
        btnDeleteClient = new JButton("Delete client");
        btnUpdateClient = new JButton("Update existing client");
        btnFindAllClient = new JButton("Show all clients");
        btnFindByIdClient = new JButton("Find client by Id");
        btnRemoveAllClient = new JButton("Delete all clients");

        tfClientPhoneNumber = new JTextField();
        tfClientId = new JTextField();
        tfClientName = new JTextField();
        tfClientIdCardNumber = new JTextField();
        tfClientPNC = new JTextField();
        tfClientAddress = new JTextField();
    }

    public Long getId(){
        return Long.parseLong(tfClientId.getText());
    }

    public String getAddress(){
        return tfClientAddress.getText();
    }

    public Long getPhoneNumber(){
        return Long.parseLong(tfClientPhoneNumber.getText());
    }

    public String getName(){
        return tfClientName.getText();
    }

    public Long getIdentificationNumber(){
        return Long.parseLong(tfClientIdCardNumber.getText());
    }

    public Long getPersonalNumericalCode(){
        return Long.parseLong(tfClientPNC.getText());
    }

    public void setSaveClientButtonListener(ActionListener saveClientButtonListener) {
        btnSaveClient.addActionListener(saveClientButtonListener);
    }

    public void setRemoveClientButtonListener(ActionListener removeClientButtonListener) {
        btnDeleteClient.addActionListener(removeClientButtonListener);
    }

    public void setFindAllClientButtonListener(ActionListener findAllClientButtonListener) {
        btnFindAllClient.addActionListener(findAllClientButtonListener);
    }

    public void setUpdateClientButtonListener(ActionListener updateClientButtonListener) {
        btnUpdateClient.addActionListener(updateClientButtonListener);
    }

    public void setFindByIdClientButtonListener(ActionListener findByIdClientButtonListener){
        btnFindByIdClient.addActionListener(findByIdClientButtonListener);
    }

    public void setRemoveAllClientButtonListener(ActionListener removeAllClientButtonListener){
        btnRemoveAllClient.addActionListener(removeAllClientButtonListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }
}
