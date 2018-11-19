package com.richa.concurrentwriter.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentWriterHttpServer {
    private static final int PORT = 4000;
    private ConcurrentWriterPostHandler concurrentWriterPostHandler;
    private HttpServer server;
    private static final int NUMBER_OF_THREADS = 5;
    private ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public ConcurrentWriterHttpServer(ConcurrentWriterPostHandler concurrentWriterPostHandler) {
        this.concurrentWriterPostHandler = concurrentWriterPostHandler;
    }

    public void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.setExecutor(executorService);

        HttpContext context = server.createContext("/concurrentwriter");
        context.setHandler(concurrentWriterPostHandler);

        System.out.println(String.format("Starting up server. POST to http://localhost:%s/concurrentwriter", PORT));
        server.start();
    }

    public void stopServer() {
        System.out.println("Stopping Server");
        server.stop(0);
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println("Server didn't gracefully shutdown");
        }
    }
}
