package org.o7planning.thymeleaf;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.o7planning.thymeleaf.util.Connection_Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootThymeleafLolApplication {
    private static Logger log = Logger
            .getLogger(SpringBootThymeleafLolApplication.class.getSimpleName());

    public static void main(String[] args) throws SQLException {
        log.info("VCLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLll");
        String fileDbSpring = "etc/db_spring_cof.properties";
        Connection_Util util = new Connection_Util(fileDbSpring);
        util.initConnection();
        Connection c = util.getConnection();

        SpringApplication.run(SpringBootThymeleafLolApplication.class, args);
    }

}
