package ChatApp;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Responsible for attempting network connection
 */
class ChatServer {
    void serve() {
        try {
            ServerSocket ss = new ServerSocket(0, 2);
            System.out.println("I have socket " + ss.getLocalPort());

            while(!Thread.interrupted()) {
                Socket s = ss.accept();
                System.out.println("Accepted new connection");
                CommandInterpreter ci = new CommandInterpreter(s.getInputStream(), new PrintStream(s.getOutputStream(), true));
                Thread t = new Thread(ci);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
