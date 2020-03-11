package org.o7planning.thymeleaf.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.o7planning.thymeleaf.model.EventRequest;
import org.o7planning.thymeleaf.model.EventResponse;
import org.o7planning.thymeleaf.service.impl.Event_ServiceImpl;
import org.o7planning.thymeleaf.util.Constant;

public class Connection_Util {
	private static final Logger LOG = Logger.getLogger(Connection_Util.class.getSimpleName());
	private BasicDataSource bds;
	private Database database;
	private String dbConf = Constant.EMPTY;

	public Connection_Util(Database database, String configPath) {
		this.dbConf = configPath;
		this.database = database;
	}

	public void createConnection() throws SQLException, Exception {
		Short statusCode = (short) Constant.STATUS_EVENT.RUNNING.ordinal();
		Short resultCode = (short) Constant.RESULT_EVENT.WAITING.ordinal();
		Long eventId = null;
		try {
			Date requestDate = new Date();
			this.bds = this.database.initConnection(this.bds, this.dbConf);

			// must be - insert events after init connection
			String eventCode = "create_connection_logic";
			EventRequest eventRequest = new EventRequest(requestDate, requestDate, eventCode, null,
					statusCode, resultCode, null, (short) 1, null, null,
					Connection_Util.class.getName(), "createConnection", null);
			eventId = Event_ServiceImpl.insertEvent(eventRequest);

			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.SUCCESS_FULL.ordinal();
		} catch (SQLException e) {
			LOG.error("SQL exception");
			e.printStackTrace();
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.ERROR.ordinal();
			throw e;
		} catch (Exception e) {
			LOG.error("Exception");
			e.printStackTrace();
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.ERROR.ordinal();
			throw e;
		} finally {
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			Date reponseDate = new Date();
			EventResponse eventResponse = new EventResponse(eventId, reponseDate, reponseDate,
					statusCode,
					resultCode, null, (short) 1, null, null, Connection_Util.class.getName(), null);
			try {
				Event_ServiceImpl.updateEvent(eventResponse);
			} catch (SQLException e2) {
				LOG.error("Cannot update event_id = " + eventId);
				throw e2;
			}
		}
	}

	public BasicDataSource getBds() {
		return this.bds;
	}

	public void setBds(BasicDataSource bds) {
		this.bds = bds;
	}

	/**
	 * Close connection
	 *
	 * @throws Exception
	 */
	public void closeDbcpPool() throws SQLException {
		try {
			if (null == this.bds) {
				LOG.info("Khong co connection nao de dong");
			}
			// else
			this.bds.close();
			this.bds = null;

		} catch (SQLException e) {
			LOG.error("Da co loi xay ra - khong the dong connection");
			throw e;
		}
	}

	/**
	 * get connection
	 *
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		try {
			return this.bds.getConnection();
		} catch (SQLException e) {
			LOG.error("Khong the tra ve ket noi db - error");
			throw e;
		}
	}

	public PreparedStatement setPreparedStatement(PreparedStatement preparedStatement, List<Object> params,
			String type) {
		return this.setPreparedStatement(preparedStatement, params);
	}

	/**
	 *
	 * @param preparedStatement
	 * @param params
	 * @return
	 */
	public PreparedStatement setPreparedStatement(PreparedStatement preparedStatement, List<Object> params) {
		try {

			if (null == preparedStatement) {
				LOG.error("Connection_Util -> setPreparedStatement(PreparedStatement) -> preparedStatement = null");
				return null;
			}

			int index = 0;
			for (Object item : params) {
				index++;
				if (null == item) {
					preparedStatement.setNull(index, java.sql.Types.NULL);
				} else if (item instanceof String) {
					preparedStatement.setNString(index, (String) item);
				} else if (item instanceof Integer) {
					preparedStatement.setInt(index, (Integer) item);
				} else if (item instanceof Short) {
					preparedStatement.setShort(index, (Short) item);
				} else if (item instanceof Double) {
					preparedStatement.setDouble(index, (Double) item);
				} else if (item instanceof Float) {
					preparedStatement.setFloat(index, (Float) item);
				} else if (item instanceof Boolean) {
					preparedStatement.setBoolean(index, (Boolean) item);
				} else if (item instanceof Date) {
					preparedStatement.setTimestamp(index, new Timestamp(((Date) item).getTime()));
				} else if (item instanceof java.sql.Date) {
					preparedStatement.setDate(index, (java.sql.Date) item);
				} else if (item instanceof BigDecimal) {
					preparedStatement.setBigDecimal(index, (BigDecimal) item);
				} else if (item instanceof Long) {
					preparedStatement.setLong(index, (Long) item);
				}
			}

			return preparedStatement;
		} catch (Exception e) {
			LOG.error("ERROR - Connection_Util -> setPreparedStatement(PreparedStatement)");
			e.printStackTrace();
			return null;
		}
	}
}
