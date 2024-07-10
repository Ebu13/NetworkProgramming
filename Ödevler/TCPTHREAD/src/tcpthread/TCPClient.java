
package tcpthread;

import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        String serverName = "localhost"; // Sunucu IP adresi
        int port = 6789;

        try (Socket clientSocket = new Socket(serverName, port);
             BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
             BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            System.out.print("Sunucuya göndermek için bir mesaj girin: ");
            String userMessage = inFromUser.readLine();
            outToServer.writeBytes(userMessage + "\n");

            String serverResponse = inFromServer.readLine();
            System.out.println("Sunucudan gelen: " + serverResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
