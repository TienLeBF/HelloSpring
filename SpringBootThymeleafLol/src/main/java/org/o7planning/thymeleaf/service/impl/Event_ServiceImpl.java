package org.o7planning.thymeleaf.service.impl;

import java.sql.SQLException;

import org.o7planning.thymeleaf.logic.Event_Logic;
import org.o7planning.thymeleaf.model.EventRequest;
import org.o7planning.thymeleaf.model.EventResponse;

public class Event_ServiceImpl {

    private Event_ServiceImpl() {
    }

    public static Long insertEvent(EventRequest eventRequest) throws SQLException {
        try {
            return Event_Logic.insertEvent(eventRequest);
        } catch (SQLException e) {
            throw e;
        }
    }

    public static void updateEvent(EventResponse eventResponse) throws SQLException {
        try {
            Event_Logic.updateEvent(eventResponse);
        } catch (SQLException e) {
            throw e;
        }
    }
}
