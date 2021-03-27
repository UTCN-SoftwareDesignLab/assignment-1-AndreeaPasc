package controller;

import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import view.UserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminController {

    private UserView userView;
    private UserService userService;

    public AdminController(UserView userView, UserService userService) {
        this.userView = userView;
        this.userService = userService;

        userView.setFindAllUserButtonListener(new AdminController.FindAllUserButtonListener);
        userView.setSaveUserButtonListener(new AdminController.SaveUserButtonListener);
        userView.setRemoveUserButtonListener(new AdminController.RemoveUserButtonListener);
        userView.setUpdateUserButtonListener(new AdminController.UpdateUserButtonListener);
        userView.setRemoveAllUserButtonListener(new AdminController.RemoveAllUserButtonListener);
        userView.setFindByUsernameAndPasswordButtonListener(new AdminController.FindByUsernameAndPasswordButtonListener);
    }

    private class FindAllUserButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<User> clientNotification = userService.findAll();
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(userView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(userView.getContentPane(), "Found all users successfully!");
            }
        }
    }

    public class SaveUserButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            User user = createUser();

            Notification<User> userNotification = userService.save(user);
            if(userNotification.hasErrors()){
                JOptionPane.showMessageDialog(userView.getContentPane(), userNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(userView.getContentPane(), "Saved client successfully!");
            }
        }
    }

    public class RemoveUserButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            User user = createUser();

            Notification<User> userNotification = userService.delete(user);
            if(userNotification.hasErrors()){
                JOptionPane.showMessageDialog(userService.getContentPane(), userNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(userService.getContentPane(), "Removed user successfully!");
            }
        }
    }

    public class UpdateUserButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            User newUser = createUser();
            Notification<User> userNotification = null;
            try {
                userNotification = userService.findById(userView.getId());
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }
            if(userNotification.hasErrors()){
                JOptionPane.showMessageDialog(userView.getContentPane(), userNotification.getFormattedErrors());
            } else {
                User oldUser = new UserBuilder().setId(userView.getId()).build();
                userService.update(oldUser, newUser);
                JOptionPane.showMessageDialog(userView.getContentPane(), "Updated user successfully!");
            }
        }
    }

    public class RemoveAllUserButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<User> clientNotification = userService.removeALl();
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(userView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(userView.getContentPane(), "Removed all users successfully!");
            }
        }
    }

    public class FindByUsernameAndPasswordButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            User user = createUser();

            Notification<User> userNotification = null;
            try {
                userNotification = userService.findByUsernameAndPassword(user);
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }
            if(userNotification.hasErrors()){
                JOptionPane.showMessageDialog(userView.getContentPane(), userNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(userView.getContentPane(), "Found user successfully!");
            }
        }
    }

    private User createUser(){
        User user = new UserBuilder()
                .setUsername(userView.getUsername())
                .setPassword(userView.getPassword())
                .build();
        return user;
    }
}
