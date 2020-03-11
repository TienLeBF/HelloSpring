package org.o7planning.thymeleaf.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
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
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
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
		LOG.info("Person_Controller -> index get -> is called");
		Short statusCode = (short) Constant.STATUS_EVENT.RUNNING.ordinal();
		Short resultCode = (short) Constant.RESULT_EVENT.WAITING.ordinal();
		Long eventId = null;
		try {
			Date requestDate = new Date();
			String eventCode = "index_page";
			EventRequest eventRequest = new EventRequest(requestDate, requestDate, eventCode, null,
					statusCode, resultCode, null, (short) 1, null, null,
					Person_Controller.class.getName(), "index(Model)", null);
			eventId = Event_ServiceImpl.insertEvent(eventRequest);

			model.addAttribute("message", this.message);
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.SUCCESS_FULL.ordinal();

		} catch (Exception e) {
			LOG.error("Person_Controller -> index -> ERROR index page");
			e.printStackTrace();
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.ERROR.ordinal();
		} finally {
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			Date reponseDate = new Date();
			EventResponse eventResponse = new EventResponse(eventId, reponseDate, reponseDate,
					statusCode, resultCode, null, (short) 1, null, null,
					Person_Controller.class.getName(), null);
			try {
				Event_ServiceImpl.updateEvent(eventResponse);
			} catch (Exception e2) {
				LOG.error("Cannot update event_id = " + eventId);
				e2.printStackTrace();
			}
			LOG.info("Person_Controller -> index -> is called -> ended");
		}

		return "index";
	}

	@RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
	public String listPerson(Model model) {
		LOG.info("Person_Controller -> listPerson GET -> is called");
		Short statusCode = (short) Constant.STATUS_EVENT.RUNNING.ordinal();
		Short resultCode = (short) Constant.RESULT_EVENT.WAITING.ordinal();
		Long eventId = null;
		try {
			Date requestDate = new Date();
			String eventCode = "person_list_page";
			EventRequest eventRequest = new EventRequest(requestDate, requestDate, eventCode, null,
					statusCode, resultCode, null, (short) 1, null, null,
					Person_Controller.class.getName(), "listPerson(Model)",
					null);
			eventId = Event_ServiceImpl.insertEvent(eventRequest);

			this.persons = this.getPersonsList();
			model.addAttribute("persons", this.persons);
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.SUCCESS_FULL.ordinal();

		} catch (Exception e) {
			LOG.error("Person_Controller -> listPerson -> ERROR personList page");
			e.printStackTrace();
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.ERROR.ordinal();
		} finally {
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			Date reponseDate = new Date();
			EventResponse eventResponse = new EventResponse(eventId, reponseDate, reponseDate,
					statusCode, resultCode, null, (short) 1, null, null,
					Person_Controller.class.getName(), null);
			try {
				Event_ServiceImpl.updateEvent(eventResponse);
			} catch (Exception e2) {
				LOG.error("Cannot update event_id = " + eventId);
				e2.printStackTrace();
			}
			LOG.info("Person_Controller -> personList -> is called -> ended");
		}

		return "personList";
	}

	@RequestMapping(value = { "/personAdd" }, method = RequestMethod.GET)
	public String personAdd(Model model) {
		LOG.info("Person_Controller -> personAdd GET -> is called");
		Short statusCode = (short) Constant.STATUS_EVENT.RUNNING.ordinal();
		Short resultCode = (short) Constant.RESULT_EVENT.WAITING.ordinal();
		Long eventId = null;
		try {
			Date requestDate = new Date();
			String eventCode = "person_add_page";
			EventRequest eventRequest = new EventRequest(requestDate, requestDate, eventCode, null,
					statusCode, resultCode, null, (short) 1, null, null,
					Person_Controller.class.getName(), "personAdd(Model)",
					null);
			eventId = Event_ServiceImpl.insertEvent(eventRequest);

			PersonForm form = new PersonForm();
			model.addAttribute("personForm", form);
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.SUCCESS_FULL.ordinal();

		} catch (Exception e) {
			LOG.error("Person_Controller -> listPerson -> ERROR personList page");
			e.printStackTrace();
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.ERROR.ordinal();
		} finally {
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			Date reponseDate = new Date();
			EventResponse eventResponse = new EventResponse(eventId, reponseDate, reponseDate,
					statusCode, resultCode, null, (short) 1, null, null,
					Person_Controller.class.getName(), null);
			try {
				Event_ServiceImpl.updateEvent(eventResponse);
			} catch (Exception e2) {
				LOG.error("Cannot update event_id = " + eventId);
				e2.printStackTrace();
			}
			LOG.info("Person_Controller -> personAdd -> is called -> ended");
		}

		return "personAdd";
	}

	@RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
	public String addPerson(Model model, @ModelAttribute("personForm") PersonForm personForm,
			BindingResult bindingResult)
					throws org.springframework.validation.BindException {
		LOG.info("Person_Controller -> personAdd POST -> is called");
		Short statusCode = (short) Constant.STATUS_EVENT.RUNNING.ordinal();
		Short resultCode = (short) Constant.RESULT_EVENT.WAITING.ordinal();
		Long eventId = null;
		try {
			Date requestDate = new Date();
			String eventCode = "add_person_action";
			EventRequest eventRequest = new EventRequest(requestDate, requestDate, eventCode, null,
					statusCode, resultCode, null, (short) 1, null, null,
					Person_Controller.class.getName(), "addPerson(Model, PersonForm)",
					null);
			eventId = Event_ServiceImpl.insertEvent(eventRequest);
			// check valid
			if (bindingResult.hasErrors()) {
				LOG.error("Person_Controller -> personAdd POST -> is bindingResult.hasErrors()");
				throw new BindException(bindingResult);
			}

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
				this.service.insertPersons(personList);

				statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
				resultCode = (short) Constant.RESULT_EVENT.SUCCESS_FULL.ordinal();

				LOG.info("Person_Controller -> addPerson -> successfull inserted person");
				return "redirect:/personList";
			} else {
				LOG.info("Person_Controller -> addPerson -> Cannot insert person");
			}

			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.SUCCESS_FULL.ordinal();

		} catch (SQLException e) {
			LOG.error("Person_Controller -> addPerson -> SQLException -> ERROR personList page");
			e.printStackTrace();
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.ERROR.ordinal();
		} catch (Exception e) {
			LOG.error("Person_Controller -> addPerson -> Exception -> ERROR personList page");
			e.printStackTrace();
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.ERROR.ordinal();
		} finally {
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			Date reponseDate = new Date();
			EventResponse eventResponse = new EventResponse(eventId, reponseDate, reponseDate,
					statusCode, resultCode, null, (short) 1, null, null,
					Person_Controller.class.getName(), null);
			try {
				Event_ServiceImpl.updateEvent(eventResponse);
			} catch (Exception e2) {
				LOG.error("Cannot update event_id = " + eventId);
				e2.printStackTrace();
			}
			LOG.info("Person_Controller -> addPerson -> is called -> ended");
		}

		return "personAdd";
	}

	/**
	 * get list persons
	 *
	 * @return list persons
	 */
	public List<Person> getPersonsList() {
		LOG.info("Person_Controller -> getPersonsList -> is called");
		List<Person> persons = null;
		Short statusCode = (short) Constant.STATUS_EVENT.RUNNING.ordinal();
		Short resultCode = (short) Constant.RESULT_EVENT.WAITING.ordinal();
		Long eventId = null;
		try {
			Date requestDate = new Date();
			String eventCode = "get_person_list_logic";
			EventRequest eventRequest = new EventRequest(requestDate, requestDate, eventCode, null,
					statusCode, resultCode, null, (short) 1, null, null,
					Person_Controller.class.getName(), "getPersonList()", null);
			eventId = Event_ServiceImpl.insertEvent(eventRequest);

			persons = this.service.getPersonsList();
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.SUCCESS_FULL.ordinal();
		} catch (SQLException e) {
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			resultCode = (short) Constant.RESULT_EVENT.ERROR.ordinal();
			LOG.error("Person_Controller -> getPersonsList -> SQLException");
			e.printStackTrace();
		} finally {
			statusCode = (short) Constant.STATUS_EVENT.STOP.ordinal();
			Date reponseDate = new Date();
			EventResponse eventResponse = new EventResponse(eventId, reponseDate, reponseDate, statusCode, resultCode,
					null, (short) 1, null, null, Person_Controller.class.getName(), null);
			try {
				Event_ServiceImpl.updateEvent(eventResponse);
			} catch (Exception e2) {
				LOG.error("Cannot update event_id = " + eventId);
				e2.printStackTrace();
			}
			LOG.info("Person_Controller -> getPersonsList -> is called -> ended");
		}

		return persons;
	}

	/**
	 * insert list persons
	 *
	 * @return true in case of successfull otherwise false
	 */
	public boolean insertPersons(List<Person> persons) {
		LOG.info("Person_Controller -> insertPersons -> is called");
		boolean result = false;
		Short statusCode = (short) Constant.STATUS_EVENT.RUNNING.ordinal();
		Short resultCode = (short) Constant.RESULT_EVENT.WAITING.ordinal();
		Long eventId = null;
		try {
			Date requestDate = new Date();
			String eventCode = "insert_person_logic";
			EventRequest eventRequest = new EventRequest(requestDate, requestDate, eventCode, null,
					statusCode, resultCode, null, (short) 1, null, null,
					Person_Controller.class.getName(), "insertPerson(List<Person>)", null);

			result = this.service.insertPersons(persons);
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
					null, (short) 1, null, null, Person_Controller.class.getName(), null);
			try {
				Event_ServiceImpl.updateEvent(eventResponse);
			} catch (Exception e2) {
				LOG.error("Cannot update event_id = " + eventId);
				e2.printStackTrace();
			}
			LOG.info("Person_Controller -> insertPersons -> is called -> ended");
		}

		return result;
	}
}
