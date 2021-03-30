package controller;

import model.ActivityLog;
import model.Client;
import model.builder.ActivityLogBuilder;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.activity.ActivityLogService;
import service.client.ClientService;
import service.user.UserService;
import view.AdminView;
import view.ClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ClientController {
    private final ClientView clientView;
    private final AdminView adminView;
    private final UserService userService;
    private final ClientService clientService;
    private final ActivityLogService activityLogService;

    public ClientController(ClientView clientView, UserService userService, AdminView adminView, ClientService clientService, ActivityLogService activityLogService) {
        this.clientView = clientView;
        this.clientService = clientService;
        this.activityLogService = activityLogService;
        this.adminView = adminView;
        this.userService = userService;

        clientView.setSaveClientButtonListener(new SetSaveClientButtonListener());
        clientView.setRemoveClientButtonListener(new DeleteClientButtonListener());
        clientView.setUpdateClientButtonListener(new UpdateClientButtonListener());
        clientView.setFindByIdClientButtonListener(new FindByIdClientButtonListener());
        clientView.setFindAllClientButtonListener(new FindAllClientButtonListener());
        clientView.setRemoveAllClientButtonListener(new RemoveAllClientButtonListener());
    }

    private class SetSaveClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Client client = null;
            try {
                client = createClient();
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

            Notification<Boolean> clientNotification = clientService.save(client);
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(clientView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Saved client successfully!");
                try {
                    activityLogService.save(new ActivityLogBuilder().setActivity("new client saved by user").setDate(new Date()).setUser(userService.findByUsername(adminView.getUsername())).build());
                } catch (EntityNotFoundException entityNotFoundException) {
                    entityNotFoundException.printStackTrace();
                }
            }
        }
    }

    public class DeleteClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Client client = null;
            try {
                client = clientService.findByPNC(clientView.getPersonalNumericalCode());
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

            if(client == null){
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Could not find client to delete!");
            } else {
                clientService.delete(client);
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Deleted client successfully!");
                try {
                    activityLogService.save(new ActivityLogBuilder().setActivity("client deleted by user").setDate(new Date()).setUser(userService.findByUsername(adminView.getUsername())).build());
                } catch (EntityNotFoundException entityNotFoundException) {
                    entityNotFoundException.printStackTrace();
                }
            }
        }
    }

    public class UpdateClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Client newClient = null;
            try {
                newClient = createClient();
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }
            Client oldClient = null;
            try {
                oldClient = clientService.findByPNC(clientView.getPersonalNumericalCode());
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }
            if(oldClient != null){
                clientService.update(oldClient, newClient);
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Updated client successfully!");
            } else {
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Could not find and update client!");
                try {
                    activityLogService.save(new ActivityLogBuilder().setActivity("client updated by user").setDate(new Date()).setUser(userService.findByUsername(adminView.getUsername())).build());
                } catch (EntityNotFoundException entityNotFoundException) {
                    entityNotFoundException.printStackTrace();
                }
            }
        }
    }

    public class FindByIdClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> clientNotification = null;
            try {
                clientNotification = clientService.findById(clientView.getId());
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(clientView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Found id client successfully!");
            }
        }
    }

    public class FindAllClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> clientNotification = clientService.findAll();
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(clientView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Found all clients successfully!");
            }
        }
    }

    public class RemoveAllClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> clientNotification = clientService.removeAll();
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(clientView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Removed all clients successfully!");
                try {
                    activityLogService.save(new ActivityLogBuilder().setActivity("all clients removed by user").setDate(new Date()).setUser(userService.findByUsername(adminView.getUsername())).build());
                } catch (EntityNotFoundException entityNotFoundException) {
                    entityNotFoundException.printStackTrace();
                }
            }
        }
    }

    private Client createClient() throws EntityNotFoundException {
        return new ClientBuilder()
                .setAddress(clientView.getAddress())
                .setIdentificationNumber(clientView.getIdentificationNumber())
                .setPersonalNumericalCode(clientView.getPersonalNumericalCode())
                .setName(clientView.getName())
                .setPhoneNumber(clientView.getPhoneNumber())
                .build();
    }
}
