package tcpsocket;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * İstemci olarak çalışacak program
 *
 * @author Ahmet Karadoğan
 */
public class TCPClient {

    private static Socket socket = null;

    public static void main(String[] args) {
        //Türkçe karakter sorunu için yazılacak kod:
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
        }

        try {
            //localhost üzerinden 1234 portuna bağlantı oluştur:
            socket = new Socket("localhost", 1234);

            //veya localhost adresini InetAddress olarak alıp oluştur:            
            //InetAddress host=InetAddress.getLocalHost();
            //socket=new Socket(host,1234);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);//sunucuya veri gönderme nesnesi oluştur
            Scanner input = new Scanner(socket.getInputStream());//sunucudan gelen veriyi okuma nesnesi
            
            //Scanner yerine BufferedReader kullanılabilir:
            //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            Scanner giris = new Scanner(System.in);//konsoldan kullanıcıdan veri alma
            while (true) {
                System.out.println("Mesaj yaz:");
                String msj = giris.nextLine();
                out.println(msj);//girilen mesajı sunucuya gönder

                String veri = input.nextLine();//sunucudan gelen cevabı oku
                System.out.println("SERVER:" + veri);
            }

        } catch (IOException ex) {
            System.out.println("Sunucuya bağlanamadı.Hata mesajı:" + ex.getMessage());
            System.exit(1);
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("Bağlantı kapatılamadı!" + ex.getMessage());
                System.exit(1);
            }
        }

    }

}
