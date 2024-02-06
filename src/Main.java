import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    static int[][] graph = new int[91][31];
    static boolean compound = true;
    static Equation f;
    static Compound h;
    static Equation g;
    public static void display(int[][] a){
        for (int j=30;j>=0;j--){
            for (int i=0;i<91;i++){
                if (a[i][j]==0){
                    System.out.print(" ");
                } else if (a[i][j]==1){
                    System.out.print("O");
                } else if (a[i][j]==2){
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }

    public static double[] rescale(double[] arr, double low, double scale){
        for (int i=0; i<93; i++){
            if (arr[i]!=Double.MAX_VALUE) {
                arr[i] /= scale;
                arr[i] -= low;
            }
        }
        return arr;
    }

    public static double getPoint(double x){
        if (compound){
            return h.getPoint(x);
        } else {
            return f.getPoint(x);
        }

    }
    public static void graphPoints(double[] a, double lx, double xs, double ly, double ys){
        for (int i=1 ; i<92; i++){
            int temp = (int)(a[i]+0.5);
            if (temp>=0 && temp<=30){
               graph[i-1][temp]=2;
            }
        }
        for (int i=1; i<92; i++){
            if (a[i]!=Double.MAX_VALUE && a[i+1]!=Double.MAX_VALUE){
                int one = (int)(a[i]+0.5);
                int two = (int)(a[i+1]+0.5);
                if (Math.abs(one-two)>1) {
                    double av = ((lx + (xs * (i-1)) + (lx + (xs * i))) / 2);
                    double average = getPoint(av);
                    int avg = (int) ((average/ys ) - ly + 0.5);
                    if (!(one>30 && two>30) && !(one<0 && two<0)) {
                        if (one > two) {
                            if (one>30){
                                one=30;
                            }
                            if (avg>30){
                                avg=30;
                            }
                            if (avg<0){
                                avg=0;
                            }
                            if (two<0){
                                two=0;
                            }
                            for (int j = one; j > avg; j--) {
                                graph[i - 1][j] = 2;
                            }

                            for (int j = avg; j >= two; j--) {
                                graph[i][j] = 2;
                            }
                        } else {
                            if (one<0){
                                one =0;
                            }
                            if (avg>30){
                                avg=30;
                            }
                            if (avg<0){
                                avg=0;
                            }
                            if (two>30){
                                two = 30;
                            }
                            for (int j = one; j < avg; j++) {
                                graph[i - 1][j] = 2;
                            }
                            if (avg<0){
                                avg=0;
                            }
                            for (int j = avg; j < two; j++) {
                                graph[i][j] = 2;
                            }
                        }
                    }
                }
            }
        }
    }
    public static int[][] drawAxis(double lowX, double highX, double lowY, double highY){
        int[][] axis = new int[91][31];
        double xScale = (highX-lowX)/90;
        double yScale = (highY-lowY)/30;
        double closest = 1000000000000.0;
        double closestI = 0;
        boolean check = false;
        int xAxis=0;
        int yAxis=0;
        if (0<=highX && 0>=lowX){
            for (int i=0; i<91;i++){
                if (lowX+(xScale*i)==0){
                    xAxis= i;
                    i=100;
                    check = true;
                } else if (Math.abs(lowX+(xScale*i))<closest){
                    closest = lowX+xScale*i;
                    closestI = i;
                }
            }
            if (!check){
                xAxis = (int)closestI;
            }
            for (int i=0;i<31;i++){
                axis[xAxis][i]=1;
            }
        }
        check = false;
        if (0<=highY && 0>=lowY){
            for (int i=0; i<31;i++){
                if (lowY+(yScale*i)==0){
                    yAxis= i;
                    i=100;
                    check = true;
                } else if (Math.abs(lowY+(yScale*i))<closest){
                    closest = lowY+yScale*i;
                    closestI = i;
                }
            }
            if (!check){
                xAxis = (int)closestI;
            }
            for (int i=0;i<91;i++){
                axis[i][yAxis]=1;
            }
        }
        return axis;
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("How many terms are in the equation?");
        int len = scan.nextInt();
        double[] fx = new double[len * 2];
        int ex;
        for (int i = 0; i < len; i++) {
            System.out.println("Enter this term's coefficent.");
            fx[2 * i] = scan.nextDouble();
            System.out.println("Enter this term's exponent (as an integer).");
            ex = scan.nextInt();
            fx[(2 * i) + 1] = (double) ex;
        }
        f = new Equation(fx);
        System.out.println("Do you want to enter a second equation?(y/n)");
        String yn = scan.nextLine();
        yn = scan.nextLine();
        if (yn.equals("y")) {
            System.out.println("How many terms are in the equation?");
            len = scan.nextInt();
            double[] gx = new double[len * 2];
            int ex1;
            for (int i = 0; i < len; i++) {
                System.out.println("Enter this term's coefficent.");
                gx[2 * i] = scan.nextDouble();
                System.out.println("Enter this term's exponent (as an integer).");
                ex1 = scan.nextInt();
                gx[(2 * i) + 1] = (double) ex1;
            }
            g = new Equation(gx);
            System.out.println("Type f/g or g/f to divide, fog or gof to take f of g or g of f, or just g or f to do one equation");
            String type = scan.nextLine();
            type = scan.nextLine();
            if (type.equals("f/g")) {
                h = new Compound(f, g, 0);
            } else if (type.equals("g/f")) {
                h = new Compound(g, f, 0);
            } else if (type.equals("fog")) {
                h = new Compound(f, g, 1);
            } else if (type.equals("gof")) {
                h = new Compound(g, f, 1);
            }
        } else {
            compound = false;
        }
        /*for (int i=0;i<100;i++){
            double testvar = scan.nextDouble();
            System.out.println(f.getPoint(testvar));
        }*/
        System.out.println("Enter the lower bound for x.");
        double LowX = scan.nextDouble();
        System.out.println("Enter the upper bound for x.");
        double HighX = scan.nextDouble();
        System.out.println("Enter the lower bound for y.");
        double LowY = scan.nextDouble();
        System.out.println("Enter the upper bound for y.");
        double HighY = scan.nextDouble();
        double Xscale = (HighX - LowX) / 90;
        double Yscale = (HighY - LowY) / 30;
        double[] graphData;
        if (compound){
            h.makeCompoundData(LowX,LowY,Xscale);
            graphData = h.getComData();
        } else {
            f.makeData(LowX, HighX, Xscale);
            graphData = f.getData();
        }
        double ScaledLowY = LowY/Yscale;
        graphData = rescale(graphData, ScaledLowY, Yscale);
        //f.printData();
       // for (int i=0;i<93;i++){
            //System.out.println(graphData[i]);
        //}
        graph = drawAxis(LowX, HighX, LowY, HighY);
        graphPoints(graphData, LowX, Xscale, ScaledLowY, Yscale);
        display(graph);

    }
}