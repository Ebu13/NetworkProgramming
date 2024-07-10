package tcpserver;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Sunucu olarak çalışacak program
 * @author Ahmet Karadoğan 
 */
public class TCPServer {

    private static ServerSocket serverSocket = null;//server socketi global tanımla
    public static void main(String[] args) {

        //Türkçe karakter sorunu için yazılacak kod:
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
        }
        
        try {
            serverSocket = new ServerSocket(1234);
            System.out.println("Sunucu TCP Socket oluşturuldu.Bağlantı bekleniyor..");
            Socket clientSocket = serverSocket.accept();
            System.out.println(clientSocket.toString() + " bağlandı.");
            //System.out.println(clientSocket.getInetAddress().getHostName() + " : " + clientSocket.getPort()+ " baglandi.");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);//istemciye veri gönderme nesnesi
            Scanner input = new Scanner(clientSocket.getInputStream());//istemciden veri alma nesnesi 
            //Scanner yerine BufferedReader kullanılabilir:
            //BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            while (true) { //sürekli iletişim için. (Durdurma şartı eklenebilir. istemciden gelen bir Kapat mesajı gibi)
                String gelenVeri = input.nextLine();//veriyi oku
                System.out.println(clientSocket.getInetAddress().getHostName() + " istemci:" + gelenVeri);//istemci bilgisi ile birlikte ekrana yazdır
                out.println(gelenVeri.toUpperCase());//gelen mesajı büyük harfe çevirip istemciye gönder               
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
