
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPClientSwing extends JFrame {

    private JTextArea textArea;
    private JTextField inputField;
    private JButton sendButton;
    private JTextField portField;
    private JTextField hostField;
    private JTextField nameField;
    private static DatagramSocket socket;
    private static InetAddress host;
    private int attemptCount = 0;

    public UDPClientSwing() {
        // Initialize components
        textArea = new JTextArea(20, 30);
        inputField = new JTextField(20);
        sendButton = new JButton("Send");
        portField = new JTextField("1234", 5);
        hostField = new JTextField("localhost", 10);
        nameField = new JTextField("Username", 10);

        // Layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Username:"));
        add(nameField);
        add(new JLabel("Host:"));
        add(hostField);
        add(new JLabel("Port:"));
        add(portField);
        add(new JScrollPane(textArea));
        add(inputField);
        add(sendButton);

        // Action for button
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = nameField.getText() + ": " + inputField.getText();
                int port = Integer.parseInt(portField.getText());
                sendMessage(message, port);
            }
        });

        // Default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    // ... [UDPClientSwing sınıfının diğer kısımları] ...

public void sendMessage(String message, int port) {
    try {
        host = InetAddress.getByName(hostField.getText());
        socket = new DatagramSocket();
        socket.setSoTimeout(3000); // 3 seconds timeout

        while (attemptCount < 3) {
            try {
                message = nameField.getText() + ": " + inputField.getText(); // Kullanıcı adını mesaja ekleyin
                DatagramPacket outPacket = new DatagramPacket(message.getBytes(), message.length(), host, port);
                socket.send(outPacket);
                textArea.append("You: " + inputField.getText() + "\n"); // Kullanıcının gönderdiği mesajı ekrana yazdır

                // Receive response
                byte[] buffer = new byte[256];
                DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UDPClientSwing.class.getName()).log(Level.SEVERE, null, ex);
                }
                socket.receive(inPacket);

                String response = new String(inPacket.getData(), 0, inPacket.getLength());
                textArea.append("Server: " + response + "\n"); // Sunucudan gelen yanıtı ekrana yazdır
                attemptCount = 0; // Reset attempt count after successful communication
                break;
            } catch (SocketTimeoutException ste) {
                attemptCount++;
                textArea.append("Attempt " + attemptCount + ": No response from server. Retrying...\n");
            }
        }

        if (attemptCount >= 3) {
            textArea.append("Failed to receive a response after 3 attempts. Server might be down. Closing the program.\n");
            JOptionPane.showMessageDialog(this, "Bağlanamadı", "Hata", JOptionPane.ERROR_MESSAGE); // Hata mesajı göster
            System.exit(1);
        }
    } catch (UnknownHostException uhe) {
        textArea.append("Host error: " + uhe.getMessage() + "\n");
    } catch (SocketException se) {
        textArea.append("Socket error: " + se.getMessage() + "\n");
    } catch (IOException ioe) {
        textArea.append("IO error: " + ioe.getMessage() + "\n");
    } finally {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UDPClientSwing();
            }
        });
    }
}
