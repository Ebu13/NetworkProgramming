
package threadornekrunnable;
/*
Thread sınıfından kalıtım ile extends edilmeyip Runnable olarak da implements edilebilir.
Bu durumda ayrı bir Thread nesnesi oluşturulup bu sınıfın nesnesi parametre olarak verilmelidir.
*/
public class ThreadOrnekRunnable implements Runnable{

    
    public static void main(String[] args) {
       ThreadOrnekRunnable nesne1=new ThreadOrnekRunnable();
       Thread t1=new Thread(nesne1);
       t1.start();
       
       ThreadOrnekRunnable nesne2=new ThreadOrnekRunnable();
       Thread t2=new Thread(nesne2);
       t2.start();
    }

    @Override
    public void run() {
         for(int i=0;i<10;i++){
            try {
                System.out.println(Thread.currentThread().getName()+" calisiyor.");
                int bekle=(int) (Math.random() * 1000);
                Thread.sleep(bekle);
            } catch (InterruptedException ex) {
                System.out.println("Thread hatasi"+ex);
            }
        }
    }
    
}
