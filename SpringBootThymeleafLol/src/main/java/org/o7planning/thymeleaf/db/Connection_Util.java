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
        try {
        	Event event = new Event();
        	event.setRequestAt(new Date());
        	event.setResponseAt(new Date());
        	event.setModifyAt(null);
        	event.setRequestDay(new Date());
        	event.setStatus((short)1);
        	event.setResultCode((short)1);
        	event.setResultMessage("test");
        	event.setGroupEvent((short)1);
        	event.setService("thymeleaf");
        	event.setHostRequest("localhost test");
        	event.setHostProcess("localhost test");
        	event.setClassName(Connection.class.getName());
        	event.setOtherData(null);
        	event.setLastRecordId(0);
            this.bds = this.database.initConnection(this.bds, this.dbConf);
            Event_ServiceImpl.insertEvent(event);
        } catch (SQLException e) {
            log.error("SQL exception");
            throw e;
        } catch (Exception e) {
            log.error("Exception");
            throw e;
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
