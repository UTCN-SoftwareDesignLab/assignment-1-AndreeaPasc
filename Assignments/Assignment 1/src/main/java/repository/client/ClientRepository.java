package repository.client;

import model.ClientInfo;
import model.User;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

public interface ClientRepository {
    List<ClientInfo> findAll();

    boolean save(ClientInfo clientInfo);

    void removeAll();

    void update(ClientInfo oldClient, ClientInfo newClient);

    boolean delete(ClientInfo clientInfo);

    ClientInfo findById(ClientInfo clientInfo) throws EntityNotFoundException;

    ClientInfo findByPNC(ClientInfo clientInfo) throws EntityNotFoundException;
}
