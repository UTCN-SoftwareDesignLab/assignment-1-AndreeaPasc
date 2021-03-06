package controller;

import model.ActivityLog;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.activity.ActivityLogService;
import service.user.UserService;
import sun.swing.BakedArrayList;
import view.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

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
            List<ActivityLog> activities = null;
            try {
                activities = activityLogService.findAll();
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

            Date startDate = null;
            Date endDate = null;
            try {
                startDate = adminView.getStartDate();
                endDate = adminView.getEndDate();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }

            assert activities != null;
            ArrayList<ActivityLog> activitiesOnDate = new ArrayList<>();
            for(ActivityLog ac : activities){
                if(activityLogService.checkDateRange(startDate, endDate, ac)) {
                    activitiesOnDate.add(ac);
                }
            }

            if(activitiesOnDate.isEmpty()){
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Could not find activities!");
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Found all activities successfully!");
                JOptionPane.showMessageDialog(adminView.getContentPane(), activityLogService.showActivityLog(activitiesOnDate));
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
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Saved user successfully!");
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
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Could not find the user to delete");
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
        Role role = new Role(2L, EMPLOYEE, null);
        return new UserBuilder()
                .setUsername(adminView.getUsername())
                .setPassword(adminView.getPassword())
                .setRoles(Collections.singletonList(role))
                .build();
    }
}
