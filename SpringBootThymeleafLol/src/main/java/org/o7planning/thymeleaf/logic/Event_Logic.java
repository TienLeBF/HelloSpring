package org.o7planning.thymeleaf.logic;

import java.sql.SQLException;

import org.o7planning.thymeleaf.SpringBootThymeleafLolApplication;
import org.o7planning.thymeleaf.dao.impl.Event_DaoImpl;
import org.o7planning.thymeleaf.model.Event;

public class Event_Logic {
	private Event_Logic() {
	}

	public static void insertEvent(Event event) throws SQLException {
		try {
			Event_DaoImpl.insert(SpringBootThymeleafLolApplication.MYSQL_MONITOR.getConnection(), event);
		} catch (SQLException e) {
			throw e;
		}
	}
	
}
