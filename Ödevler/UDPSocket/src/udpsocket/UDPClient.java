package udpsocket;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPClient {

    private static DatagramSocket socket;
    private static InetAddress host;

    public static void main(String[] args) {
        try {

            host = InetAddress.getLocalHost();//localhost üzerindeki sunucu ise
            //host = InetAddress.getByName("localhost");//belirtilen sunucu adının adresi
            socket = new DatagramSocket();
            socket.setSoTimeout(3000);//yanıt bekleme süresi ms olarak verilebilir

            Scanner giris = new Scanner(System.in);

            while (true) { //sürekli mesajlaşma devam etmesi için sonsuz döngü (veya kullanıcı exit yazınca durdurulabilir)
                System.out.println("Mesaj yaz:");
                String mesaj = giris.nextLine();
                //girilen veriyi byte olarak datagram pakete dönüştür ve gönder:
                DatagramPacket outPacket = new DatagramPacket(mesaj.getBytes(), mesaj.length(), host, 1234);
                socket.send(outPacket);
                
                //Sunucudan yanıt alma:
                byte[] buffer = new byte[256];
                DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(inPacket);

                //gelen datagram paketini Stringe dönüştür ve ekrana yazdır:
                String gelenMesaj = new String(inPacket.getData(), 0, inPacket.getLength());
                System.out.println("SERVER " + inPacket.getAddress() + ":" + inPacket.getPort() + ":" + gelenMesaj);

            }
        } catch (SocketException ex) {
            System.out.println("Socket hatasi!");
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Hata." + ex.getMessage());
            System.exit(1);
        } finally {
            System.out.println("Baglanti kapatiliyor!\n");
            socket.close();
        }
    }

}
