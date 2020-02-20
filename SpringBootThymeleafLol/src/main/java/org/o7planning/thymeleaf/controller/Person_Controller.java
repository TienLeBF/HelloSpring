package org.o7planning.thymeleaf.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.o7planning.thymeleaf.model.Person;
import org.o7planning.thymeleaf.service.Person_Service;
import org.o7planning.thymeleaf.service.impl.Person_ServiceImpl;

public class Person_Controller {
	private Person_Service service;
	Connection dbSpring;

	public Person_Controller() {
		service = new Person_ServiceImpl();
	}

	/**
	 * @return
	 */
	public List<Person> getListPersons() {
		try {
			return service.getListPersons(dbSpring);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return
	 */
	public boolean insertPersons(List<Person> persons) {
		try {
			return service.insertListPersons(dbSpring, persons);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
