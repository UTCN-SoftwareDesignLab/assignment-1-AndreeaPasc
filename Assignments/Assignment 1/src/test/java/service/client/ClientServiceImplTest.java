package service.client;

import launcher.ComponentFactory;

import model.Client;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;

public class ClientServiceImplTest {
    private static ClientService clientService;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        clientService = componentFactory.getClientService();
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
}
