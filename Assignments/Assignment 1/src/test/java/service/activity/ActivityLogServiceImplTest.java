package service.activity;

import launcher.ComponentFactory;
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
import repository.activity.ActivityLogRepository;
import repository.user.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class ActivityLogServiceImplTest {

    private static ActivityLogService activityLogService;
    private static UserRepository userRepository;
    private static ActivityLogRepository activityLogRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        activityLogService = componentFactory.getActivityLogService();
        userRepository = componentFactory.getUserRepository();
        activityLogRepository = componentFactory.getActivityLogRepository();
    }

    @Before
    public void cleanUp(){
        userRepository.removeAll();
    }

    @Test
    public void findAll() throws EntityNotFoundException {
        Assert.assertFalse(activityLogService.findAll().getResult());
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
        Assert.assertTrue(activityLogService.save(activityLog));
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
        activityLogService.save(activityLog);

        List<ActivityLog> activities = activityLogRepository.findAll();
        ActivityLog activity0 = activities.get(0);
        ActivityLog found = null;

        for (ActivityLog ac : activities) {
            if(ac.getId().equals(activityLogService.findById(activity0.getId()).getId()))
                found = ac;
        }
        Assert.assertNotNull(found);
    }

    @Test
    public void checkDate() throws ParseException {
        Role role = new Role(1L, EMPLOYEE, null);
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);
        User user = new UserBuilder()
                .setUsername("user2@test.com")
                .setPassword("pass2_User2")
                .setRoles(roles)
                .build();
        userRepository.save(user);

        String sDate = "31/12/2020";
        String sStartDate = "30/12/2020";
        String sStopDate = "01/01/2021";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
        Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(sStartDate);
        Date stopDate = new SimpleDateFormat("dd/MM/yyyy").parse(sStopDate);

        ActivityLog activityLog = new ActivityLogBuilder()
                .setActivity("delete")
                .setUser(user)
                .setDate(date)
                .build();
        activityLogService.save(activityLog);

        Assert.assertTrue(activityLogService.checkDateRange(startDate, stopDate, activityLog));
    }
}
