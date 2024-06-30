/*
Serverin aynı anda birden çok client ile iletişim kurabilmesi için Thread kullanılmalıdır.
Client tarafında bunun için ekstra bir işlem yapılmasına gerek yoktur.
Birden fazla client uygulaması oluşturulup hepsi de aynı sunucuya ve aynı port numarasına(1234) bağlanabilir
Her bağlanan client için serverSocket.accept() metodu çalışıp bir ClientHandler nesnesi oluşturulur
*/
package tcpserverthread;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPServerThread {

    private static ServerSocket serverSocket = null;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(1234);
            System.out.println("Sunucu TCP Socket oluşturuldu.Bağlantı bekleniyor..");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println(clientSocket.toString() + " bağlandı.");

                ClientHandler handler = new ClientHandler(clientSocket); //ClientHandler sınıfına client socketi gönderilir
                handler.start();//run() metodu çağrılır
            }
        } catch (IOException ex) {
            System.out.println("Baglanti saglanamadi." + ex.getMessage());
            System.exit(1);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                System.out.println("Bağlantı kapatılamadı!" + ex.getMessage());
                System.exit(1);
            }
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;
    PrintWriter out;
    Scanner input;
    public ClientHandler(Socket socket) {
        try {
            clientSocket = socket;
            out = new PrintWriter(clientSocket.getOutputStream(), true); //istemciye veri gönderme nesnesi
            input = new Scanner(clientSocket.getInputStream());//istemciden veri alma nesnesi 
        } catch (IOException ex) {
            System.out.println("Bağlantı hatasi!" + ex.getMessage());
        }
    }
    @Override
    public void run() {
        String gelenVeri;
        do {
            gelenVeri = input.nextLine();
            //client tarafında gönderilen her mesaja kullanıcı adı da eklenirse isim ve mesajı ayır. 
            //isim>mesaj Örnek format: Ali>Merhaba            
            String isim = gelenVeri.split(">")[0]; 
            String mesaj = gelenVeri.split(">")[1];
            System.out.println(isim + ": " + mesaj);
            out.println(gelenVeri.toUpperCase());
        } while (!gelenVeri.equals("exit")); //client tarafında exit mesajı gelene kadar iletişim devam eder
        if (clientSocket != null) {
            try {
                System.out.println("Baglanti kapatiliyor..");
                clientSocket.close();
            } catch (IOException ex) {
                System.out.println("Bağlantı kapatilamadi!" + ex.getMessage());
            }
        }
    }
}
