
import java.util.Scanner;

public class MatrixUpdate {
    //Declaration the matrix
    static float[][] matrix;
    static float[][] matrix1;

    public static void main(String[] args) {
        System.out.print("Enter The Order Of Matrix : ");
        Scanner sc = new Scanner(System.in) ;
        int order = sc.nextInt();               //input order of matrix
        matrix=new float[order][order];        //Memory Allocation of matrix
        matrix1=new float[order][order];        //matrix with all 0 values for update flag

        System.out.println("Enter The Values in Matrix");
        matrix = matrixInput(order);       //Initialize the matrix
        System.out.println("Entered Matrix is : ");
        matrixDisplay(order,matrix);

        try{                                            //for exception handling
            //Object of update Class
            Update update = new Update(order,order);

            //creating Threads object form class MatrixUpdater
            MatrixUpdater thread1 = new MatrixUpdater(update);
            MatrixUpdater thread2 = new MatrixUpdater(update);
            MatrixUpdater thread3 = new MatrixUpdater(update);

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

        System.out.println("Updated Matrix is: ");
        matrixDisplay(order,matrix);
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

//Update Class
class Update extends MatrixUpdate {

    private int i;
    private int j;

    public Update(int i, int j){
        this.i=i;
        this.j=j;
    }

    //Matrix Updation Function
    public void updateMatrix(){
        int a=0;
        for(a=0;a<i;a++){
            for (int b=0;b<j;b++){
                if (matrix1[a][b]==0){          //check update flag for prevent double updation
                    if(a>=0&&b!=0){
                        matrix[a][b]=matrix[a][b]-matrix[a][b-1];
                        matrix1[a][b]=1;        //set the update flag
                    }
                    if(a!=0&&b>=0){
                        matrix[a][b]=matrix[a][b]+matrix[a-1][b];
                        matrix1[a][b]=1;
                    }
                    if(a==0&&b==0){
                        matrix[a][b]=matrix[a][b];
                        matrix1[a][b]=1;
                    }
                }
            }
        }
    }
}//End update class

//Thread Class
class MatrixUpdater extends Thread {

    private final Update upd;

    public MatrixUpdater(Update upd){
        this.upd=upd;
    }

    @Override
    public void run() {
        upd.updateMatrix();
    }
}