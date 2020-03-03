package org.o7planning.thymeleaf.dao.impl;

import java.security.DrbgParameters.NextBytes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.o7planning.thymeleaf.model.Event;

public class Event_DaoImpl {
	private static final Logger LOG = Logger.getLogger(Event_DaoImpl.class.getSimpleName());

	private Event_DaoImpl() {
	}

	public static void insert(Connection connection, Event event) throws SQLException {
		PreparedStatement prepare = null;
		try {
			String query = "insert into monitor.events \n"
					+ "( \n"
					+ "request_at, response_at, modify_at, request_day, status, result_code, "
					+ "result_message, group_event, service, host_request, host_process, class_name, "
					+ "other_data, last_record_id \n"
					+ ") \n"
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			prepare = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
//			private Date responseAt;
//			private Date modifyAt;
//			private Date requestDay;
//			private short status;
//			private short resultCode;
//			private String resultMessage;
//			private short groupEvent;
//			private String service;
//			private String hostRequest;
//			private String hostProcess;
//			private String className;
//			private String otherData;
//			private long lastRecordId;

			prepare.setTimestamp(1, new Timestamp(event.getRequestAt().getTime()));
			prepare.setTimestamp(2, new Timestamp(event.getResponseAt().getTime()));
			prepare.setTimestamp(3, null == event.getModifyAt() ? null : new Timestamp(event.getModifyAt().getTime()));
			prepare.setTimestamp(4, new Timestamp(event.getRequestDay().getTime()));
			prepare.setShort(5, event.getStatus());
			prepare.setShort(6, event.getResultCode());
			prepare.setString(7, event.getResultMessage());
			prepare.setShort(8, event.getGroupEvent());
			prepare.setString(9, event.getService());
			prepare.setString(10, event.getHostRequest());
			prepare.setString(11, event.getHostProcess());
			prepare.setString(12, event.getClassName());
			prepare.setString(13, event.getOtherData());
			prepare.setLong(14, event.getLastRecordId());

			prepare.executeUpdate();
			try {
				ResultSet generatedKeys = prepare.getGeneratedKeys();
				if (generatedKeys.next()) {
					if (0 != generatedKeys.getInt(1)) {
						return;
					} else {
						LOG.error("Cannot insert event");
						throw new SQLException();
					}
				}
			} catch (SQLException e) {
				throw e;
			}
		} catch (SQLException e) {
			LOG.error("Cannot insert event");
			throw e;
		} finally {
			if (null != prepare) {
				prepare.close();
			}
			if (null != connection) {
				connection.close();
			}
		}

	}

}
