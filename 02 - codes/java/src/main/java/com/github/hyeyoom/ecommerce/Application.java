package com.github.hyeyoom.ecommerce;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        final Server server = ServerBuilder.forPort(50051)
                .addService(new ProductInfoImpl())
                .build();

        try {
            server.start();
            System.out.println("넹출발");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.err.println("망했어요!");
                server.shutdown();
            }));
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.err.println("아주 망했답니다.");
        }
    }
}
