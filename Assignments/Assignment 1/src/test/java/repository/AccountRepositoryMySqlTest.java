package repository;

import launcher.ComponentFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AccountRepositoryMySqlTest {

    private static AccountRepository accountRepository;
    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setupClass() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        accountRepository = componentFactory.getAccountRepository();
        clientRepository = componentFactory.getClientRepositoryMySQL();
    }

    @Before
    public void cleanUp() {
        accountRepository.removeAll();
        clientRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setPersonalNumericalCode(123654L)
                .setIdentificationNumber(1285L)
                .setPhoneNumber(4569789L)
                .setName("Marian")
                .build();
        clientRepository.save(client);
        List<Client> clients = clientRepository.findAll();

        Account account = new AccountBuilder()
                .setClientID(clients.get(0).getId())
                .setCreationDate(new Date())
                .setIdentificationNumber(123L)
                .setMoneyAmount(45L)
                .setType("Savings")
                .build();

        accountRepository.save(account);

        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 1);
    }

    @Test
    public void save(){
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setPersonalNumericalCode(123654L)
                .setIdentificationNumber(1285L)
                .setPhoneNumber(4569789L)
                .setName("Marian")
                .build();
        clientRepository.save(client);
        List<Client> clients = clientRepository.findAll();

        Account account = new AccountBuilder()
                .setClientID(clients.get(0).getId())
                .setCreationDate(new Date())
                .setIdentificationNumber(123L)
                .setMoneyAmount(50L)
                .setType("Savings")
                .build();

        Assert.assertTrue(accountRepository.save(account));
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

        clientRepository.save(client);
        List<Client> clients = clientRepository.findAll();

        Account account = new AccountBuilder()
                .setClientID(clients.get(0).getId())
                .setCreationDate(new Date())
                .setIdentificationNumber(123L)
                .setMoneyAmount(50L)
                .setType("Savings")
                .build();

        Account account2 = new AccountBuilder()
                .setClientID(clients.get(0).getId())
                .setCreationDate(new Date())
                .setIdentificationNumber(1234L)
                .setMoneyAmount(500L)
                .setType("Debit")
                .build();
        accountRepository.save(account);
        accountRepository.save(account2);

        Account found = null;

        List<Account> accounts = accountRepository.findAll();
        Account account0 = accounts.get(0);

        for (Account acc : accounts) {
            if(acc.getId().equals(accountRepository.findById(account0).getId()))
                found = acc;
        }
        Assert.assertNotNull(found);
    }

    @Test
    public void delete(){
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setPersonalNumericalCode(123654L)
                .setIdentificationNumber(1285L)
                .setPhoneNumber(4569789L)
                .setName("Marian")
                .build();
        clientRepository.save(client);
        List<Client> clients = clientRepository.findAll();

        Account account = new AccountBuilder()
                .setClientID(clients.get(0).getId())
                .setCreationDate(new Date())
                .setIdentificationNumber(123L)
                .setMoneyAmount(50L)
                .setType("Savings")
                .build();
        accountRepository.save(account);
        accountRepository.delete(account);
        List<Account> accounts = accountRepository.findAll();
        Assert.assertTrue(accounts.isEmpty());
    }

    @Test
    public void update(){
        Client client = new ClientBuilder()
                .setAddress("Str. M. Eminescu, 20")
                .setPersonalNumericalCode(123654L)
                .setIdentificationNumber(1285L)
                .setPhoneNumber(4569789L)
                .setName("Marian")
                .build();
        clientRepository.save(client);
        List<Client> clients = clientRepository.findAll();

        Account account = new AccountBuilder()
                .setClientID(clients.get(0).getId())
                .setCreationDate(new Date())
                .setIdentificationNumber(123L)
                .setMoneyAmount(50L)
                .setType("Savings")
                .build();

        Account account2 = new AccountBuilder()
                .setClientID(clients.get(0).getId())
                .setCreationDate(new Date())
                .setIdentificationNumber(123L)
                .setMoneyAmount(50L)
                .setType("Debit")
                .build();
        accountRepository.save(account);
        accountRepository.update(accountRepository.findAll().get(0), account2);
        assertEquals(accountRepository.findAll().get(0).getType(), account2.getType());
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

        clientRepository.save(client);
        List<Client> clients = clientRepository.findAll();

        Account account = new AccountBuilder()
                .setClientID(clients.get(0).getId())
                .setCreationDate(new Date())
                .setIdentificationNumber(123L)
                .setMoneyAmount(50L)
                .setType("Savings")
                .build();

        Account account2 = new AccountBuilder()
                .setClientID(clients.get(0).getId())
                .setCreationDate(new Date())
                .setIdentificationNumber(1234L)
                .setMoneyAmount(500L)
                .setType("Debit")
                .build();
        accountRepository.save(account);
        accountRepository.save(account2);
        accountRepository.removeAll();
        List<Account> accounts = accountRepository.findAll();
        Assert.assertTrue(accounts.isEmpty());
    }
}
