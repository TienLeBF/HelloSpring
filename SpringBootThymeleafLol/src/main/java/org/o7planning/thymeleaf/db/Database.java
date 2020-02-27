package org.o7planning.thymeleaf.db;

import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.o7planning.thymeleaf.Exception.ApplicationException;

public interface Database {
    public boolean initConnection(BasicDataSource bds, String configPath)
            throws SQLException, Exception;

    public String validate(String configPath) throws ApplicationException;
}
