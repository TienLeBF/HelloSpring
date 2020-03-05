package org.o7planning.thymeleaf.logic;

import java.sql.SQLException;

import org.o7planning.thymeleaf.dao.impl.Event_DaoImpl;
import org.o7planning.thymeleaf.model.EventRequest;
import org.o7planning.thymeleaf.model.EventResponse;

public class Event_Logic {
    private Event_Logic() {
    }

    public static Long insertEvent(EventRequest eventRequest) throws SQLException {
        try {
            return Event_DaoImpl.insertEvent(eventRequest);
        } catch (SQLException e) {
            throw e;
        }
    }

    public static void updateEvent(EventResponse eventResponse) throws SQLException {
        try {
            Event_DaoImpl.updateEvent(eventResponse);
        } catch (SQLException e) {
            throw e;
        }
    }

}
