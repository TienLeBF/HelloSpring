package org.o7planning.thymeleaf.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

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
            EventRequest eventRequest = new EventRequest(requestDate, requestDate, statusCode,
                    resultCode, null, (short) 1, null, null, Connection_Util.class.getName(), null);

            this.bds = this.database.initConnection(this.bds, this.dbConf);
            eventId = Event_ServiceImpl.insertEvent(eventRequest);
            statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
            resultCode = (short) Constant.RESULT_EVENT.SUCCESS_FULL.ordinal();
        } catch (SQLException e) {
            LOG.error("SQL exception");
            resultCode = (short) Constant.RESULT_EVENT.ERROR.ordinal();
            throw e;
        } catch (Exception e) {
            LOG.error("Exception");
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
            } catch (Exception e2) {
                LOG.error("Cannot update ");
                e2.printStackTrace();
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

}
