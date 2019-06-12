import web.HttpServer;

public class Main {

    public static void main(String[] args) {
        System.out.println("start http server in 127.0.0.1:8080");
        HttpServer.start(args);
    }
}
