package repository.activity;

import launcher.ComponentFactory;
import model.Account;
import model.ActivityLog;
import model.Role;
import model.User;
import model.builder.ActivityLogBuilder;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class ActivityLogRepositoryMySqlTest {
    private static ActivityLogRepository activityLogRepository;
    private static UserRepository userRepository;

    @BeforeClass
    public static void setupClass() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        activityLogRepository = componentFactory.getActivityLogRepository();
        userRepository = componentFactory.getUserRepository();
    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }

    @Test
    public void findAll() throws EntityNotFoundException {
        List<ActivityLog> activities = activityLogRepository.findAll();
        Assert.assertNotNull(activities);
    }

    @Test
    public void save() {
        Role role = new Role(1L, EMPLOYEE, null);
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);
        User user = new UserBuilder()
                .setUsername("user2@test.com")
                .setPassword("pass2_User2")
                .setRoles(roles)
                .build();
        userRepository.save(user);

        ActivityLog activityLog = new ActivityLogBuilder()
                .setActivity("delete")
                .setUser(user)
                .setDate(new Date())
                .build();
        Assert.assertTrue(activityLogRepository.save(activityLog));
    }

    @Test
    public void findById() throws EntityNotFoundException {
        Role role = new Role(1L, EMPLOYEE, null);
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);
        User user = new UserBuilder()
                .setUsername("user2@test.com")
                .setPassword("pass2_User2")
                .setRoles(roles)
                .build();
        userRepository.save(user);

        ActivityLog activityLog = new ActivityLogBuilder()
                .setActivity("delete")
                .setUser(user)
                .setDate(new Date())
                .build();
        activityLogRepository.save(activityLog);

        List<ActivityLog> activities = activityLogRepository.findAll();
        ActivityLog activity0 = activities.get(0);
        ActivityLog found = null;

        for (ActivityLog ac : activities) {
            if(ac.getId().equals(activityLogRepository.findById(activity0.getId()).getId()))
                found = ac;
        }
        Assert.assertNotNull(found);
    }
}
