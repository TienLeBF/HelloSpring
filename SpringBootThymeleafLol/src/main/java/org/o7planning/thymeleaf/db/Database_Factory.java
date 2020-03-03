package org.o7planning.thymeleaf.db;

import org.apache.log4j.Logger;

public class Database_Factory {
    private static final Logger LOG = Logger.getLogger(Database_Factory.class.getSimpleName());

    public static Database getDatabase(String type) {
        if (null == type || type.isEmpty()) {
            LOG.info("Kieu database duoc truyen vao la null || empty");
            return null;
        } else if ("MYSQL".equalsIgnoreCase(type)) {
            return new DatabaseMysql();
        } else if ("SQL_SERVER".equalsIgnoreCase(type)) {
            return new DatabaseMysql();
        } else {
            LOG.info("Kieu database duoc truyen vao = " + type + " khong ton tai");
            return null;
        }
    }
}
