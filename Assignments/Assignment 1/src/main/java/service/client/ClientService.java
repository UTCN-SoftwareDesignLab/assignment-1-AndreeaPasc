package service.client;

import model.ClientInfo;

import java.util.List;

public interface ClientService {

    List<ClientInfo> findAll();

    boolean save(ClientInfo clientInfo);

    void removeAll();

    boolean update(ClientInfo clientInfo);

    boolean delete(ClientInfo clientInfo);

    ClientInfo findById(ClientInfo clientInfo);

    ClientInfo findByPNC(ClientInfo clientInfo);
}
