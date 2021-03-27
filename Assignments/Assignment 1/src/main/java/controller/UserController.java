package controller;

import view.AccountView;
import view.ClientView;

public class UserController {
    private final AccountController accountController;
    private final ClientController clientController;


    public UserController(AccountController accountController, ClientController clientController) {
        ClientView clientView = new ClientView();
        AccountView accountView = new AccountView();


    }
}
