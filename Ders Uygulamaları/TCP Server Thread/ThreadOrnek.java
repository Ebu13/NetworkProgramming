
package threadornek;

public class ThreadOrnek extends Thread{ //Thread sınıfından kalıtım alınır
    
    public static void main(String[] args) {
        
        //Aynı sınıftan 2 farklı nesne oluştur
        //her iki nesne de Thread sınıfından override edilen run() metodunu çağırır.
        ThreadOrnek thread1=new ThreadOrnek();
        ThreadOrnek thread2=new ThreadOrnek();
        thread1.start();//thread1 için run() çağrılır
        thread2.start();//thread2 için run() çağrılır, thread1 bitmesini beklemeden ikisi de çalışmaya devam eder
    }
    
    
    @Override
    public void run(){
        
        for(int i=0;i<10;i++){
            try {
                System.out.println(getName()+" calisiyor."); //çağıran thread adını yazdır
                int beklemeSuresi=(int) (Math.random() * 1000); //her adımda rastgele 0-1000 arası milisaniye olarak bekleme süresi belirle
                sleep(beklemeSuresi); //mevcut thread beklemeSuresi kadar durup diğer threadler çalışmaya devam eder
            } catch (InterruptedException ex) {
                System.out.println("Thread hatasi"+ex);
            }
        }        
    }
     
}


