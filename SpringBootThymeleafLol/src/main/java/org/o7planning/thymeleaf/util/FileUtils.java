package org.o7planning.thymeleaf.util;

import java.io.File;

import org.jboss.logging.Logger;

public class FileUtils {
    private static final Logger log = Logger.getLogger(FileUtils.class.getSimpleName());
    public static String validatePath(String path) {
        if (null == path) {
            log.error("DATA_CONFIG (FILE) IS " + Constant.STRING_NULL);

            return path;
        }

        if (path.isEmpty()) {
            log.error("DATA_CONFIG (FILE) IS EMPTY");

            return path;
        }

        File file = new File(path);
        if (!file.exists()) {
            log.error("DATA_CONFIG IS NOT EXIST");

            return path;
        }

        if (file.isDirectory()) {
            log.error("DATA_CONFIG IS DIRECTORY");

            return path;
        }

        return Constant.BLANK;
    }
}
