package org.o7planning.thymeleaf.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
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
            this.bds = this.database.initConnection(this.bds, this.dbConf);
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
