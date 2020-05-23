package lesson7_8.NetworkClient;

@FunctionalInterface
public interface AuthEvent {
    void authIsSuccessful(String nickname);
}
