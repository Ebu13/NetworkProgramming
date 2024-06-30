package tcpchatapp;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class TCPChatApp extends JFrame {

    private JTextArea serverTextArea;
    private JTextField serverTextField;
    private JTextArea clientTextArea;
    private JTextField clientTextField;
    private JButton serverSendButton;
    private JButton clientSendButton;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter serverOut;
    private PrintWriter clientOut;
    private BufferedReader serverIn;
    private BufferedReader clientIn;

    public TCPChatApp() {
        setTitle("TCP Chat App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        // Server Panel
        JPanel serverPanel = new JPanel(new BorderLayout());
        serverTextArea = new JTextArea();
        serverTextArea.setEditable(false);
        JScrollPane serverScrollPane = new JScrollPane(serverTextArea);
        serverPanel.add(serverScrollPane, BorderLayout.CENTER);

        serverTextField = new JTextField();
        serverPanel.add(serverTextField, BorderLayout.NORTH);
        
        serverSendButton = new JButton("Send Server");
        serverSendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = serverTextField.getText();
                sendMessage("Server: " + message, serverOut, serverTextArea);
                serverTextField.setText(""); // Clear the text field after sending
            }
        });
        serverPanel.add(serverSendButton, BorderLayout.SOUTH);
        
        add(serverPanel);

        // Client Panel
        JPanel clientPanel = new JPanel(new BorderLayout());
        clientTextArea = new JTextArea();
        clientTextArea.setEditable(false);
        JScrollPane clientScrollPane = new JScrollPane(clientTextArea);
        clientPanel.add(clientScrollPane, BorderLayout.CENTER);

        clientTextField = new JTextField();
        clientPanel.add(clientTextField, BorderLayout.NORTH);
        
        clientSendButton = new JButton("Send Client");
        clientSendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = clientTextField.getText();
                sendMessage("Client: " + message, clientOut, clientTextArea);
                clientTextField.setText(""); // Clear the text field after sending
            }
        });
        clientPanel.add(clientSendButton, BorderLayout.SOUTH);
        
        add(clientPanel);

        setVisible(true);

        startServer();
        startClient();
    }

    private void startServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(1234);
                    serverTextArea.append("Server started. Waiting for connections...\n");

                    clientSocket = serverSocket.accept();
                    serverTextArea.append("Client connected: " + clientSocket.getInetAddress().getHostName() + "\n");

                    serverOut = new PrintWriter(clientSocket.getOutputStream(), true);
                    serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String inputLine;
                    while ((inputLine = serverIn.readLine()) != null) {
                        serverTextArea.append(inputLine + "\n");
                    }
                } catch (IOException ex) {
                    serverTextArea.append("Error: " + ex.getMessage() + "\n");
                } finally {
                    try {
                        if (serverSocket != null) {
                            serverSocket.close();
                        }
                        if (clientSocket != null) {
                            clientSocket.close();
                        }
                    } catch (IOException ex) {
                        serverTextArea.append("Error closing sockets: " + ex.getMessage() + "\n");
                    }
                }
            }
        }).start();
    }

    private void startClient() {
        try {
            clientSocket = new Socket("localhost", 1234);
            clientTextArea.append("Connected to server.\n");

            clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
            clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String inputLine;
                        while ((inputLine = clientIn.readLine()) != null) {
                            clientTextArea.append(inputLine + "\n");
                        }
                    } catch (IOException ex) {
                        clientTextArea.append("Error: " + ex.getMessage() + "\n");
                    }
                }
            }).start();
        } catch (IOException ex) {
            clientTextArea.append("Error: " + ex.getMessage() + "\n");
        }
    }

    private void sendMessage(String message, PrintWriter output, JTextArea outputTextArea) {
        if (output != null) {
            output.println(message);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TCPChatApp();
            }
        });
    }
}
