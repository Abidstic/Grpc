import Services.UserService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Grpc_server {
    private static final Logger logger = Logger.getLogger(Grpc_server.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(9090).addService(new UserService()).build();
        server.start();
        logger.info("Server started at port : " + server.getPort());
        server.awaitTermination(60, TimeUnit.SECONDS);


    }
}
