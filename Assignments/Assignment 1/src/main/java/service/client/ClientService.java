package service.client;

import model.ClientInfo;
import repository.EntityNotFoundException;

import java.util.List;

public interface ClientService {

    List<ClientInfo> findAll();

    boolean save(ClientInfo clientInfo);

    void removeAll();

    public void update(ClientInfo oldClient, ClientInfo newClient);

    boolean delete(ClientInfo clientInfo);

    ClientInfo findById(ClientInfo clientInfo) throws EntityNotFoundException;

    ClientInfo findByPNC(ClientInfo clientInfo) throws EntityNotFoundException;
}
