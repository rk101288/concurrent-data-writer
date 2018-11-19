package com.richa.concurrentwriter.log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.util.concurrent.TimeUnit.SECONDS;

public class LogManager {
    private String filePath;
    private static final String REPORT = "Received %s unique numbers, %s duplicates. \n Unique total: %s";
    private List<String> currentUnique = new ArrayList<>();

    private long lastUniqueCount;
    private long lastDuplicatedCount;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public LogManager(String filePath) {
        this.filePath = filePath;
    }

    public void writeToLogFile(String message) throws IOException {
        if(!currentUnique.contains(message)) {
            Path path = Paths.get(filePath);
            Files.write(path, (message + System.lineSeparator()).getBytes(UTF_8), APPEND);
            currentUnique.add(message);
            lastUniqueCount ++;
        } else {
            lastDuplicatedCount ++;
        }
    }


    public void printCurrentReport() {
        final Runnable ReportPrinter = () ->  {
            System.out.println(String.format(REPORT, lastUniqueCount, lastDuplicatedCount, currentUnique.size()));
            lastUniqueCount = 0;
            lastDuplicatedCount = 0;
        };

        scheduler.scheduleAtFixedRate(ReportPrinter, 10, 10, SECONDS);
    }
}
