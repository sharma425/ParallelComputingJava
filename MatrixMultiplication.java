
import java.util.Scanner;

public class MatrixMultiplication{
    //Declaration the matrices
    static float[][] matrix1;
    static float[][] matrix2;
    static float[][] resultWithThread;
    static float[][] resultWithoutThread;

    public static void main(String[] args) {
        System.out.print("Enter The Order Of Matrix : ");
        Scanner sc = new Scanner(System.in) ;
        int order = sc.nextInt();               //input order of matrix
        matrix1=new float[order][order];        //Memory Allocation of matrices
        matrix2=new float[order][order];
        resultWithThread = new float[order][order];
        resultWithoutThread = new float[order][order];

        System.out.println("Enter The Values in 1st Matrix");
        matrix1 = matrixInput(order);           //Initialize the matrix
        System.out.println("Enter The Values in 2nd Matrix");
        matrix2 = matrixInput(order);

        long start = System.currentTimeMillis();    //for time stamping

        resultWithoutThread = matrixMultiply(matrix1,matrix2,order);
        //method for matrix multiplication without thread
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long stop = System.currentTimeMillis();
        long timeWithoutThread = stop - start;

        System.out.println("Matrix multiplication without Thread : ");
        matrixDisplay(order,resultWithoutThread);
        System.out.println("Time required for Multiplication without Thread : "+timeWithoutThread +" milliseconds");
        System.out.println();

        //now code for multiplication with thread
        long start1 = System.currentTimeMillis();
        try{                                            //for exception handling
            //Object of multiply Class
            Thread.sleep(10);
            Multiply multiply = new Multiply(order,order);

            //creating Threads object form class MatrixMultilier
            MatrixMultiplier thread1 = new MatrixMultiplier(multiply);
            MatrixMultiplier thread2 = new MatrixMultiplier(multiply);
            MatrixMultiplier thread3 = new MatrixMultiplier(multiply);

            //Implementing threads
            Thread th1 = new Thread(thread1);
            Thread th2 = new Thread(thread2);
            Thread th3 = new Thread(thread3);

            //Starting threads
            th1.start();
            th2.start();
            th3.start();

            //for main thread waiting for child threads to finish
            th1.join();
            th2.join();
            th3.join();

        }catch (Exception e) {
            e.printStackTrace();
        }

        long stop1 = System.currentTimeMillis();
        System.out.println("Matrix multiplication with Thread : ");
        matrixDisplay(order,resultWithThread);
        long timeWithThread = stop1 - start1;
        System.out.println("Time required for Multiplication with Thread : "+timeWithThread+" milliseconds");
    }


    //method for taking matrix input
    public static float[][] matrixInput(int order){
        Scanner S = new Scanner(System.in) ;
        float [][] matrix = new float[order][order];
        for (int i=0;i<order;i++){          //loop for row
            for (int j=0;j<order;j++){       //loop for column
                System.out.println("Enter value of row "+i+" and column "+j);
                matrix[i][j]=S.nextFloat();
            }
        }
        return matrix;
    }

    //method for matrix multiplication without thread
    public static float[][] matrixMultiply(float[][] matrix1,float[][] matrix2,int order){
        float [][] matrix3 = new float[order][order];

        for (int i= 0;i<order;i++){         //loop for row
            for (int j=0;j<order;j++){       //loop for column
                matrix3[i][j] = 0;
                for(int k=0;k<order;k++){
                    //loop for row by column multiplication
                    matrix3[i][j] += matrix1[i][k]*matrix2[k][j];
                }
            }
        }
        return matrix3;
    }

    //method for display the matrix
    public static void matrixDisplay(int order,float[][] matrix){
        for (int i= 0;i<order;i++){         //loop for row
            for (int j=0;j<order;j++){       //loop for column
                System.out.print(matrix[i][j]+"  ");
            }
            System.out.println();
        }
    }


}



//Multiply Class
class Multiply extends MatrixMultiplication {

    private int i;
    private int j;
    private int chance;

    public Multiply(int i, int j){
        this.i=i;
        this.j=j;
        chance=0;
    }

    //Matrix Multiplication Function
    public  void multiplyMatrix(){

        float sum=0;
        int a=0;
        for(a=0;a<i;a++){
            sum=0;
            for(int b=0;b<j;b++){
                sum=sum+matrix1[chance][b]*matrix2[b][a];
            }
            resultWithThread[chance][a]=sum;
        }

        if(chance>=i)
            return;
        chance++;
    }
}//End multiply class

//Thread Class
class MatrixMultiplier implements Runnable {

    private final Multiply mul;

    public MatrixMultiplier(Multiply mul){
        this.mul=mul;
    }

    @Override
    public void run() {
        mul.multiplyMatrix();
    }
}