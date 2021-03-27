package service.client;

import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public Notification<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Notification<Client> save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Notification<Client> removeAll() {
        clientRepository.removeAll();
        return null;
    }

    @Override
    public void update(Client oldClient, Client newClient) {
        clientRepository.update(oldClient, newClient);
    }

    @Override
    public Notification<Client> delete(Client client) {
        return clientRepository.delete(client);
    }

    @Override
    public Notification<Client> findById(Client client) throws EntityNotFoundException {
        return clientRepository.findById(client);
    }

    @Override
    public Client findByPNC(Client client) throws EntityNotFoundException {
        return clientRepository.findByPNC(client);
    }
}
