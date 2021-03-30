package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class LoginView extends JFrame {

    private JTextField tfUsername;
    private JTextField tfPassword;
    private JTextField tfAdminOrUser;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(new JLabel("Username"));
        add(tfUsername);
        add(new JLabel("Password"));
        add(tfPassword);
        add(new JLabel("For registration only, choose Admin or Employee (administrator/employee)"));
        add(tfAdminOrUser);
        add(btnLogin);
        add(btnRegister);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {

        tfUsername = new JTextField();
        tfPassword = new JTextField();
        tfAdminOrUser = new JTextField();
        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public String getAdminOrUser(){ return tfAdminOrUser.getText(); }

    public void setLoginButtonListener(ActionListener loginButtonListener) {
        btnLogin.addActionListener(loginButtonListener);
    }

    public void setRegisterButtonListener(ActionListener registerButtonListener) {
        btnRegister.addActionListener(registerButtonListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }

}
