package com.richa.concurrentwriter;

import com.richa.concurrentwriter.server.ConcurrentWriterHttpServer;
import com.richa.concurrentwriter.server.ConcurrentWriterPostHandler;
import com.richa.concurrentwriter.log.LogManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final String FilePath = "numbers.log";

    public static void main(String[] args) throws IOException {
        Path path = Paths.get(FilePath);

        OutputStream out = Files.newOutputStream(path);
        out.close();

        LogManager logManager = new LogManager(FilePath);

        ConcurrentWriterPostHandler handler = new ConcurrentWriterPostHandler(logManager);

        ConcurrentWriterHttpServer server = new ConcurrentWriterHttpServer(handler);
        server.startServer();

        Runtime.getRuntime().addShutdownHook(new Thread(server::stopServer));
        logManager.printCurrentReport();
    }
}
