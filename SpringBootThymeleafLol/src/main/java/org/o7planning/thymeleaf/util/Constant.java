package org.o7planning.thymeleaf.util;

public class Constant {
    private Constant() {

    }

    public static final String BLANK = "";
    public static final String STRING_NULL = "NULL";
    public static final String EMPTY = "";
    public static final String DB_INITIAL_SIZE_STR = "8";

    public static final int DB_INITIAL_SIZE = 8;
    public static final int DB_MAXTOTAL = 100;
    public static final int DB_MAX_IDLE = 1;
    public static final boolean DB_TEST_WHILE_IDLE = true;
    public static final boolean DB_TEST_ON_BORROW = false;
    public static final boolean DB_REMOVE_ABANDONE_ON_BORROW = true;
    public static final int DB_REMOVE_ABANDONED_TIMEOUT = 55;
    public static final String DB_VALIDATION_QUERY = "SELECT 1 FROM DUAL";
    public static final int DB_VALIDATION_QUERY_TIMEOUT = 15;
    public static final int DB_TIME_BETWEEN_EVIECTION_RÚN_M = 60000;

}
