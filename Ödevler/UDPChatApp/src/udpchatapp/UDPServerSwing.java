/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpchatapp;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

public class UDPServerSwing extends JFrame {

    private JTextArea textArea;
    private JButton startServerButton;
    private JTextField portField;
    private static DatagramSocket socket;

    public UDPServerSwing() {
        // Initialize components
        textArea = new JTextArea(20, 30);
        startServerButton = new JButton("Start Server");
        portField = new JTextField("1234", 5);

        // Layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Port:"));
        add(portField);
        add(new JScrollPane(textArea));
        add(startServerButton);

        // Action for button
        startServerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int port = Integer.parseInt(portField.getText());
                startServer(port);
            }
        });

        // Default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void startServer(int port) {
        try {
            socket = new DatagramSocket(port);
            textArea.append("Server started on port: " + port + "\n");

            new Thread(() -> {
                while (true) {
                    try {
                        byte[] buffer = new byte[256];
                        DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
                        socket.receive(inPacket);

                        String message = new String(inPacket.getData(), 0, inPacket.getLength());
                        InetAddress clientAddress = inPacket.getAddress();
                        int clientPort = inPacket.getPort();

                        textArea.append("Client (" + clientAddress + ":" + clientPort + "): " + message + "\n"); // Client'tan gelen mesajı ekrana yazdır

                        // Echo the message back to the client
                        DatagramPacket outPacket = new DatagramPacket(message.getBytes(), message.length(), clientAddress, clientPort);
                        socket.send(outPacket);
                    } catch (IOException ex) {
                        textArea.append("Error: " + ex.getMessage() + "\n");
                    }
                }
            }).start();
        } catch (SocketException ex) {
            textArea.append("Server error: " + ex.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UDPServerSwing();
            }
        });
    }
}
