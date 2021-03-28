package controller;

import model.Client;
import model.User;
import model.validation.Notification;
import service.user.AuthenticationService;
import view.AccountView;
import view.AdminView;
import view.ClientView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static database.Constants.Roles.ADMINISTRATOR;

public class LoginController {
    private final LoginView loginView;
    private final AdminView adminView;
    private final ClientView clientView;
    private final AccountView accountView;
    private final AuthenticationService authenticationService;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, AdminView adminView, AccountView accountView, ClientView clientView) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.adminView = adminView;
        this.accountView = accountView;
        this.clientView = clientView;
        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                User currentUser = loginNotification.getResult();
                if(currentUser.getRoles().get(0).equals(ADMINISTRATOR)){
                    adminView.setVisible();
                }else{
                    accountView.setVisible();
                    clientView.setVisible();
                }
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                }
            }
        }
    }
}
