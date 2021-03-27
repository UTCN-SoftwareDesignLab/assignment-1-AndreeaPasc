package service.client;

import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;

public interface ClientService {

    Notification<Client> findAll();

    Notification<Client> save(Client client);

    Notification<Client> removeAll();

    public void update(Client oldClient, Client newClient);

    Notification<Client> delete(Client client);

    Notification<Client> findById(Client client) throws EntityNotFoundException;

    Client findByPNC(Client client) throws EntityNotFoundException;
}
