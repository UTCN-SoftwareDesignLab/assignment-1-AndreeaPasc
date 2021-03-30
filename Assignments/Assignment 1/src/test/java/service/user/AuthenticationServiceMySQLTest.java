package service.user;

import launcher.ComponentFactory;
import model.Role;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.user.UserRepository;

import java.util.ArrayList;

import static database.Constants.Roles.EMPLOYEE;

/**
 * Created by Alex on 11/03/2017.
 */
public class AuthenticationServiceMySQLTest {

    public static final String TEST_USERNAME = "test@username.com";
    public static final String TEST_PASSWORD = "TestPassword1@";
    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        userRepository = componentFactory.getUserRepository();
        authenticationService = componentFactory.getAuthenticationService();
    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }


    @Test
    public void register() {
        Role role = new Role(1L, EMPLOYEE, null);
        Assert.assertTrue(
                authenticationService.register(TEST_USERNAME, TEST_PASSWORD, role).getResult()
        );
    }

    @Test
    public void login() throws Exception {
        Role role = new Role(1L, EMPLOYEE, null);
        authenticationService.register(TEST_USERNAME, TEST_PASSWORD, role);
        User user = authenticationService.login(TEST_USERNAME, TEST_PASSWORD).getResult();
        Assert.assertNotNull(user);
    }

    @Test
    public void logout() {

    }

}