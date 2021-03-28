package repository.client;

import launcher.ComponentFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientRepositoryMySqlTest {

    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setupClass() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        clientRepository = componentFactory.getClientRepository();
    }

    @Before
    public void cleanUp() {
        clientRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setIdentificationNumber(123654L)
                .setName("Mihai Florin")
                .setPersonalNumericalCode(299021L)
                .setPhoneNumber(456789512L)
                .build();
        Client client2 = new ClientBuilder()
                .setAddress("Str. M. Viteazul, 20")
                .setIdentificationNumber(123456L)
                .setName("Mihai Andrei")
                .setPersonalNumericalCode(299020L)
                .setPhoneNumber(456789510L)
                .build();
        clientRepository.save(client);
        clientRepository.save(client2);

        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 2);
    }

    @Test
    public void findById() throws EntityNotFoundException {
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setIdentificationNumber(4569L)
                .setName("Mihai Narius")
                .setPersonalNumericalCode(78965L)
                .setPhoneNumber(4567812L)
                .build();
        Client client2 = new ClientBuilder()
                .setAddress("Str. M. Viteazul, 20")
                .setIdentificationNumber(1236L)
                .setName("Mihai Ion")
                .setPersonalNumericalCode(2020L)
                .setPhoneNumber(45610L)
                .build();
        clientRepository.save(client);
        clientRepository.save(client2);

        Client found = null;

        List<Client> clients = clientRepository.findAll();
        Client client0 = clients.get(0);
        for (Client cl : clients) {
            if (cl.getId().equals(clientRepository.findById(client0).getId())) {
                found = cl;
            }
        }
        Assert.assertNotNull(found);
    }

    @Test
    public void save() {
        assertTrue(clientRepository.save(
                new ClientBuilder()
                        .setAddress("Str. M. Viteazul, 20")
                        .setIdentificationNumber(1236L)
                        .setName("Mihai Ion")
                        .setPersonalNumericalCode(2020L)
                        .setPhoneNumber(45610L)
                        .build()
        ));
    }

    @Test
    public void update() {
        Client client = new ClientBuilder()
                .setAddress("Str. M. Viteazul, 20")
                .setPersonalNumericalCode(123123123L)
                .setIdentificationNumber(12312345L)
                .setPhoneNumber(456987L)
                .setName("Marian")
                .build();
        Client updateClient = new ClientBuilder()
                .setAddress("Str. M. Viteazul, 20")
                .setPersonalNumericalCode(123123123L)
                .setPhoneNumber(456987L)
                .setIdentificationNumber(123123L)
                .setName("Mihai")
                .build();

        clientRepository.save(client);
        clientRepository.update(clientRepository.findAll().get(0), updateClient);
        assertEquals(clientRepository.findAll().get(0).getName(), updateClient.getName());
    }

    @Test
    public void delete(){
        Client client = new ClientBuilder()
                .setAddress("Str. M. Viteazul, 20")
                .setPersonalNumericalCode(123123123L)
                .setIdentificationNumber(12312345L)
                .setPhoneNumber(456987L)
                .setName("Marian").build();

        clientRepository.save(client);
        clientRepository.delete(client);
        List<Client> clients = clientRepository.findAll();
        Assert.assertTrue(clients.isEmpty());
    }

    @Test
    public void findByPNC() throws EntityNotFoundException {
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setIdentificationNumber(4569L)
                .setName("Mihai Narius")
                .setPersonalNumericalCode(78965L)
                .setPhoneNumber(4567812L)
                .build();
        Client client2 = new ClientBuilder()
                .setAddress("Str. M. Viteazul, 20")
                .setIdentificationNumber(1236L)
                .setName("Mihai Ion")
                .setPersonalNumericalCode(2020L)
                .setPhoneNumber(45610L)
                .build();
        clientRepository.save(client);
        clientRepository.save(client2);

        Client found = null;

        List<Client> clients = clientRepository.findAll();
        Client client0 = clients.get(0);
        for (Client cl : clients) {
            if (cl.getPersonalNumericalCode().equals(clientRepository.findByPNC(client0).getPersonalNumericalCode())) {
                found = cl;
            }
        }
        Assert.assertNotNull(found);
    }

    @Test
    public void removeAll(){
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setIdentificationNumber(4569L)
                .setName("Mihai Narius")
                .setPersonalNumericalCode(78965L)
                .setPhoneNumber(4567812L)
                .build();
        Client client2 = new ClientBuilder()
                .setAddress("Str. M. Viteazul, 20")
                .setIdentificationNumber(1236L)
                .setName("Mihai Ion")
                .setPersonalNumericalCode(2020L)
                .setPhoneNumber(45610L)
                .build();
        clientRepository.save(client);
        clientRepository.save(client2);
        clientRepository.removeAll();
        List<Client> clients = clientRepository.findAll();
        Assert.assertTrue(clients.isEmpty());
    }
}