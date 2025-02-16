import java.io.*;
import java.net.*;

public class GroupChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

        // Create input and output streams
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        // Read name from the user
        System.out.print("Enter your name: ");
        String name = userInput.readLine();
        out.println(name); // Send the name to the server

        // Thread to read messages from the server
        Thread readThread = new Thread(new ReadMessages(in));
        readThread.start();

        // Send messages from the user to the server
        String message;
        while ((message = userInput.readLine()) != null) {
            out.println(message); // Send the message to the server
        }

        socket.close();
    }

    // Class to read incoming messages from the server
    private static class ReadMessages implements Runnable {
        private BufferedReader in;

        public ReadMessages(BufferedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message); // Display the message
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}