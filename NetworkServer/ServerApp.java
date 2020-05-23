package lesson7_8.NetworkServer;

public class ServerApp {

    private static final int PORT = 8190;

    public static void main(String[] args) {
        MyServer server = new MyServer(8190);
        server.start();
    }
}
