package org.o7planning.thymeleaf.db;

import java.io.File;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.o7planning.thymeleaf.Exception.ApplicationException;
import org.o7planning.thymeleaf.util.Constant;

public class DatabaseMysql implements Database {
    private final static Logger LOG = Logger.getLogger(DatabaseMysql.class.getSimpleName());

    @Override
    public boolean initConnection(BasicDataSource bds, String configPath)
            throws SQLException, Exception {
        try {

        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        return false;
    }

    @Override
    public String validate(String configPath) throws ApplicationException {
        if (null == configPath) {
            LOG.error("DATA_CONFIG (FILE) IS " + Constant.STRING_NULL);
            throw new ApplicationException("DATA_CONFIG (FILE) IS " + Constant.STRING_NULL);
        }

        if (configPath.isEmpty()) {
            LOG.error("DATA_CONFIG (FILE) IS EMPTY");
            throw new ApplicationException("DATA_CONFIG (FILE) IS EMPTY");
        }

        File file = new File(configPath);
        if (!file.exists()) {
            LOG.error("DATA_CONFIG IS NOT EXIST");
            throw new ApplicationException("DATA_CONFIG IS NOT EXIST");
        }

        if (file.isDirectory()) {
            LOG.error("DATA_CONFIG IS DIRECTORY");
            throw new ApplicationException("DATA_CONFIG IS DIRECTORY");
        }

        return configPath;
    }

}
