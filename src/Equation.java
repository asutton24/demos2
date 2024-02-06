public class Equation {
    private double[] eq;
    private double[] data = new double[93];
    public Equation(double[] arr){
        eq = arr;
    }
    public Equation(){

    }
    public double getPoint(double x){
        double ans = 0;
            for (int j=0; j<eq.length;j+=2){
                ans+=(eq[j]*Math.pow(x,eq[j+1]));
            }
            return ans;
    }
    public void makeData(double X1, double X2, double XS){
        double ans = 0;
        for (int i =0; i<93 ; i+=1){
            ans = 0;
            for (int j=0; j<eq.length;j+=2){
                ans+=(eq[j]*Math.pow(((X1-XS)+(XS*i)),eq[j+1]));
            }
            data[i]=ans;
        }
    }
    public void printData(){
        for (int i=0;i<93;i++){
            System.out.println(data[i]);
        }
    }
    public double[] getData(){
        return data;
    }
    public double[] getEq(){
        return eq;
    }
}
