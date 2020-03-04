package org.o7planning.thymeleaf.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.o7planning.thymeleaf.SpringBootThymeleafLolApplication;
import org.o7planning.thymeleaf.model.Event;

public class Event_DaoImpl {
    private static final Logger LOG = Logger.getLogger(Event_DaoImpl.class.getSimpleName());

    private Event_DaoImpl() {
    }

    public static Long insertEvent(Event event) throws SQLException {
        Connection connection = null;
        PreparedStatement prepare = null;
        try {

            String query = "insert \ninto monitor.events \n"
                    + "( \n"
                    + "request_at, "
                    + "response_at, "
                    + "modify_at, "
                    + "request_day, "
                    + "status, "
                    + "result_code, "
                    + "result_message, "
                    + "group_event, "
                    + "service, "
                    + "host_request, "
                    + "host_process, "
                    + "class_name, "
                    + "other_data";
            if (null != event.getLastRecordId()) {
                query += ", "
                        + "last_record_id \n";
            }
            query += ") \n"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
            if (null != event.getLastRecordId()) {
                query += ", ?";
            }

            query += ")";
            connection = SpringBootThymeleafLolApplication.MYSQL_MONITOR.getConnection();
            prepare = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            prepare.setTimestamp(1, null == event.getRequestAt() ? null
                    : new Timestamp(event.getRequestAt().getTime()));
            prepare.setTimestamp(2, null == event.getResponseAt() ? null
                    : new Timestamp(event.getResponseAt().getTime()));
            prepare.setTimestamp(3, null == event.getModifyAt() ? null
                    : new Timestamp(event.getModifyAt().getTime()));
            prepare.setTimestamp(4, null == event.getRequestDay() ? null
                    : new Timestamp(event.getRequestDay().getTime()));
            prepare.setShort(5, event.getStatus());
            prepare.setShort(6, event.getResultCode());
            prepare.setString(7, event.getResultMessage());
            prepare.setShort(8, event.getGroupEvent());
            prepare.setString(9, event.getService());
            prepare.setString(10, event.getHostRequest());
            prepare.setString(11, event.getHostProcess());
            prepare.setString(12, event.getClassName());
            prepare.setString(13, event.getOtherData());
            if (null != event.getLastRecordId()) {
                prepare.setLong(14, event.getLastRecordId());
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

    public static void updateEvent(Event event) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            String query = "UPDATE monitor.events \n"
                    + "set response_at = ?, \n"
                    + "modify_at = ?, \n"
                    + "status = ?, \n"
                    + "result_code = ?, \n"
                    + "result_message = ? \n"
                    + "WHERE \n"
                    + " event_id  = ?";

            connection = SpringBootThymeleafLolApplication.MYSQL_MONITOR.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setTimestamp(1, new Timestamp(event.getResponseAt().getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(event.getModifyAt().getTime()));
            preparedStatement.setShort(3, event.getStatus());
            preparedStatement.setShort(4, event.getResultCode());
            preparedStatement.setString(5, event.getResultMessage());
            preparedStatement.setLong(6, event.getEvent_id());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Cannot update event + " + event.getEvent_id());
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
}
