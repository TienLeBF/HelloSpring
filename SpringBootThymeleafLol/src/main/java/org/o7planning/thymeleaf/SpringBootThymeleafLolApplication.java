package org.o7planning.thymeleaf;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.o7planning.thymeleaf.db.Connection_Util;
import org.o7planning.thymeleaf.db.Database;
import org.o7planning.thymeleaf.db.Database_Factory;
import org.o7planning.thymeleaf.model.EventRequest;
import org.o7planning.thymeleaf.model.EventResponse;
import org.o7planning.thymeleaf.service.impl.Event_ServiceImpl;
import org.o7planning.thymeleaf.util.Constant;
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
			// 1. Start server
			SpringApplication.run(SpringBootThymeleafLolApplication.class, args);
		} catch (Exception e) {
			LOG.error("Start server failure - ERROR ...");
			e.printStackTrace();
		}

		LOG.info("**********************Start server complete**********************");
		// 2. update event error, SYNC, OTHER
		updateEventError();
	}

	/**
	 * Update event error
	 */
	private static void updateEventError() {
		LOG.info("SpringBootThymeleafLolApplication -> updateEventError -> is called");
		Short statusCode = (short) Constant.STATUS_EVENT.RUNNING.ordinal();
		Short resultCode = (short) Constant.RESULT_EVENT.WAITING.ordinal();
		Long eventId = null;
		try {
			Date requestDate = new Date();
			String eventCode = "update_event_error_logic";
			EventRequest eventRequest = new EventRequest(requestDate, requestDate, eventCode, null, statusCode,
					resultCode, null, (short) 1, null, null, SpringBootThymeleafLolApplication.class.getName(),
					"updateEventError()", null);
			eventId = Event_ServiceImpl.insertEvent(eventRequest);

			Event_ServiceImpl.udpateEventError();
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.SUCCESS_FULL.ordinal();

		} catch (Exception e) {
			LOG.error("SpringBootThymeleafLolApplication -> updateEventError -> ERROR ");
			e.printStackTrace();
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.ERROR.ordinal();
		} finally {
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			Date reponseDate = new Date();
			EventResponse eventResponse = new EventResponse(eventId, reponseDate, reponseDate, statusCode, resultCode,
					null, (short) 1, null, null, SpringBootThymeleafLolApplication.class.getName(), null);
			try {
				Event_ServiceImpl.updateEvent(eventResponse);
			} catch (Exception e2) {
				LOG.error("Cannot update event_id = " + eventId);
				e2.printStackTrace();
			}
			LOG.info("SpringBootThymeleafLolApplication -> updateEventError -> is called -> ended");
		}
	}
}
