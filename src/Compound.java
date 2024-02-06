public class Compound {
    private Equation a = new Equation();
    private Equation b = new Equation();
    private int op;
    private double[] comData;
    public Compound(Equation x, Equation y, int o){
        a = x;
        b = y;
        op = o;
    }
    public Compound(){

    }

    public double getPoint(double x){
        int y;
        if (op==0){
            double y1 = a.getPoint(x);
            double y2 = b.getPoint(x);
            if (y2!=0){
                return (y1/y2);
            } else {
                return Double.MAX_VALUE;
            }
        } else {
            return (a.getPoint(b.getPoint(x)));
        }
    }
    public void makeCompoundData(double x1, double x2, double xs){
        a.makeData(x1,x2,xs);
        b.makeData(x1,x2,xs);
        if (op==0){
            double[] num = a.getData();
            double[] den = b.getData();
            comData = new double[93];
            for (int i=0;i<93;i++){
                if (den[i]!=0){
                    comData[i]=(num[i]/den[i]);
                } else {
                    comData[i] = (Double.MAX_VALUE);
                }
            }
        } else {
            double[] blob = b.getData();
            double[] eq = a.getEq();
            comData = new double[93];
            for (int i=0;i<93;i++) {
                double ans = 0;
                for (int j = 0; j < a.getEq().length; j+=2) {
                    ans+=(eq[j]*Math.pow(blob[i] , eq[j+1]));
                }
                comData[i]=ans;
            }
        }
    }
    public void printComData(){
        for (int i=0;i<93;i++){
            System.out.println(comData[i]);
        }
    }

    public double[] getComData() {
        return comData;
    }
}
