package org.o7planning.thymeleaf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.o7planning.thymeleaf.util.Connection_Util;
import org.o7planning.thymeleaf.util.Constant;
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
        String name = Constant.BLANK;
        int age;
        int sex;
        String email = Constant.BLANK;
        String address = Constant.BLANK;
        int stt = 0;
        try {
            String query = " select first_name, last_name, age, sex, email, address from spring.person";
            PreparedStatement preStatement = c.prepareStatement(query);
            ResultSet resultSet = preStatement.executeQuery();
            while (null != resultSet && resultSet.next()) {
                name = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                age = resultSet.getInt("age");
                email = resultSet.getString("email");
                address = resultSet.getString("address");
                stt++;
                log.info("++++++++++$$$++++++++++");
                log.info("Record " + stt + ":");
                log.info("name    = " + name);
                log.info("age     = " + age);
                log.info("email   = " + email);
                log.info("address = " + address);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        SpringApplication.run(SpringBootThymeleafLolApplication.class, args);
    }

}
