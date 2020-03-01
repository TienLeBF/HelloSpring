package org.o7planning.thymeleaf.Exception;

import java.io.FileNotFoundException;

import org.o7planning.thymeleaf.util.Constant;

public class ApplicationException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public String getErrorCode(Exception e) {
        if (e instanceof FileNotFoundException) {
            return "FileNotFoundException";
        }
        return Constant.BLANK;
    }
}
