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
        List<Client> clients = clientRepository.findAll();
        Notification<Boolean> accountNotification = new Notification<>();
        if(!clients.isEmpty()){
            accountNotification.setResult(Boolean.TRUE);
        }else{
            accountNotification.setResult(Boolean.FALSE);
        }
        return accountNotification;
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
    public void delete(Client client) {
        clientRepository.delete(client);
    }

    @Override
    public Notification<Boolean> findById(Long id) throws EntityNotFoundException {
        Client client = clientRepository.findById(id);
        Notification<Boolean> clientNotification = new Notification<>();
        List<Client> clients = clientRepository.findAll();
        clients.add(client);
        if(!clients.isEmpty()){
            clientNotification.setResult(Boolean.TRUE);
        }else{
            clientNotification.setResult(Boolean.FALSE);
        }
        return clientNotification;
    }

    @Override
    public Client findByPNC(Long pnc) throws EntityNotFoundException {
        return clientRepository.findByPNC(pnc);
    }
}
