package launcher;

import controller.AccountController;
import controller.AdminController;
import controller.ClientController;
import controller.LoginController;
import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.activity.ActivityLogRepository;
import repository.activity.ActivityLogRepositoryMySql;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.activity.ActivityLogService;
import service.activity.ActivityLogServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserService;
import service.user.UserServiceImpl;
import view.AccountView;
import view.ClientView;
import view.LoginView;
import view.AdminView;

import java.sql.Connection;

public class ComponentFactory {

    private final LoginView loginView;
    private final LoginController loginController;

    //private final AccountView accountView;
    private final AccountController accountController;

    //private final ClientView clientView;
    private final ClientController clientController;

    private final AdminController adminController;
    //private final AdminView adminView;

    private final AuthenticationService authenticationService;
    private final AccountService accountService;
    private final ClientService clientService;
    private final UserService userService;
    private final ActivityLogService activityLogService;

    private final ActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

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
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.activityLogRepository = new ActivityLogRepositoryMySql(connection, userRepository);

        this.activityLogService = new ActivityLogServiceImpl(activityLogRepository);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.userService = new UserServiceImpl(this.userRepository);
        this.clientService = new ClientServiceImpl(this.clientRepository);
        this.accountService = new AccountServiceImpl(this.accountRepository);

        AccountView accountView = new AccountView();
        this.accountController = new AccountController(accountView, this.accountService);
        ClientView clientView = new ClientView();
        this.clientController = new ClientController(clientView, this.clientService);

        AdminView adminView = new AdminView();
        this.adminController = new AdminController(adminView, this.userService, this.activityLogService);

        this.loginView = new LoginView();
        this.loginController = new LoginController(loginView, authenticationService, adminView, accountView, clientView);


    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    /*
    public AccountView getAccountView() {
        return accountView;
    }
     */

    public AccountController getAccountController() {
        return accountController;
    }

    /*
    public ClientView getClientView() {
        return clientView;
    }
     */

    public ClientController getClientController() {
        return clientController;
    }

    public AdminController getAdminController() {
        return adminController;
    }

    /*
    public AdminView getUserView() {
        return adminView;
    }
     */

    public AccountService getAccountService() {
        return accountService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public UserService getUserService() {
        return userService;
    }

    public static ComponentFactory getInstance() {
        return instance;
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

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public ActivityLogRepository getActivityLogRepository(){
        return  activityLogRepository;
    }

    public LoginController getLoginController() {
        return loginController;
    }
}
