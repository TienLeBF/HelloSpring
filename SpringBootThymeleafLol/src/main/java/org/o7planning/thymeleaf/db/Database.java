package org.o7planning.thymeleaf.db;

import java.io.File;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.o7planning.thymeleaf.exception.ApplicationException;

public interface Database {
    public BasicDataSource initConnection(BasicDataSource bds, String configPath)
            throws SQLException, Exception;

    public File validate(String configPath) throws ApplicationException;
}
