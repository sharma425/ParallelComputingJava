import java.util.Scanner;
public class Barrier implements Runnable{       //implement runnable for threading
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in) ;
        System.out.print("Enter the number of threads Barrier waits for : ");
        int num = sc.nextInt();
        for (int n=0; n<num; n++) {
            Thread t = new Thread(new Barrier());       //Creating new Barrier Thread
            t.start();
            try {
                Thread.sleep(1000);         //put thread sleep for 1000 millisecond
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Barrier Completed Waiting for "+num+ " Threads");
        // wait for all threads' run() methods to complete before continuing
    }

    public void run() {
        System.out.println("Barrier is now waiting...");

    }

}