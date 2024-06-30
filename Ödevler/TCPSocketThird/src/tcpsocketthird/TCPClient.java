package tcpsocketthird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class TCPClient {

    private static BufferedReader in;
    private static PrintWriter out;
    private static JFrame frame = new JFrame("Mesajlaşma Uygulaması");
    private static JTextField textField = new JTextField(50);
    private static JTextArea messageArea = new JTextArea(16, 50);
    private static String userName;

    public static void main(String[] args) {
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println(textField.getText());
                textField.setText("");
            }
        });

        String serverAddress = JOptionPane.showInputDialog(
                frame,
                "Sunucu IP adresini girin:",
                "Sunucu Bağlantısı",
                JOptionPane.QUESTION_MESSAGE);

        int port = Integer.parseInt(JOptionPane.showInputDialog(
                frame,
                "Sunucu port numarasını girin:",
                "Port Numarası",
                JOptionPane.QUESTION_MESSAGE));

        while (true) {
            try {
                Socket socket = new Socket(serverAddress, port);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                break;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Bağlantı sağlanamadı: " + e.getMessage(),
                        "Bağlantı Hatası",
                        JOptionPane.ERROR_MESSAGE);
                serverAddress = JOptionPane.showInputDialog(
                        frame,
                        "Sunucu IP adresini girin:",
                        "Sunucu Bağlantısı",
                        JOptionPane.QUESTION_MESSAGE);

                port = Integer.parseInt(JOptionPane.showInputDialog(
                        frame,
                        "Sunucu port numarasını girin:",
                        "Port Numarası",
                        JOptionPane.QUESTION_MESSAGE));
            }
        }

        while (true) {
            userName = JOptionPane.showInputDialog(
                    frame,
                    "Kullanıcı adınızı girin (en az 5 karakter):",
                    "Kullanıcı Adı",
                    JOptionPane.PLAIN_MESSAGE);

            if (userName != null && userName.trim().length() >= 5) {
                out.println(userName);
                try {
                    String response = in.readLine();
                    if (response.startsWith("Kullanıcı adı kabul edildi: ")) {
                        break;
                    } else {
                        JOptionPane.showMessageDialog(
                                frame,
                                response,
                                "Geçersiz Kullanıcı Adı",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "Kullanıcı adı en az 5 karakter olmalıdır.",
                        "Geçersiz Kullanıcı Adı",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        frame.setTitle("Mesajlaşma Uygulaması - " + userName);
        textField.setEditable(true);

        new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        String message = in.readLine();
                        if (message == null) {
                            break;
                        }
                        messageArea.append(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
