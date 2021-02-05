package org.example.plantsmap.exception;

import java.util.HashMap;
import java.util.Map;

public class InvalidDataException extends Exception{
    private Map<String, String> errorsMap;


    public InvalidDataException(String message) {
        super(message);
        this.errorsMap = new HashMap<>();
        errorsMap.put("message", message);
    }

    public InvalidDataException(String message, Map<String, String> errorsMap) {
        super(message);
        this.errorsMap = errorsMap;
    }
}
