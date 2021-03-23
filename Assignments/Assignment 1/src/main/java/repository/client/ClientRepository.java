package repository.client;

import model.ClientInfo;
import model.User;
import model.validation.Notification;

import java.util.List;

public interface ClientRepository {
    List<ClientInfo> findAll();

    boolean save(ClientInfo clientInfo);

    void removeAll();

    public boolean update(ClientInfo clientInfo);

    public boolean delete(ClientInfo clientInfo);

    public ClientInfo findById(ClientInfo clientInfo);

    public ClientInfo findByPNC(ClientInfo clientInfo);
}
