package org.o7planning.thymeleaf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

public class Connection_Util {
    private static Logger log = Logger.getLogger(Connection_Util.class.getSimpleName());
    private BasicDataSource bds;

    private String dbConf = Constant.EMPTY;

    public Connection_Util(String config) {
        this.dbConf = config;
    }

    public Connection_Util() {
    }

    public BasicDataSource getBds() {
        return this.bds;
    }

    public void setBds(BasicDataSource bds) {
        this.bds = bds;
    }

    /**
     * Init connection - read config
     *
     * @return
     * @throws IOException
     */
    public boolean initConnection() {
        try {
            // 1.1 Read file config
            // 1.1.1 Check config
            if (null == this.dbConf) {
                log.error("DATA_CONFIG (FILE) IS " + Constant.STRING_NULL);

                return false;
            }

            if (this.dbConf.isEmpty()) {
                log.error("DATA_CONFIG (FILE) IS EMPTY");

                return false;
            }

            File file = new File(this.dbConf);
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

            // Thong tin database
            String url = Constant.EMPTY;
            String user = Constant.EMPTY;
            String password = Constant.EMPTY;
            String database = Constant.EMPTY;
            String initialSize = Constant.DB_INITIAL_SIZE_STR;
            String driverClass = Constant.EMPTY;
            String minPoolSize = Constant.EMPTY;
            String acquireIncrement = Constant.EMPTY;
            String maxPoolSize = Constant.EMPTY;
            String maxStatements = Constant.EMPTY;
            String jdbcURL = Constant.EMPTY;
            // String maxTotal = Constant.EMPTY;
            // xu ly ma hoa
            String encryptKey = prop.getProperty("encrypt-database");
            // Neu khong co ma hoa
            if (null == encryptKey || encryptKey.isEmpty()) {
                url = prop.getProperty("url");
                user = prop.getProperty("user");
                password = prop.getProperty("password");
                database = prop.getProperty("database");

                initialSize = prop.getProperty("initialSize");
                driverClass = prop.getProperty("driverClass");
                minPoolSize = prop.getProperty("minPoolSize");
                acquireIncrement = prop.getProperty("acquireIncrement");
                maxPoolSize = prop.getProperty("maxPoolSize");
                maxStatements = prop.getProperty("maxStatements");
                jdbcURL = prop.getProperty("jdbcURL");

            } else {
                String deCrypt = new String(Files.readAllBytes(file.toPath()),
                        StandardCharsets.UTF_8);
                String[] config = deCrypt.split(System.getProperty("line.separator").toString());
                String[] arr = null;
                for (String properties : config) {
                    if (properties == null || properties.isEmpty() || properties.trim().isEmpty()
                            || properties.trim().charAt(0) == '#' || !properties.contains("=")) {
                        continue;
                    }
                    arr = properties.split("=", 2);
                    switch (arr[0]) {
                    case "url":
                        url = arr[1];
                        break;
                    case "user":
                        user = arr[1];
                    case "password":
                        password = arr[1];
                    case "database":
                        database = arr[1];
                    case "initialSize":
                        initialSize = arr[1];
                    case "driverClass":
                        driverClass = arr[1];
                    case "acquireIncrement":
                        acquireIncrement = arr[1];
                    case "maxPoolSize":
                        maxPoolSize = arr[1];
                        break;
                    case "maxStatements":
                        maxStatements = arr[1];
                        break;
                    case "jdbcURL":
                        jdbcURL = arr[1];
                        break;
                    default:
                        break;
                    }
                }
            }

            // clear connection
            log.info("JDCL URL = " + jdbcURL);
            log.info("user = " + user);
            log.info("password = " + password);
            log.info("database = " + database);
            log.info("initialSize = " + initialSize);
            log.info("driverClass = " + driverClass);
            log.info("minPoolSize = " + minPoolSize);
            log.info("acquireIncrement = " + acquireIncrement);
            log.info("maxPoolSize = " + maxPoolSize);
            log.info("maxStatements = " + maxStatements);
            log.info("jdbcURL = " + jdbcURL);

            if (null != this.bds) {
                this.bds.close();
                this.bds = null;
            }

            // create new connection
            this.bds = new BasicDataSource();
            this.bds.setUrl(jdbcURL);
            this.bds.setUsername(user);
            this.bds.setPassword(password);

            // the settings below are optional
            //            this.bds.setInitialSize(
            //                    null == initialSize ? Constant.DB_INITIAL_SIZE : Integer.parseInt(initialSize));
            this.bds.setInitialSize(Constant.DB_INITIAL_SIZE);
            this.bds.setMaxTotal(Constant.DB_MAXTOTAL);
            this.bds.setMaxIdle(Constant.DB_MAX_IDLE);
            this.bds.setTestOnBorrow(Constant.DB_TEST_ON_BORROW);
            this.bds.setTestWhileIdle(Constant.DB_TEST_WHILE_IDLE);
            this.bds.setRemoveAbandonedOnBorrow(Constant.DB_REMOVE_ABANDONE_ON_BORROW);
            this.bds.setRemoveAbandonedTimeout(Constant.DB_REMOVE_ABANDONED_TIMEOUT);
            this.bds.setValidationQuery(Constant.DB_VALIDATION_QUERY);
            this.bds.setValidationQueryTimeout(Constant.DB_VALIDATION_QUERY_TIMEOUT);

            log.info("Khoi tao connection thanh cong");
            return true;
        } catch (NullPointerException e) {
            log.error("Da co loi xay ra : NullPointerException");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("Da co loi xay ra : IOException");
            e.printStackTrace();
        } catch (Exception e) {
            log.error("Da co loi xay ra : Exception Dbcp error");
            e.printStackTrace();
        }

        log.info("Khoi tao connection khong thanh cong");
        return false;
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
