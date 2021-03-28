package service.client;

import model.Client;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Notification<Boolean> findAll() {
        ClientValidator clientValidator = null;
        boolean valid = false;
        List<Client> clients = clientRepository.findAll();
        for(Client client: clients) {
            clientValidator = new ClientValidator(client);
            valid = clientValidator.validate();
        }
        Notification<Boolean> clientNotification = new Notification<>();
        if(valid){
            clientNotification.setResult(!clientRepository.findAll().isEmpty());
        }else{
            assert clientValidator != null;
            clientValidator.getErrors().forEach(clientNotification::addError);
            clientNotification.setResult(Boolean.FALSE);
        }
        return clientNotification;
    }

    @Override
    public Notification<Boolean> save(Client client) {
        ClientValidator clientValidator = new ClientValidator(client);
        boolean valid = clientValidator.validate();
        Notification<Boolean> clientNotification = new Notification<>();
        if(valid){
            clientNotification.setResult(clientRepository.save(client));
        }else{
            clientValidator.getErrors().forEach(clientNotification::addError);
            clientNotification.setResult(Boolean.FALSE);
        }
        return clientNotification;
    }

    @Override
    public Notification<Boolean> removeAll() {
        clientRepository.removeAll();
        Notification<Boolean> clientNotification = new Notification<>();
        List<Client> clients = clientRepository.findAll();
        if(clients.isEmpty()){
            clientNotification.setResult(Boolean.TRUE);
        }else{
            clientNotification.setResult(Boolean.FALSE);
        }
        return clientNotification;
    }

    @Override
    public void update(Client oldClient, Client newClient) {
        clientRepository.update(oldClient, newClient);
    }

    @Override
    public Notification<Boolean> delete(Client client) {
        ClientValidator clientValidator = new ClientValidator(client);
        boolean valid = clientValidator.validate();
        Notification<Boolean> clientNotification = new Notification<>();
        if(valid){
            clientNotification.setResult(clientRepository.delete(client));
        }else{
            clientValidator.getErrors().forEach(clientNotification::addError);
            clientNotification.setResult(Boolean.FALSE);
        }
        return clientNotification;
    }

    @Override
    public Notification<Boolean> findById(Client client) throws EntityNotFoundException {
        ClientValidator clientValidator = new ClientValidator(client);
        boolean valid = clientValidator.validate();
        Notification<Boolean> clientNotification = new Notification<>();
        List<Client> clients = null;
        if(valid){
            clients.add(clientRepository.findById(client));
            clientNotification.setResult(!clients.isEmpty());
        }else{
            clientValidator.getErrors().forEach(clientNotification::addError);
            clientNotification.setResult(Boolean.FALSE);
        }
        return clientNotification;
    }

    @Override
    public Client findByPNC(Client client) throws EntityNotFoundException {
        return clientRepository.findByPNC(client);
    }
}
