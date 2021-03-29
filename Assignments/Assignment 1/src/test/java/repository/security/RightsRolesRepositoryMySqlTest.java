package repository.security;

import controller.AdminController;
import launcher.ComponentFactory;
import model.Right;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class RightsRolesRepositoryMySqlTest {

    private static RightsRolesRepository rightsRolesRepository;
    private static UserRepository userRepository;

    @BeforeClass
    public static void setupClass() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        rightsRolesRepository = componentFactory.getRightsRolesRepository();
        userRepository = componentFactory.getUserRepository();
    }

    @Before
    public void cleanUp(){
        rightsRolesRepository.removeAll();
        userRepository.removeAll();
    }

    @Test
    public void addRole(){
        rightsRolesRepository.addRole("USER");
        Assert.assertNotNull(rightsRolesRepository.findRoleByTitle("USER"));
    }

    @Test
    public void findRoleByTitle(){
        rightsRolesRepository.addRole("ADMIN");
        Assert.assertEquals(rightsRolesRepository.findRoleByTitle("ADMIN").getRole(), "ADMIN");
    }

    @Test
    public void findRoleById(){
        rightsRolesRepository.addRole("ADMIN");
        Role role = rightsRolesRepository.findRoleByTitle("ADMIN");
        Assert.assertNotNull(rightsRolesRepository.findRoleById(role.getId()));
    }
}
