import java.util.Scanner;
public class Fibonacci extends Thread {     //inherit the Thread Class
    private int x;
    public int answer;                  //will store fibonacci value with thread

    public Fibonacci(int x) {
        this.x = x;
    } //constructor of class Fibonacci

    public void run() {         //to implement the thread
        if (x == 0){
            answer = 0;
        }else if( x == 1 || x == 2 ) {
            answer = 1;
        }else {
            try {                   //exception handling
                /*
                 * Below we are invoking 2 threads to compute separate values
                 * This will for a tree structure
                 */
                Fibonacci f1 = new Fibonacci(x-1);      //recursive calling
                Fibonacci f2 = new Fibonacci(x-2);
                f1.start();
                f2.start();                     //to start a thread
                f1.join();
                f2.join();                      //for wait till child thread complete exection

                answer = f1.answer + f2.answer;
            }catch(InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static int fib(int arg) {         //method for fibonacci without thread
        if (arg <= 1)
            return arg;

        return fib(arg-1) + fib(arg-2);         //recursive calling
    }
    public static void main(String[] args){         //main method
        Scanner sc = new Scanner(System.in) ;       //scanner object for input
        System.out.println("Enter the number : ");
        int inputNum = sc.nextInt();
        try {
            long start = System.currentTimeMillis();        //for timestamping
            Fibonacci f = new Fibonacci(inputNum);          //method call for fibonacci with thread
            f.start();
            f.join();
            System.out.println("Fibonacci number at "+inputNum+" position with Threading is : "+f.answer);
            long stop = System.currentTimeMillis();
            long timeWithThread = stop - start;
            System.out.println("Total time with threading : " + timeWithThread+ " milliseconds");


            long start1 = System.currentTimeMillis();
            int answerWithoutThread = fib(inputNum);        //method call for fibonacci without thread
            System.out.println("Fibonacci number at "+inputNum+" position without Threading is : "+answerWithoutThread);

            long stop1 = System.currentTimeMillis();
            long timeWithoutThread  = stop1 - start1;
            System.out.println("Total time without threading : " + timeWithoutThread+ " milliseconds");

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}