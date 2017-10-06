package ChatApp;

/**
 * This application is a server for AndroidChat application by daniel0707[@github]
 */
public class Main {

    public static void main(String args[]) {
        ChatServer server = new ChatServer();
        server.serve();
    }

}