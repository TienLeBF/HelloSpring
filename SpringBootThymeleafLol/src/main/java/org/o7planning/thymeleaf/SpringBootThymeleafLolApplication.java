package org.o7planning.thymeleaf;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.o7planning.thymeleaf.db.Connection_Util;
import org.o7planning.thymeleaf.db.Database;
import org.o7planning.thymeleaf.db.Database_Factory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootThymeleafLolApplication {
	private static final Logger LOG = Logger.getLogger(SpringBootThymeleafLolApplication.class.getSimpleName());
	public static final Connection_Util MYSQL_SPRING;
	public static final Connection_Util MYSQL_MONITOR;
	static {
		String fileDbMonitor = "etc/db_monitor_cof.properties";
		Database db2 = Database_Factory.getDatabase("MYSQL");
		MYSQL_MONITOR = new Connection_Util(db2, fileDbMonitor);

		String fileDbSpring = "etc/db_spring_cof.properties";
		Database db = Database_Factory.getDatabase("MYSQL");
		MYSQL_SPRING = new Connection_Util(db, fileDbSpring);

		try {
			MYSQL_MONITOR.createConnection();
			MYSQL_SPRING.createConnection();
		} catch (SQLException e) {
			// LOG.error("Cannot create connection");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException, Exception {
		LOG.info("*************************Starting server*************************");
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringApplication.run(SpringBootThymeleafLolApplication.class, args);
		LOG.info("**********************Start server complete**********************");
	}

}
