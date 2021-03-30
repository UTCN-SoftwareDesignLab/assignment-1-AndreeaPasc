package repository.client;

import model.Client;
import repository.EntityNotFoundException;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();

    boolean save(Client client);

    void removeAll();

    void update(Client oldClient, Client newClient);

    boolean delete(Client client);

    Client findById(Long id) throws EntityNotFoundException;

    Client findByPNC(Long pnc) throws EntityNotFoundException;
}
