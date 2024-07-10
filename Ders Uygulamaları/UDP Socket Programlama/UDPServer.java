package udpserver;

import java.io.IOException;
import java.net.*;

public class UDPServer {

    private static DatagramSocket socket;

    public static void main(String[] args) {
        
        try {
            System.out.println("Baglanti olusturuluyor..\n");
            socket = new DatagramSocket(1234);
            System.out.println("Baglanti acildi. Mesaj bekleniyor...\n");
            
            while (true) {
                //Clienttan veri alma:
                byte[] buffer = new byte[256];
                DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(inPacket);

                //gelen datagram paketini String verisine dönüştürme:
                String gelenMesaj = new String(inPacket.getData(), 0, inPacket.getLength());
                
                //client adres ve port bilgisini öğrenme:
                InetAddress clientAddress = inPacket.getAddress();
                int clientPort = inPacket.getPort();
                
                System.out.println(clientAddress+":"+clientPort+">Mesaj alindi:"+gelenMesaj);
                                
                //gelen mesajı büyük harfe çevirip clienta gönderme:
                String gidenMesaj=gelenMesaj.toUpperCase();
                
                DatagramPacket outPacket = new DatagramPacket(gidenMesaj.getBytes(), gidenMesaj.length(), clientAddress, clientPort);
                socket.send(outPacket);
            }

        } catch (SocketException ex) {
            System.out.println("Socket Baglanti Hatası:" + ex.getMessage());
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Hata." + ex.getMessage());
            System.exit(1);
        } finally {
            System.out.println("Baglanti kapatiliyor!\n");
            socket.close();//close için exception kontrolü gerekmediğinden try-catch içine alınmıyor
        }

    }

}
