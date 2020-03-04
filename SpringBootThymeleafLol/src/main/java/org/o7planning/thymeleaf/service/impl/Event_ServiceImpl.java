package org.o7planning.thymeleaf.service.impl;

import java.sql.SQLException;

import org.o7planning.thymeleaf.logic.Event_Logic;
import org.o7planning.thymeleaf.model.Event;

public class Event_ServiceImpl {

    private Event_ServiceImpl() {
    }

    public static Long insertEvent(Event event) throws SQLException {
        try {
            return Event_Logic.insertEvent(event);
        } catch (SQLException e) {
            throw e;
        }
    }

    public static void updateEvent(Event event) throws SQLException {
        try {
            Event_Logic.updateEvent(event);
        } catch (SQLException e) {
            throw e;
        }
    }
}
