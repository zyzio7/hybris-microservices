package pl.zyskowski.hybris.controller.exception.data;

import java.util.Arrays;
import java.util.List;

public class ExceptionData {

    public String exception;
    public List<String> messages;

    public ExceptionData(Class clazz, List<String> messages) {
        this.exception = clazz.getName();
        this.messages = messages;
    }

    public ExceptionData(Class clazz, String messages) {
        this(clazz, Arrays.asList(messages));
    }

    public ExceptionData(Exception ex) {
        this(ex.getClass(), ex.getMessage());
    }
}
