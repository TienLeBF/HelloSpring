package org.o7planning.thymeleaf.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.o7planning.thymeleaf.exception.ApplicationException;
import org.o7planning.thymeleaf.util.Constant;

public class DatabaseMysql implements Database {
	private final static Logger LOG = Logger.getLogger(DatabaseMysql.class.getSimpleName());

	@Override
	public BasicDataSource initConnection(BasicDataSource bds, String configPath)
			throws SQLException, ApplicationException, FileNotFoundException, IOException {
		try {
			// 1.1 validate file config & doc file
			File file = this.validate(configPath);
			Properties prop = new Properties();
			FileInputStream fileInputStream = new FileInputStream(file);
			prop.load(fileInputStream);

			// 1.2 Lay thong tin database trong file config
			// 1.2.1 Liet ke thong tin database can lay
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

			// Kiem tra file ma hoa hay khong
			String keyEncrypt = prop.getProperty("encrypt-database");
			// Neu khong ma hoa
			if (null == keyEncrypt || keyEncrypt.isEmpty()) {
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
				// Neu co ma hoa
				String deCrypt = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
				String[] lines = deCrypt.split(System.getProperty("line.separator").toString());
				String[] item = null;
				for (String line : lines) {
					if (null == line || line.isEmpty() || line.trim().startsWith("#") || !line.contains("=")) {
						continue;
					}

					item = line.split("=", 2);
					switch (item[0]) {
					case "url":
						url = item[1];
						break;
					case "user":
						user = item[1];
					case "password":
						password = item[1];
					case "database":
						database = item[1];
					case "initialSize":
						initialSize = item[1];
					case "driverClass":
						driverClass = item[1];
					case "acquireIncrement":
						acquireIncrement = item[1];
					case "maxPoolSize":
						maxPoolSize = item[1];
						break;
					case "maxStatements":
						maxStatements = item[1];
						break;
					case "jdbcURL":
						jdbcURL = item[1];
						break;
					default:
						break;
					}
				}

			}

			// 1.3 Clear dbs - create new connection
			if (null != bds) {
				bds.close();
				bds = null;
			}

			bds = new BasicDataSource();
			bds.setUrl(jdbcURL);
			bds.setUsername(user);
			bds.setPassword(password);

			// the settings below are optional
			// bds.setInitialSize(
			// null == initialSize ? Constant.DB_INITIAL_SIZE :
			// Integer.parseInt(initialSize));
			bds.setInitialSize(Constant.DB_INITIAL_SIZE);
			bds.setMaxTotal(Constant.DB_MAXTOTAL);
			bds.setMaxIdle(Constant.DB_MAX_IDLE);
			bds.setTestOnBorrow(Constant.DB_TEST_ON_BORROW);
			bds.setTestWhileIdle(Constant.DB_TEST_WHILE_IDLE);
			bds.setRemoveAbandonedOnBorrow(Constant.DB_REMOVE_ABANDONE_ON_BORROW);
			bds.setRemoveAbandonedTimeout(Constant.DB_REMOVE_ABANDONED_TIMEOUT);
			bds.setValidationQuery(Constant.DB_VALIDATION_QUERY);
			bds.setValidationQueryTimeout(Constant.DB_VALIDATION_QUERY_TIMEOUT);

			LOG.info("Khoi tao connection thanh cong");
			return bds;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		} catch (ApplicationException e) {
			throw e;
		}
	}

	@Override
	public File validate(String configPath) throws ApplicationException {
		if (null == configPath) {
			LOG.error("db config file is null");
			throw new ApplicationException("db config file is null");
		}

		if (configPath.isEmpty()) {
			LOG.error("db config file is empty");
			throw new ApplicationException("db config file is empty");
		}

		File file = new File(configPath);
		if (!file.exists()) {
			LOG.error("db config file is not exist");
			throw new ApplicationException("db config file is not exist");
		}

		if (file.isDirectory()) {
			LOG.error("db config file is directory - not a file");
			throw new ApplicationException("db config file is directory - not a file");
		}

		return file;
	}

}
