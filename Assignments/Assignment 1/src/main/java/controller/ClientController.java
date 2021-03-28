package controller;

import model.Account;
import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.client.ClientService;
import view.ClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClientController {
    private ClientView clientView;
    private ClientService clientService;

    public ClientController(ClientView clientView, ClientService clientService) {
        this.clientView = clientView;
        this.clientService = clientService;

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
            Client client = createClient();

            Notification<Boolean> clientNotification = clientService.save(client);
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(clientView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Saved client successfully!");
            }
        }
    }

    public class DeleteClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Client client = createClient();
            List<Client> clients = null;
            clients.add(client);
            clientService.delete(client);

            if(!clients.isEmpty()){
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Could not delete client!");
            } else {
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Deleted account successfully!");
            }
        }
    }

    public class UpdateClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Client newClient = createClient();
            Notification<Boolean> clientNotification = null;
            try {
                clientNotification = clientService.findById(clientView.getId());
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(clientView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                Client oldClient = new ClientBuilder().setId(clientView.getId()).build();
                clientService.update(oldClient, newClient);
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Updated client successfully!");
            }
        }
    }

    public class FindByIdClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Client client = createClient();
            Notification<Boolean> clientNotification = null;
            try {
                clientNotification = clientService.findById(client.getId());
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
            }
        }
    }

    private Client createClient(){
        Client client = new ClientBuilder()
                .setId(clientView.getId())
                .setAddress(clientView.getAddress())
                .setIdentificationNumber(clientView.getIdentificationNumber())
                .setPersonalNumericalCode(clientView.getPersonalNumericalCode())
                .setName(clientView.getName())
                .setPhoneNumber(clientView.getPhoneNumber())
                .build();
        return client;
    }
}
