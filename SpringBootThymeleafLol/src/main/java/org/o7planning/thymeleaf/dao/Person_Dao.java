package org.o7planning.thymeleaf.dao;

import java.sql.SQLException;
import java.util.List;

import org.o7planning.thymeleaf.model.Person;

/**
 * @author le
 *
 */
public interface Person_Dao {
    /**
     * @return
     * @throws SQLException
     */
    public List<Person> getUser() throws SQLException;

    /**
     * @return
     * @throws SQLException
     */
    public boolean inserPersons(List<Person> persons) throws SQLException;
}
