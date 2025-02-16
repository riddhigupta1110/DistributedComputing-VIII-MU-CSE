import java.io.*;
import java.net.*;
import java.util.*;

public class GroupChatServer {

    private static final int PORT = 12345;
    
    public static void main(String[] args) throws IOException {

        System.out.println("Group Chat Server is running...");
        ServerSocket serverSocket = new ServerSocket(PORT);

        try {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } finally {
            serverSocket.close();
        }
    }

    // Client handler thread
    private static class ClientHandler extends Thread {
        private static Set<ClientHandler> clientHandlers = new HashSet<>();
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Request name from the client
                out.println("Enter your name: ");
                clientName = in.readLine();

                // Add the client to the set of handlers
                synchronized (clientHandlers) {
                    clientHandlers.add(this);
                }

                out.println("Welcome, " + clientName + "! You can start chatting now.");
                
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(clientName + ": " + message);
                    // Broadcast the message to all other clients
                    synchronized (clientHandlers) {
                        for (ClientHandler handler : clientHandlers) {
                            if (handler != this) {
                                handler.out.println(clientName + ": " + message);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (clientName != null) {
                        synchronized (clientHandlers) {
                            clientHandlers.remove(this);
                        }
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
