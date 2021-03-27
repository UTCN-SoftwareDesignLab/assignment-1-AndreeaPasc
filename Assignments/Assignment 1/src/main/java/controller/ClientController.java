package controller;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.client.ClientService;
import view.ClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientController {
    private ClientView clientView;
    private ClientService clientService;

    public ClientController(ClientView clientView, ClientService clientService) {
        this.clientView = clientView;
        this.clientService = clientService;

        clientView.setSaveClientButtinListener(new SetSaveClientButtonListener);
        clientView.setRemoveClientButtonListeer(new DeleteClientButtonListener);
        clientView.setUpdateClientButtonListener(new UpdateClientButtonListener);
        clientView.setFindByIdClientButtonListener(new FindByIdClientButtonListener);
        clientView.setFindAllClientButtonListener(new FindAllClientButtonListener);
        clientView.setRemoveAllClientButtonListener(new RemoveAllClientButtonListener);
    }

    private class SetSaveClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Client client = createClient();

            Notification<Client> clientNotification = clientService.save(client);
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

            Notification<Client> clientNotification = clientService.delete(client);
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(clientView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Removed client successfully!");
            }
        }
    }

    public class UpdateClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Client newClient = createClient();

            Notification<Client> clientNotification = null;
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

            Notification<Client> clientNotification = null;
            try {
                clientNotification = clientService.findById(client);
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
            Notification<Client> clientNotification = clientService.findAll();
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
            Notification<Client> clientNotification = clientService.removeAll();
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(clientView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Removed all clients successfully!");
            }
        }
    }

    private Client createClient(){
        Client client = new ClientBuilder()
                .setAddress(clientView.getAddress())
                .setIdentificationNumber(clientView.getIdentificationNumber())
                .setPersonalNumericalCode(clientView.getPersonalNumericalCode())
                .setName(clientView.getName())
                .setPhoneNumber(clientView.getPhoneNUmber)
                .build();
        return client;
    }
}
