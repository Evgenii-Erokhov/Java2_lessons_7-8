package lesson7_8.NetworkServer;

public interface AuthService {

    String getNickByLoginAndPassword(String login, String password);

    void start();
    void stop();

}
