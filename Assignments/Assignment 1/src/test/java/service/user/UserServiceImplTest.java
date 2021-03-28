package service.user;

import launcher.ComponentFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;

import java.util.ArrayList;
import static database.Constants.Roles.EMPLOYEE;

public class UserServiceImplTest{
    private static UserService userService;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        userService = componentFactory.getUserService();
    }

    @Before
    public void cleanUp() {
        userService.removeAll();
    }

    @Test
    public void findAll(){
        boolean valid = userService.findAll().getResult();
        Assert.assertFalse(valid);
    }

    @Test
    public void save(){
        Role role = new Role(1L, EMPLOYEE, null);
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);
        User user = new UserBuilder()
                .setUsername("user1@test.com")
                .setPassword("pass1_Test")
                .setRoles(roles)
                .build();

        userService.save(user);
        Assert.assertTrue(userService.findAll().getResult());
    }

    @Test
    public void removeAll(){
        Role role = new Role(1L, EMPLOYEE, null);
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);
        User user = new UserBuilder()
                .setUsername("user1@test.com")
                .setPassword("pass1_Test")
                .setRoles(roles)
                .build();

        userService.save(user);
        userService.removeAll();
        Assert.assertFalse(userService.findAll().getResult());
    }

    @Test
    public void delete(){
        Role role = new Role(1L, EMPLOYEE, null);
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);
        User user = new UserBuilder()
                .setUsername("user1@test.com")
                .setPassword("pass1_Test")
                .setRoles(roles)
                .build();

        userService.save(user);
        userService.delete(user);
        Assert.assertFalse(userService.findAll().getResult());
    }

    @Test
    public void findById() throws EntityNotFoundException {
        Role role = new Role(1L, EMPLOYEE, null);
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);
        User user = new UserBuilder()
                .setUsername("user1@test.com")
                .setPassword("pass1_Test")
                .setRoles(roles)
                .build();
        userService.save(user);
        Assert.assertTrue(userService.findById(user.getId()).getResult());
    }

    @Test
    public void update(){
        Role role = new Role(1L, EMPLOYEE, null);
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);
        User user = new UserBuilder()
                .setUsername("user1@test.com")
                .setPassword("pass1_Test")
                .setRoles(roles)
                .build();
        User user2 = new UserBuilder()
                .setUsername("user2@test.com")
                .setPassword("pass1_Test")
                .setRoles(roles)
                .build();

        userService.save(user);
        userService.update(user, user2);
        Assert.assertEquals("user2@test.com", user2.getUsername());
    }

    @Test
    public void findByUsernameAndPassword(){
        Role role = new Role(1L, EMPLOYEE, null);
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);
        User user = new UserBuilder()
                .setUsername("user1@test.com")
                .setPassword("pass1_User1")
                .setRoles(roles)
                .build();

        userService.save(user);

        Assert.assertNotNull(userService.findByUsernameAndPassword(user.getUsername(), user.getPassword()));
    }
}