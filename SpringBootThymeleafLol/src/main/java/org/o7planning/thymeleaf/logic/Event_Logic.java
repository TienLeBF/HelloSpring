package org.o7planning.thymeleaf.logic;

import java.sql.SQLException;

import org.o7planning.thymeleaf.dao.impl.Event_DaoImpl;
import org.o7planning.thymeleaf.model.Event;

public class Event_Logic {
    private Event_Logic() {
    }

    public static Long insertEvent(Event event) throws SQLException {
        try {
            return Event_DaoImpl.insertEvent(event);
        } catch (SQLException e) {
            throw e;
        }
    }

    public static void updateEvent(Event event) throws SQLException {
        try {
            Event_DaoImpl.updateEvent(event);
        } catch (SQLException e) {
            throw e;
        }
    }

}
