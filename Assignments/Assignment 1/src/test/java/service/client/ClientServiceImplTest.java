package service.client;

import launcher.ComponentFactory;

import model.Client;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

public class ClientServiceImplTest {
    private static ClientService clientService;
    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        clientService = componentFactory.getClientService();
        clientRepository = componentFactory.getClientRepository();
    }

    @Before
    public void cleanUp() {
        clientService.removeAll();
    }

    @Test
    public void findALl(){
        boolean valid = clientService.findAll().getResult();
        Assert.assertFalse(valid);
    }

    @Test
    public void save(){
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setIdentificationNumber(123456L)
                .setName("Mihai Florin")
                .setPersonalNumericalCode(1234567891L)
                .setPhoneNumber(1234567891L)
                .build();
        clientService.save(client);
        Assert.assertTrue(clientService.findAll().getResult());
    }

    @Test
    public void removeALl(){
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setIdentificationNumber(123456L)
                .setName("Mihai Florin")
                .setPersonalNumericalCode(1234567891L)
                .setPhoneNumber(1234567891L)
                .build();
        clientService.save(client);
        clientService.removeAll();
        Assert.assertFalse(clientService.findAll().getResult());
    }

    @Test
    public void delete(){
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setIdentificationNumber(123456L)
                .setName("Mihai Florin")
                .setPersonalNumericalCode(1234567891L)
                .setPhoneNumber(1234567891L)
                .build();
        clientService.save(client);
        clientService.delete(client);
        Assert.assertFalse(clientService.findAll().getResult());
    }

    @Test
    public void findById() throws EntityNotFoundException {
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setIdentificationNumber(123456L)
                .setName("Mihai Florin")
                .setPersonalNumericalCode(1234567891L)
                .setPhoneNumber(1234567891L)
                .build();
        clientService.save(client);
        Assert.assertTrue(clientService.findById(client.getId()).getResult());
    }

    @Test
    public void update(){
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setIdentificationNumber(123456L)
                .setName("Mihai Florin")
                .setPersonalNumericalCode(1234567891L)
                .setPhoneNumber(1234567891L)
                .build();

        Client client2 = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setIdentificationNumber(123456L)
                .setName("Marian Florin")
                .setPersonalNumericalCode(1234567891L)
                .setPhoneNumber(1234567891L)
                .build();

        clientService.save(client);
        clientService.update(client, client2);
        Client client3 = clientRepository.findAll().get(0);
        Assert.assertEquals(client3.getName(), "Marian Florin");
    }
}
