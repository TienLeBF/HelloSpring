package org.o7planning.thymeleaf.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.o7planning.thymeleaf.db.Connection_Util;
import org.o7planning.thymeleaf.form.PersonForm;
import org.o7planning.thymeleaf.model.EventRequest;
import org.o7planning.thymeleaf.model.EventResponse;
import org.o7planning.thymeleaf.model.Person;
import org.o7planning.thymeleaf.service.Person_Service;
import org.o7planning.thymeleaf.service.impl.Event_ServiceImpl;
import org.o7planning.thymeleaf.service.impl.Person_ServiceImpl;
import org.o7planning.thymeleaf.util.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Person_Controller {
	private static final Logger LOG = Logger.getLogger(Person_Controller.class.getSimpleName());
	private Person_Service service;
	private List<Person> persons;

	@Value("${welcome.message}")
	private String message;
	@Value("${error.message")
	private String errorMessage;

	public Person_Controller() {
		LOG.info("create a new Person_Controller");
		this.service = new Person_ServiceImpl();
		this.persons = new ArrayList<Person>();
	}

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("message", this.message);

		return "index";
	}

	@RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
	public String listPerson(Model model) {
		this.persons = this.getListPersons();
		model.addAttribute("persons", this.persons);

		return "personList";
	}

	@RequestMapping(value = { "/personAdd" }, method = RequestMethod.GET)
	public String personAdd(Model model) {
		PersonForm form = new PersonForm();
		model.addAttribute("personForm", form);

		return "personAdd";
	}

	@RequestMapping(value = {}, method = RequestMethod.POST)
	public String addPerson(Model model, @ModelAttribute("personForm") PersonForm personForm) {
		try {
			String firstName = personForm.getFirstName();
			String lastName = personForm.getLastName();
			short age = personForm.getAge();
			short sex = personForm.getSex();
			String email = personForm.getEmail();
			String address = personForm.getAddress();

			if (null != firstName && !firstName.isEmpty() && null != lastName && !lastName.isEmpty()) {
				Person person = new Person(firstName, lastName, age, sex, email, address);
				List<Person> personList = new ArrayList<Person>();
				personList.add(person);
				this.service.insertListPersons(this.persons);

				return "personList";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "personAdd";
	}

	/**
	 * get list persons
	 *
	 * @return list persons
	 */
	public List<Person> getListPersons() {
		List<Person> persons = null;
		Short statusCode = (short) Constant.STATUS_EVENT.RUNNING.ordinal();
		Short resultCode = (short) Constant.RESULT_EVENT.WAITING.ordinal();
		Long eventId = null;
		try {
			LOG.info("Person_Controller -> getListPersons -> is called");
			Date requestDate = new Date();
			EventRequest eventRequest = new EventRequest(requestDate, requestDate, statusCode, resultCode, null,
					(short) 1, null, null, Connection_Util.class.getName(), null);
			eventId = Event_ServiceImpl.insertEvent(eventRequest);

			persons = this.service.getListPersons();
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.SUCCESS_FULL.ordinal();
		} catch (SQLException e) {
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.ERROR.ordinal();
			LOG.error("Person_Controller -> getListPersons -> SQLException");
			e.printStackTrace();
		} finally {
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			Date reponseDate = new Date();
			EventResponse eventResponse = new EventResponse(eventId, reponseDate, reponseDate, statusCode, resultCode,
					null, (short) 1, null, null, Connection_Util.class.getName(), null);
			try {
				Event_ServiceImpl.updateEvent(eventResponse);
			} catch (Exception e2) {
				LOG.error("Cannot update event_id = " + eventId);
				e2.printStackTrace();
			}
		}

		return persons;
	}

	/**
	 * insert list persons
	 *
	 * @return true in case of successfull otherwise false
	 */
	public boolean insertPersons(List<Person> persons) {
		boolean result = false;
		Short statusCode = (short) Constant.STATUS_EVENT.RUNNING.ordinal();
		Short resultCode = (short) Constant.RESULT_EVENT.WAITING.ordinal();
		Long eventId = null;
		try {
			LOG.info("Person_Controller -> insertPersons -> is called");
			Date requestDate = new Date();
			EventRequest eventRequest = new EventRequest(requestDate, requestDate, statusCode, resultCode, null,
					(short) 1, null, null, Connection_Util.class.getName(), null);

			result = this.service.insertListPersons(persons);
			eventId = Event_ServiceImpl.insertEvent(eventRequest);
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.SUCCESS_FULL.ordinal();
		} catch (SQLException e) {
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.ERROR.ordinal();
			LOG.error("Person_Controller -> insertPersons ->  SQLException");
			e.printStackTrace();
		} finally {
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			Date reponseDate = new Date();
			EventResponse eventResponse = new EventResponse(eventId, reponseDate, reponseDate, statusCode, resultCode,
					null, (short) 1, null, null, Connection_Util.class.getName(), null);
			try {
				Event_ServiceImpl.updateEvent(eventResponse);
			} catch (Exception e2) {
				LOG.error("Cannot update event_id = " + eventId);
				e2.printStackTrace();
			}
		}

		return result;
	}
}
