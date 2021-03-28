package service.client;

import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;

public interface ClientService {

    Notification<Boolean> findAll();

    Notification<Boolean> save(Client client);

    Notification<Boolean> removeAll();

    void update(Client oldClient, Client newClient);

    Notification<Boolean> delete(Client client);

    Notification<Boolean> findById(Client client) throws EntityNotFoundException;

    Client findByPNC(Client client) throws EntityNotFoundException;
}
