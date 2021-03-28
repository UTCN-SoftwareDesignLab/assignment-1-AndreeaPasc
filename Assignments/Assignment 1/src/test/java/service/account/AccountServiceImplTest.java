package service.account;

import launcher.ComponentFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;
import service.client.ClientService;

import java.util.Date;
import java.util.List;

public class AccountServiceImplTest {
    private static AccountService accountService;
    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        accountService = componentFactory.getAccountService();
        clientRepository = componentFactory.getClientRepository();
    }

    @Before
    public void cleanUp() {
        accountService.removeAll();
        clientRepository.removeAll();
    }

    @Test
    public void findALl(){
        boolean valid = accountService.findAll().getResult();
        Assert.assertFalse(valid);
    }

    @Test
    public void save(){
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setPersonalNumericalCode(133654L)
                .setIdentificationNumber(1275L)
                .setPhoneNumber(4579789L)
                .setName("Mariana")
                .build();
        clientRepository.save(client);
        List<Client> clients = clientRepository.findAll();

        Account account = new AccountBuilder()
                .setClientID(clients.get(0).getId())
                .setCreationDate(new Date())
                .setIdentificationNumber(123456L)
                .setMoneyAmount(45L)
                .setType("Savings")
                .build();

        accountService.save(account);
        Assert.assertTrue(accountService.findAll().getResult());
    }

    @Test
    public void removeAll(){
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setPersonalNumericalCode(133654L)
                .setIdentificationNumber(1275L)
                .setPhoneNumber(4579789L)
                .setName("Mariana")
                .build();
        clientRepository.save(client);
        List<Client> clients = clientRepository.findAll();

        Account account = new AccountBuilder()
                .setClientID(clients.get(0).getId())
                .setCreationDate(new Date())
                .setIdentificationNumber(123456L)
                .setMoneyAmount(45L)
                .setType("Savings")
                .build();

        accountService.save(account);
        accountService.removeAll();
        Assert.assertFalse(accountService.findAll().getResult());
    }

    @Test
    public void delete(){
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setPersonalNumericalCode(133654L)
                .setIdentificationNumber(1275L)
                .setPhoneNumber(4579789L)
                .setName("Mariana")
                .build();
        clientRepository.save(client);
        List<Client> clients = clientRepository.findAll();

        Account account = new AccountBuilder()
                .setClientID(clients.get(0).getId())
                .setCreationDate(new Date())
                .setIdentificationNumber(123456L)
                .setMoneyAmount(45L)
                .setType("Savings")
                .build();

        accountService.save(account);
        accountService.delete(account);
        Assert.assertFalse(accountService.findAll().getResult());
    }

    @Test
    public void findById() throws EntityNotFoundException {
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setPersonalNumericalCode(133654L)
                .setIdentificationNumber(1275L)
                .setPhoneNumber(4579789L)
                .setName("Mariana")
                .build();
        clientRepository.save(client);
        List<Client> clients = clientRepository.findAll();

        Account account = new AccountBuilder()
                .setClientID(clients.get(0).getId())
                .setCreationDate(new Date())
                .setIdentificationNumber(123456L)
                .setMoneyAmount(45L)
                .setType("Savings")
                .build();

        accountService.save(account);
        Assert.assertTrue(accountService.findById(account.getId()).getResult());
    }
}
