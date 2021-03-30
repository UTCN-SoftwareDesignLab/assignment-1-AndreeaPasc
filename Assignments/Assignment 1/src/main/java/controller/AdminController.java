package controller;

import model.ActivityLog;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.account.AccountService;
import service.activity.ActivityLogService;
import service.user.UserService;
import view.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminController {

    private final AdminView adminView;
    private final UserService userService;
    private final ActivityLogService activityLogService;

    public AdminController(AdminView adminView, UserService userService, ActivityLogService activityLogService) {
        this.adminView = adminView;
        this.userService = userService;
        this.activityLogService = activityLogService;

        adminView.setFindAllUserButtonListener(new FindAllUserButtonListener());
        adminView.setSaveUserButtonListener(new SaveUserButtonListener());
        adminView.setRemoveUserButtonListener(new RemoveUserButtonListener());
        adminView.setUpdateUserButtonListener(new UpdateUserButtonListener());
        adminView.setRemoveAllUserButtonListener(new RemoveAllUserButtonListener());
        adminView.setFindByUsernameAndPasswordButtonListener(new FindByUsernameAndPasswordButtonListener());
        adminView.setActivityLogButtonListener(new ActivityLogButtonListener());
    }

    private class ActivityLogButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> activityLogNotification = null;
            try {
                activityLogNotification = activityLogService.findAll();
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }
            try {
                activityLogNotification = activityLogService.findAll();
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }
            if(activityLogNotification.hasErrors()){
                JOptionPane.showMessageDialog(adminView.getContentPane(), activityLogNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Found all activities successfully!");
            }
        }
    }

    private class FindAllUserButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> clientNotification = userService.findAll();
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(adminView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Found all users successfully!");
            }
        }
    }

    public class SaveUserButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            User user = createUser();

            Notification<Boolean> userNotification = userService.save(user);
            if(userNotification.hasErrors()){
                JOptionPane.showMessageDialog(adminView.getContentPane(), userNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Saved client successfully!");
            }
        }
    }

    public class RemoveUserButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            User user = null;
            try {
                user = userService.findByUsername(adminView.getUsername());
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

            if(user == null){
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Could find the user to delete");
            } else {
                userService.delete(user);
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Removed user successfully!");
            }
        }
    }

    public class UpdateUserButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            User newUser = createUser();

            if(newUser != null){
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Could not find user to update!");
            } else {
                User oldUser = null;
                try {
                    oldUser = userService.findByUsername(adminView.getUsername());
                } catch (EntityNotFoundException entityNotFoundException) {
                    entityNotFoundException.printStackTrace();
                }
                userService.update(oldUser, newUser);
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Updated user successfully!");
            }
        }
    }

    public class RemoveAllUserButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> clientNotification = userService.removeAll();
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(adminView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Removed all users successfully!");
            }
        }
    }

    public class FindByUsernameAndPasswordButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            User user = createUser();

            Notification<User> userNotification = null;
            userNotification = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
            if(userNotification.hasErrors()){
                JOptionPane.showMessageDialog(adminView.getContentPane(), userNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Found user successfully!");
            }
        }
    }

    private User createUser(){
        return new UserBuilder()
                .setUsername(adminView.getUsername())
                .setPassword(adminView.getPassword())
                //.setRoles(adminView.getRoles())
                .build();
    }
}
