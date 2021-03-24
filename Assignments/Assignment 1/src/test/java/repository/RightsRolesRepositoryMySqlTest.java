package repository;

import launcher.ComponentFactory;
import org.junit.BeforeClass;
import repository.security.RightsRolesRepository;

public class RightsRolesRepositoryMySqlTest {

    private static RightsRolesRepository rightsRolesRepository;

    @BeforeClass
    public static void setupClass() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        rightsRolesRepository = componentFactory.getRightsRolesRepository();
    }
}
