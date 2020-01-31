package org.o7planning.thymeleaf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

public class Connection_Util {
    private static Logger log = Logger.getLogger(Connection_Util.class.getSimpleName());
    private BasicDataSource bds;

    private static String DATABASE_CONFIG = Constant.EMPTY;

    public Connection_Util(String config) {
        DATABASE_CONFIG = config;
    }

    public Connection_Util() {
    }

    public BasicDataSource getBds() {
        return this.bds;
    }

    public void setBds(BasicDataSource bds) {
        this.bds = bds;
    }

    public Connection getConnection() throws SQLException {
        try {
            return this.bds.getConnection();
        } catch (SQLException e) {
            throw e;

        }
    }

    private boolean initConnection() throws IOException {
        try {
            // 1.1 Read file config
            // 1.1.1 Check config
            if (null == DATABASE_CONFIG) {
                log.error("DATA_CONFIG (FILE) IS " + Constant.STRING_NULL);

                return false;
            }

            if (DATABASE_CONFIG.isEmpty()) {
                log.error("DATA_CONFIG (FILE) IS EMPTY");

                return false;
            }

            File file = new File(DATABASE_CONFIG);
            if (!file.exists()) {
                log.error("DATA_CONFIG IS NOT EXIST");

                return false;
            }

            if (file.isDirectory()) {
                log.error("DATA_CONFIG IS DIRECTORY");

                return false;
            }

            // 1.1.2 Read file
            log.info("FILE ABSOLUTEPATH: " + file.getAbsolutePath());
            Properties prop = new Properties();
            FileInputStream fileInputStream = new FileInputStream(file);
            prop.load(fileInputStream);

            // xu ly ma hoa
            String encrypt = prop.getProperty("encrypt-database");

        } catch (Exception e) {
            log.error("Da co loi xay ra");
            e.printStackTrace();
        }
        return true;
    }
}
