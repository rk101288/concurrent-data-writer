package com.richa.concurrentwriter.log;

import java.util.List;

public class LogMessageValidator {
    private static final int VALID_MESSAGE_LENGTH = 9;
    public static final String VALID_TERMINATION_MESSAGE = "terminate";

    public boolean isValidLogMessage(List<String> logMessages) {
        boolean isValid = true;

        if(logMessages == null || logMessages.size() !=  1) { //TODO use Apache library
            isValid = false;
        } else {
            String message = logMessages.get(0);
            if (message.length() != VALID_MESSAGE_LENGTH) {
                isValid = false;
            }

            try {
                if(!VALID_TERMINATION_MESSAGE.equals(message)) {
                    Integer.parseInt(message);
                }
            } catch (NumberFormatException ex) {
                isValid = false;
            }
        }

        return isValid;
    }

}
