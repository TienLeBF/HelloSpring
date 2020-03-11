package org.o7planning.thymeleaf.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.o7planning.thymeleaf.SpringBootThymeleafLolApplication;
import org.o7planning.thymeleaf.model.EventRequest;
import org.o7planning.thymeleaf.model.EventResponse;
import org.o7planning.thymeleaf.util.Constant;

public class Event_DaoImpl {
    private static final Logger LOG = Logger.getLogger(Event_DaoImpl.class.getSimpleName());

    private Event_DaoImpl() {
    }

    public static Long insertEvent(EventRequest eventRequest) throws SQLException {
        Connection connection = null;
        PreparedStatement prepare = null;
        try {

            String query = "insert \ninto monitor.events \n"
                    + "( \n"
                    + "request_at, "
                    + "event_code, " + "request_message, "
                    + "status_code, "
                    + "result_code, "
                    + "group_event, "
                    + "service, "
                    + "host_request, "
                    + "class_name, "
                    + "method_name, "
                    + "other_data";
            if (null != eventRequest.getLastRecordId()) {
                query += ", "
                        + "last_record_id \n";
            }
            query += ") \n"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
            if (null != eventRequest.getLastRecordId()) {
                query += ", ?";
            }

            query += ")";
            connection = SpringBootThymeleafLolApplication.MYSQL_MONITOR.getConnection();
            prepare = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            prepare.setTimestamp(1, null == eventRequest.getRequestAt() ? null
                    : new Timestamp(eventRequest.getRequestAt().getTime()));
            prepare.setString(2, eventRequest.getEventCode());
            prepare.setString(3, eventRequest.getRequestMessage());
            prepare.setShort(4, eventRequest.getStatus());
            prepare.setShort(5, eventRequest.getResultCode());
            prepare.setShort(6, eventRequest.getGroupEvent());
            prepare.setString(7, eventRequest.getService());
            prepare.setString(8, eventRequest.getHostRequest());
            prepare.setString(9, eventRequest.getClassName());
            prepare.setString(10, eventRequest.getMethodName());
            prepare.setString(11, eventRequest.getOtherData());
            if (null != eventRequest.getLastRecordId()) {
                prepare.setLong(12, eventRequest.getLastRecordId());
            }

            prepare.executeUpdate();
            try {
                ResultSet generatedKeys = prepare.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return (long) generatedKeys.getInt(1);
                }
            } catch (SQLException e) {
                LOG.error("Cannot insert event");
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
        return null;
    }

    public static void updateEvent(EventResponse eventResponse) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            String query = "UPDATE monitor.events \n"
                    + "set response_at = ?, \n"
                    + "modify_at = ?, \n"
                    + "status_code = ?, \n"
                    + "result_code = ?, \n"
                    + "result_message = ?, \n"
                    + "host_response = ? \n"
                    + "WHERE \n"
                    + " event_id  = ?";

            connection = SpringBootThymeleafLolApplication.MYSQL_MONITOR.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setTimestamp(1, new Timestamp(eventResponse.getResponseAt().getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(eventResponse.getModifyAt().getTime()));
            preparedStatement.setShort(3, eventResponse.getStatus());
            preparedStatement.setShort(4, eventResponse.getResultCode());
            preparedStatement.setString(5, eventResponse.getResultMessage());
            preparedStatement.setString(6, eventResponse.getHostResponse());
            preparedStatement.setLong(7, eventResponse.getEventId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Cannot update event + " + eventResponse.getEventId());
            throw e;
        } finally {
            if (null != preparedStatement) {
                preparedStatement.close();
            }
            if (null != connection) {
                connection.close();
            }
        }
    }

    public static void udpateEventError() throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            Date modifyAt = new Date();
            int statusCode = Constant.STATUS_EVENT.STOP.ordinal();
            int resultCode = Constant.RESULT_EVENT.ERROR.ordinal();
            String resultMessate = "Update events cannot complete. At start service";
            String query = "UPDATE \n"
                    + "\tmonitor.events \n"
                    + "\tSET modify_at = ?,\n"
                    + "\tstatus_code=?,\n"
                    + "\tresult_code=?,\n"
                    + "\tresult_message=?\n"
                    + "WHERE \n"
                    + "\tstatus_code = 1 \n"
                    + "\tAND result_code = 0";
            connection = SpringBootThymeleafLolApplication.MYSQL_MONITOR.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, new Timestamp(modifyAt.getTime()));
            preparedStatement.setShort(2, (short) statusCode);
            preparedStatement.setShort(3, (short) resultCode);
            preparedStatement.setString(4, resultMessate);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
}
