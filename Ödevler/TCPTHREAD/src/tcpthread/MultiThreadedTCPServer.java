
package tcpthread;

import java.io.*;
import java.net.*;

public class MultiThreadedTCPServer {
    public static void main(String[] args) {
        int port = 6789;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Sunucu başlatıldı, bağlantı bekleniyor...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Yeni istemci bağlandı: " + clientSocket.getInetAddress().getHostAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream())) {

            String clientMessage;
            while ((clientMessage = inFromClient.readLine()) != null) {
                System.out.println("İstemciden gelen: " + clientMessage);
                String response = clientMessage.toUpperCase() + "\n";
                outToClient.writeBytes(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
