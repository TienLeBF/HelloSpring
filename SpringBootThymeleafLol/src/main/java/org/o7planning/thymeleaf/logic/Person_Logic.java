package org.o7planning.thymeleaf.logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.o7planning.thymeleaf.dao.Person_Dao;
import org.o7planning.thymeleaf.dao.impl.Person_DaoImpl;
import org.o7planning.thymeleaf.model.Person;

public class Person_Logic {
	private Person_Dao dao;

	public Person_Logic() {
		dao = new Person_DaoImpl();
	}

	public List<Person> getListPersons(Connection connection) throws SQLException {
		try {
			//this.validateData(firstName, lastName, age, sex, email, address);
			return this.dao.getUser(connection);
		} catch (SQLException e) {
			throw e;
		}
	}

	public boolean insertListPerson(Connection connection, List<Person> persons) throws SQLException {
		try {
			return this.dao.inserPersons(connection, persons);
		} catch (SQLException e) {
			throw e;
		}
	}
	public boolean validateData(String firstName, String lastName, short age, short sex, String email, String address) {

		return false;
	}
}
