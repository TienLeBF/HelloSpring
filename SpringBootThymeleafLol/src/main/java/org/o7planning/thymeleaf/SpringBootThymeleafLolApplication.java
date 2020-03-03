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
	private static final Logger LOG = Logger.getLogger(SpringBootThymeleafLolApplication.class.getSimpleName());

	public static final Connection_Util MYSQL_SPRING;
	public static final Connection_Util MYSQL_MONITOR;
	static {
		String fileDbSpring = "etc/db_spring_cof.properties";
		Database db = Database_Factory.getDatabase("MYSQL");
		MYSQL_SPRING = new Connection_Util(db, fileDbSpring);
		
		String fileDbMonitor = "etc/db_monitor_cof.properties";
		Database db2 = Database_Factory.getDatabase("MYSQL");
		MYSQL_MONITOR = new Connection_Util(db2, fileDbMonitor);
		
		try {
			MYSQL_MONITOR.createConnection();
			MYSQL_SPRING.createConnection();
		} catch (Exception e) {
			LOG.error("Cannot create connection");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException, Exception {

		try {
//			List<Person> persons = new Person_Controller().getListPersons();
//			int count = 0;
//			for (Person person : persons) {
//				LOG.info("\n****************** person " + ++count + " ******************");
//				LOG.info(person.toString());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// SpringApplication.run(SpringBootThymeleafLolApplication.class, args);
	}

}
