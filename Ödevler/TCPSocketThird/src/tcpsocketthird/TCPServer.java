package tcpsocketthird;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer {

    private static final int PORT = 1234;
    private static Set<String> userNames = Collections.synchronizedSet(new HashSet<>());
    private static Set<PrintWriter> clientWriters = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sunucu Yönetim Konsolu");
        JTextArea serverLog = new JTextArea(16, 50);
        serverLog.setEditable(false);
        frame.getContentPane().add(new JScrollPane(serverLog), BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverLog.append("Sunucu başlatıldı. Bağlantı bekleniyor...\n");

            while (true) {
                new ClientHandler(serverSocket.accept(), serverLog).start();
            }
        } catch (IOException e) {
            serverLog.append("Sunucu başlatılamadı: " + e.getMessage() + "\n");
        }
    }

    private static class ClientHandler extends Thread {

        private Socket socket;
        private String userName;
        private PrintWriter out;
        private JTextArea serverLog;

        public ClientHandler(Socket socket, JTextArea serverLog) {
            this.socket = socket;
            this.serverLog = serverLog;
        }

        public void run() {
            serverLog.append("Yeni bağlantı: " + socket + "\n");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                this.out = out;

                // Kullanıcı adı alma
                while (true) {
                    out.println("Kullanıcı adı girin (en az 5 karakter):");
                    userName = in.readLine();
                    if (userName == null || userName.trim().length() < 5 || userNames.contains(userName)) {
                        out.println("Geçersiz veya zaten kullanılan kullanıcı adı, tekrar deneyin.");
                    } else {
                        synchronized (userNames) {
                            if (!userNames.contains(userName)) {
                                userNames.add(userName);
                                break;
                            }
                        }
                    }
                }

                out.println("Kullanıcı adı kabul edildi: " + userName);
                serverLog.append(userName + " katıldı.\n");
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                // Gelen mesajları diğer istemcilere iletme
                String message;
                while ((message = in.readLine()) != null) {
                    serverLog.append(userName + ": " + message + "\n");
                    synchronized (clientWriters) {
                        for (PrintWriter writer : clientWriters) {
                            writer.println(userName + ": " + message);
                        }
                    }
                }

            } catch (IOException e) {
                serverLog.append("Bağlantı hatası: " + e.getMessage() + "\n");
            } finally {
                if (out != null) {
                    synchronized (clientWriters) {
                        clientWriters.remove(out);
                    }
                }
                if (userName != null) {
                    serverLog.append(userName + " ayrıldı.\n");
                    synchronized (userNames) {
                        userNames.remove(userName);
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    serverLog.append("Soket kapatılamadı: " + e.getMessage() + "\n");
                }
            }
        }
    }
}
