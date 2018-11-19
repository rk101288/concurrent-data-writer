package com.richa.concurrentwriter.server;

import com.richa.concurrentwriter.log.LogManager;
import com.richa.concurrentwriter.log.LogMessageValidator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ConcurrentWriterPostHandler implements HttpHandler {
    private LogManager logManager;
    private static final int OKAY_RESPONSE_CODE = 200; //TODO Use a library.
    private static final int BAD_REQUEST_RESPONSE_CODE = 400; //TODO Use a library.

    private LogMessageValidator validator = new LogMessageValidator();

    public ConcurrentWriterPostHandler(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(httpExchange.getRequestBody(), UTF_8);

        List<String> logMessages;

        try (BufferedReader buffer = new BufferedReader(streamReader)) {
            logMessages = buffer.lines().collect(Collectors.toList());
        }

        String response;
        int responseCode;

        if (validator.isValidLogMessage(logMessages)) {
            String logMessage = logMessages.get(0);
            logManager.writeToLogFile(logMessage);

            if (LogMessageValidator.VALID_TERMINATION_MESSAGE.equals(logMessage)) {
                System.exit(0);
            }
            response = "Successfully wrote " + logMessage;
            responseCode = OKAY_RESPONSE_CODE;
        } else {
            System.out.println("Invalid Log Message " + logMessages.stream().
                    map(Object::toString).
                    collect(Collectors.joining("\n")));
            response = ""; //FIXME Requirements say return without comment on bad request. Wouldn't be better to indicate error?
            responseCode = BAD_REQUEST_RESPONSE_CODE;
        }

        httpExchange.sendResponseHeaders(responseCode, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

