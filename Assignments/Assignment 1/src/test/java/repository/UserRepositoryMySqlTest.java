package repository;

import launcher.ComponentFactory;
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

import static database.Constants.Roles.EMPLOYEE;
import static org.junit.Assert.assertEquals;

public class UserRepositoryMySqlTest {
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;

    @BeforeClass
    public static void setupClass() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        userRepository = componentFactory.getUserRepository();
    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<User> users = userRepository.findAll();
        assertEquals(users.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Role role = new Role(1L, EMPLOYEE, null);
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);
        User user = new UserBuilder()
                .setUsername("user1@test.com")
                .setPassword("pass1_User1")
                .setRoles(roles)
                .build();
        User user2 = new UserBuilder()
                .setUsername("user2@test.com")
                .setPassword("pass2_User2")
                .setRoles(roles)
                .build();
        userRepository.save(user);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        assertEquals(users.size(), 2);
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
        User user2 = new UserBuilder()
                .setUsername("user2@test.com")
                .setPassword("pass2_User2")
                .setRoles(roles)
                .build();
        userRepository.save(user);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        User user0 = users.get(0);
        Assert.assertNotNull(userRepository.findByUsernameAndPassword(user0.getUsername(), user0.getPassword()));
    }

    @Test
    public void save(){
        Role role = new Role(1L, EMPLOYEE, null);
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);
        Assert.assertTrue(userRepository.save(new UserBuilder()
                .setUsername("user2@test.com")
                .setPassword("pass2_User2")
                .setRoles(roles)
                .build()));
    }

    @Test
    public void removeAll(){
        Role role = new Role(1L, EMPLOYEE, null);
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);
        User user = new UserBuilder()
                .setUsername("user1@test.com")
                .setPassword("pass1_User1")
                .setRoles(roles)
                .build();
        User user2 = new UserBuilder()
                .setUsername("user2@test.com")
                .setPassword("pass2_User2")
                .setRoles(roles)
                .build();
        userRepository.save(user);
        userRepository.save(user2);
        userRepository.removeAll();
        List<User> users = userRepository.findAll();
        Assert.assertTrue(users.isEmpty());
    }

    @Test
    public void delete(){
        Role role = new Role(1L, EMPLOYEE, null);
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);
        User user = new UserBuilder()
                .setUsername("user1@test.com")
                .setPassword("pass1_User1")
                .setRoles(roles)
                .build();
        userRepository.save(user);
        userRepository.delete(user);
        List<User> users = userRepository.findAll();
        Assert.assertTrue(users.isEmpty());
    }

    @Test
    public void update(){
        Role role = new Role(2L, EMPLOYEE, null);
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);
        User user = new UserBuilder()
                .setUsername("user1@test.com")
                .setPassword("pass1_User1")
                .setRoles(roles)
                .build();
        User user2 = new UserBuilder()
                .setUsername("user2@test.com")
                .setPassword("pass1_User1")
                .setRoles(roles)
                .build();

        userRepository.save(user);
        userRepository.update(userRepository.findAll().get(0), user2);
        assertEquals(userRepository.findAll().get(0).getUsername(), user2.getUsername());
    }
}
