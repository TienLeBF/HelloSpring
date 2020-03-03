package org.o7planning.thymeleaf.db;

import java.io.File;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.o7planning.thymeleaf.exception.ApplicationException;

public class DatabaseSQLServer implements Database {

    @Override
    public BasicDataSource initConnection(BasicDataSource bds, String configPath)
            throws SQLException, Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File validate(String configPath) throws ApplicationException {
        // TODO Auto-generated method stub
        return null;
    }

}
