package org.o7planning.thymeleaf.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.o7planning.thymeleaf.model.Event;
import org.o7planning.thymeleaf.service.impl.Event_ServiceImpl;
import org.o7planning.thymeleaf.util.Constant;

public class Connection_Util {
    private static Logger log = Logger.getLogger(Connection_Util.class.getSimpleName());
    private BasicDataSource bds;
    private Database database;
    private String dbConf = Constant.EMPTY;


    public Connection_Util(Database database, String configPath) {
        this.dbConf = configPath;
        this.database = database;
    }

    public void createConnection() throws SQLException, Exception {
        Short status = (short) Constant.STATUS_EVENT.RUNNING.ordinal();
        Short resultCode = (short) Constant.RESULT_CODE.WAITING.ordinal();
        Long eventId = null;
        try {
            Event eventRequest = new Event();

            Date requestDate = new Date();
            eventRequest.setRequestAt(requestDate);
            eventRequest.setResponseAt(null);
            eventRequest.setModifyAt(requestDate);
            eventRequest.setRequestDay(requestDate);
            eventRequest.setStatus(status);
            eventRequest.setResultCode(resultCode);
            eventRequest.setResultMessage(null);
            eventRequest.setGroupEvent((short) 1);
            eventRequest.setService(null);
            eventRequest.setHostRequest(null);
            eventRequest.setHostProcess(null);
            eventRequest.setClassName(Connection.class.getName());
            eventRequest.setOtherData(null);
            eventRequest.setLastRecordId(null);

            this.bds = this.database.initConnection(this.bds, this.dbConf);
            eventId = Event_ServiceImpl.insertEvent(eventRequest);
            status = (short) Constant.STATUS_EVENT.STOP.ordinal();
            resultCode = (short) Constant.RESULT_CODE.SUCCESS_FULL.ordinal();
        } catch (SQLException e) {
            log.error("SQL exception");
            resultCode = (short) Constant.RESULT_CODE.ERROR.ordinal();
            throw e;
        } catch (Exception e) {
            log.error("Exception");
            status = (short) Constant.STATUS_EVENT.STOP.ordinal();
            resultCode = (short) Constant.RESULT_CODE.ERROR.ordinal();
            throw e;
        } finally {
            status = (short) Constant.STATUS_EVENT.STOP.ordinal();
            Event eventResponse = new Event();
            Date reponseDate = new Date();
            eventResponse.setEvent_id(eventId);
            eventResponse.setResponseAt(reponseDate);
            eventResponse.setModifyAt(reponseDate);
            eventResponse.setRequestDay(null);
            eventResponse.setStatus(status);
            eventResponse.setResultCode(resultCode);
            eventResponse.setResultMessage(null);
            eventResponse.setGroupEvent((short) 1);
            eventResponse.setService(null);
            eventResponse.setHostRequest(null);
            eventResponse.setHostProcess(null);
            eventResponse.setClassName(Connection.class.getName());
            eventResponse.setOtherData(null);

            try {
                Event_ServiceImpl.updateEvent(eventResponse);
            } catch (Exception e2) {
                log.error("Cannot update ");
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
                log.info("Khong co connection nao de dong");
            }
            // else
            this.bds.close();
            this.bds = null;

        } catch (SQLException e) {
            log.error("Da co loi xay ra - khong the dong connection");
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
            log.error("Khong the tra ve ket noi db - error");
            throw e;
        }
    }

}
