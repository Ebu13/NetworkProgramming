
package threadornek2;

import java.time.LocalTime;

public class ThreadOrnek2 {
   
    public static void main(String[] args) {
        /*
        2 farklı sınıftan nesneler oluştur, her nesne kendi sınıfındaki run() metodunu çalıştırır.
        Sınıflar Thread olarak türetildiği için aynı anda çalışırlar
        ThreadSaatYaz isimli thread sınıfı 10 defa sistem saatini yazmaya devam ederken, ThreadSayac ise 1'den 10'a kadar sayıları yazmaktadır.
        (Normalde Thread olmadan bu 2 işlem yapılsaydı önce ThreadSaatYaz çalışıp 10 defa saati yazıp işlemini bitirdikten sonra diğer işleme geçilecekti.)
        */
        ThreadSaatYaz t1=new ThreadSaatYaz();
        t1.start();
        ThreadSayac t2=new  ThreadSayac();        
        t2.start();        
    }

}

class ThreadSaatYaz extends Thread {

    /*
    ThreadSaatYaz sınıfındaki run() metodunda 10 defa bilgisayarın anlık saati ekrana yazdırılıyor.
    Her yazdırma arası rastgele 0-2 saniye arası bekleme süresi olacaktır
    */
    
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                LocalTime time = LocalTime.now();
                System.out.println("Saat: " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
                
                int bekle=(int) (Math.random() * 2000);
                sleep(bekle);
            } catch (InterruptedException ex) {
                System.out.println("Thread hatasi"+ex);
            }
        }
    }

}

class ThreadSayac extends Thread{
    /* 
    Burdaki run() metodunda 1'den 10'a kadar sayılar 0-2 saniye aralıklarla yazdırılmaktadır.
    */
     @Override
     public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                System.out.println(i);
                int bekle=(int) (Math.random() * 2000);
                sleep(bekle);
            } catch (InterruptedException ex) {
                System.out.println("Thread hatasi"+ex);
            }
        }
    }
    
}
