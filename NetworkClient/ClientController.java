package lesson7_8.NetworkClient;

import javax.swing.*;
import java.io.IOException;
import java.util.function.Consumer;

public class ClientController {

    private final NetworkService networkService;
    private final AuthDialog authDialog;
    private final ClientChat clientChat;
    private String nickname;

    public ClientController(String serverHost, int serverPort) {
        this.networkService = new NetworkService(serverHost, serverPort, this);
        this.authDialog = new AuthDialog(this);
        this.clientChat = new ClientChat(this);
    }

    public void runApplication() throws IOException {
        connectToServer();
        runAuthProcess();
    }

    private void runAuthProcess() {
        networkService.setSuccessfulAuthEvent(new AuthEvent() {
            @Override
            public void authIsSuccessful(String nickname) {
                ClientController.this.setUserName(nickname);
                ClientController.this.openChat();
            }
        });
        authDialog.setVisible(true);

    }

    private void openChat() {
        authDialog.dispose();
        networkService.setMessageHandler(new Consumer<String>() {
            @Override
            public void accept(String message) {
                clientChat.appendMessage(message);
            }
        });
        clientChat.setVisible(true);
    }

    private void setUserName(String nickname) {
        SwingUtilities.invokeLater(() -> {
            clientChat.setTitle(nickname);
        });
        this.nickname = nickname;
    }

    private void connectToServer() throws IOException {
        try {
            networkService.connect();
        } catch (IOException e) {
            System.err.println("Failed to establish server connection");
            throw e;
        }
    }

    public void sendAuthMessage(String login, String pass) throws IOException {
        networkService.sendAuthMessage(login, pass);
    }

    public void sendMessage(String message) {
        try {
            networkService.sendMessage(message);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to send message!");
            e.printStackTrace();
        }
    }

    public void shutdown() {
        networkService.close();
    }

    public String getUsername() {
        return nickname;
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(authDialog, message);
    }

    public void sendPrivateMessage(String username, String message) {
        sendMessage(String.format("/w %s %s", username, message));
    }
}
