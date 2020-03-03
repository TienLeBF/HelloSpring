package org.o7planning.thymeleaf;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.o7planning.thymeleaf.controller.Person_Controller;
import org.o7planning.thymeleaf.db.Connection_Util;
import org.o7planning.thymeleaf.db.Database;
import org.o7planning.thymeleaf.db.Database_Factory;
import org.o7planning.thymeleaf.model.Person;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootThymeleafLolApplication {
    private static Logger log = Logger
            .getLogger(SpringBootThymeleafLolApplication.class.getSimpleName());

    public static final Connection_Util MYSQL;

    static {
        String fileDbSpring = "etc/db_spring_cof.properties";

        Database db = Database_Factory.getDatabase("MYSQL");

        MYSQL = new Connection_Util(db, fileDbSpring);
        try {
            MYSQL.createConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, Exception {

        try {
            List<Person> persons = new Person_Controller().getListPersons();
            new Person_Controller().test();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //        SpringApplication.run(SpringBootThymeleafLolApplication.class, args);
    }

}
