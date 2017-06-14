package com.atejandro.examples;

import com.atejandro.examples.config.AppConfig;
import com.atejandro.examples.service.CubeSummationService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by atejandro on 11/06/17.
 */
public class CubeServer {

    private static final Logger logger = Logger.getLogger(CubeServer.class.getName());

    private Server server;

    private String port;

    private AnnotationConfigApplicationContext context;

    public CubeServer(){
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        CubeSummationService service = context.getBean(CubeSummationService.class);
        this.port = context.getEnvironment().getProperty("server.port", "5000");
        this.server = ServerBuilder
                .forPort(Integer.parseInt(this.port))
                .addService(service)
                .build();
        this.port = port;
    }

    private void start() throws IOException {
        server.start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                CubeServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }


    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final CubeServer server = new CubeServer();
        server.start();
        server.blockUntilShutdown();
    }

}
