package service.client;

import model.ClientInfo;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public List<ClientInfo> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public boolean save(ClientInfo clientInfo) {
        return clientRepository.save(clientInfo);
    }

    @Override
    public void removeAll() {
        clientRepository.removeAll();
    }

    @Override
    public void update(ClientInfo oldClient, ClientInfo newClient) {
        clientRepository.update(oldClient, newClient);
    }

    @Override
    public boolean delete(ClientInfo clientInfo) {
        return clientRepository.delete(clientInfo);
    }

    @Override
    public ClientInfo findById(ClientInfo clientInfo) throws EntityNotFoundException {
        return clientRepository.findById(clientInfo);
    }

    @Override
    public ClientInfo findByPNC(ClientInfo clientInfo) throws EntityNotFoundException {
        return clientRepository.findByPNC(clientInfo);
    }
}
