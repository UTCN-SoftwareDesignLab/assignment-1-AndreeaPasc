package database;

import static database.Constants.Tables.*;

public class SQLTableCreationFactory {

    public String getCreateSQLForTable(String table) {
        switch (table) {
            case CLIENT:
                return "CREATE TABLE IF NOT EXISTS client_info (" +
                        "  id int(11) NOT NULL AUTO_INCREMENT," +
                        "  idCardNumber int(11) NOT NULL," +
                        "  PNC int(11) NOT NULL NOT NULL," +
                        "  address varchar(500) NOT NULL," +
                        "  name varchar(500) NOT NULL," +
                        "  phoneNumber int(11) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE KEY id_UNIQUE (id)," +
                        "  UNIQUE INDEX idCardNumber_UNIQUE (idCardNumber ASC)," +
                        "  UNIQUE INDEX PNC_UNIQUE (PNC ASC)," +
                        "  UNIQUE phoneNumber_UNIQUE (phoneNumber ASC)," +
                        "  UNIQUE name_UNIQUE (name ASC)" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

            case ACCOUNT:
                return "CREATE TABLE IF NOT EXISTS account (" +
                        "  id int(11) NOT NULL AUTO_INCREMENT," +
                        "  client_id int(11) NOT NULL, " +
                        "  idNumber int(11) NOT NULL," +
                        "  type VARCHAR(200) NOT NULL," +
                        "  moneyAmount int(11) NOT NULL," +
                        "  creationDate datetime DEFAULT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id)," +
                        "  UNIQUE INDEX idNumber_UNIQUE (idNumber ASC)," +
                        "  INDEX client_id_idx (client_id ASC)," +
                        "  CONSTRAINT client_id" +
                        "    FOREIGN KEY (client_id)" +
                        "    REFERENCES client_info (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";


            case USER:
                return "CREATE TABLE IF NOT EXISTS user (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  username VARCHAR(200) NOT NULL," +
                        "  password VARCHAR(64) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  UNIQUE INDEX username_UNIQUE (username ASC));";

            case ROLE:
                return "  CREATE TABLE IF NOT EXISTS role (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  role VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  UNIQUE INDEX role_UNIQUE (role ASC));";

            case RIGHT:
                return "  CREATE TABLE IF NOT EXISTS `right` (" +
                        "  `id` INT NOT NULL AUTO_INCREMENT," +
                        "  `right` VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (`id`)," +
                        "  UNIQUE INDEX `id_UNIQUE` (`id` ASC)," +
                        "  UNIQUE INDEX `right_UNIQUE` (`right` ASC));";

            case ROLE_RIGHT:
                return "  CREATE TABLE IF NOT EXISTS role_right (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  role_id INT NOT NULL," +
                        "  right_id INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  INDEX role_id_idx (role_id ASC)," +
                        "  INDEX right_id_idx (right_id ASC)," +
                        "  CONSTRAINT role_id" +
                        "    FOREIGN KEY (role_id)" +
                        "    REFERENCES role (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE," +
                        "  CONSTRAINT right_id" +
                        "    FOREIGN KEY (right_id)" +
                        "    REFERENCES `right` (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";

            case USER_ROLE:
                return "\tCREATE TABLE IF NOT EXISTS user_role (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  user_id INT NOT NULL," +
                        "  role_id INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  INDEX user_id_idx (user_id ASC)," +
                        "  INDEX role_id_idx (role_id ASC)," +
                        "  CONSTRAINT user_fkid" +
                        "    FOREIGN KEY (user_id)" +
                        "    REFERENCES user (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE," +
                        "  CONSTRAINT role_fkid" +
                        "    FOREIGN KEY (role_id)" +
                        "    REFERENCES role (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";

            default:
                return "";

        }
    }

}
