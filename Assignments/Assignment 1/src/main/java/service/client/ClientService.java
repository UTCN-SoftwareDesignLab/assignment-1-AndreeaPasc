package service.client;

import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;

public interface ClientService {

    Notification<Boolean> findAll();

    Notification<Boolean> save(Client client);

    Notification<Boolean> removeAll();

    void update(Client oldClient, Client newClient);

    void delete(Client client);

    Notification<Boolean> findById(Long id) throws EntityNotFoundException;

    Client findByPNC(Client client) throws EntityNotFoundException;
}
