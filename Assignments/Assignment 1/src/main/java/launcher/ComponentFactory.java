package launcher;

import controller.LoginController;
import database.DBConnectionFactory;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactory {

    private final LoginView loginView;

    private final LoginController loginController;

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientRepositoryMySQL clientRepository;
    private final AccountRepositoryMySQL accountRepository;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.loginView = new LoginView();
        this.loginController = new LoginController(loginView, authenticationService);
        clientRepository = new ClientRepositoryMySQL(connection);
        accountRepository = new AccountRepositoryMySQL(connection);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public LoginView getLoginView() {
        return loginView;
    }


    public ClientRepositoryMySQL getClientRepositoryMySQL() {
        return clientRepository;
    }

    public AccountRepositoryMySQL getAccountRepository() {
        return accountRepository;
    }
    public LoginController getLoginController() {
        return loginController;
    }
}
