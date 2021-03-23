package launcher;

import database.Boostraper;

import java.sql.SQLException;

public class Launcher {

    public static boolean BOOTSTRAP = false;

    public static void main(String[] args) {
        bootstrap();

        ComponentFactory componentFactory = ComponentFactory.instance(true);
        componentFactory.getLoginView().setVisible(true);
    }

    private static void bootstrap() {
        if (BOOTSTRAP) {
            try {
                new Boostraper().execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
