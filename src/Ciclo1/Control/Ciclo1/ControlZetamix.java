package Ciclo1.Control.Ciclo1;

public class ControlZetamix {

    ControlCubica cubica;
    
    private double Zl;
    private double Zv;
    private double C;
    
    private double ter1, ter2, ter3, ter4, Z1m, Z2m;

    public ControlZetamix(double A, double B, double C) {
        ter1 = 1;
        ter2 = (C-1);
        //ter3=((-2*B*C)-(B^2)-B-C+A);
        ter3 = (-2*B*C)-Math.pow(B, 2)-B-C+A;
        //ter4=((B^2)*C)+(B*C)-(A*B);
        ter4 = (Math.pow(B, 2)*C)+(B*C)-(A*B);
        
        cubica = new ControlCubica();
        cubica.solve(ter1, ter2, ter3, ter4);

        double x1 = cubica.getX1();
        double x2 = cubica.getX2();
        double x3 = cubica.getX3();
            
        if (!Double.isNaN(x1) && !Double.isNaN(x2) && !Double.isNaN(x3)) {
            if(x1 > x2 && x1 > x3){
                Z1m = x1;
            }else if(x2 > x3){
                Z1m = x2;
            }else{
                Z1m = x3;            
            }
            
            if (x1 < x2 && x1 < x3) {
                Z2m = x1;
            }else if (x2 < x3) {
                Z2m = x2;
            }else {
                Z2m = x3;
            }
        }else{
            if((!Double.isNaN(x1) && Double.isNaN(x2) && Double.isNaN(x3)) || (!Double.isNaN(x1) && !Double.isNaN(x2) && Double.isNaN(x3) && x1 > x2) || (x1 > x2 && x1 > x3 && !Double.isNaN(x1))){
                Z1m = x1;
            }else if((!Double.isNaN(x2) && Double.isNaN(x3)) || (x2 > x3 && !Double.isNaN(x2))){
                Z1m = x2;
            }else if(!Double.isNaN(x3)){
                Z1m = x3;
            }else{
                Z1m = getMax(x1+"", x2+"", x3+"");
            }
            
            if((!Double.isNaN(x1) && Double.isNaN(x2) && Double.isNaN(x3)) || (!Double.isNaN(x1) && !Double.isNaN(x2) && Double.isNaN(x3) && x1 < x2) || (x1 < x2 && x1 < x3 && !Double.isNaN(x1))){
                Z2m = x1;
            }else if((!Double.isNaN(x2) && Double.isNaN(x3)) || (x2 < x3 && !Double.isNaN(x2))){
                Z2m = x2;
            }else if(!Double.isNaN(x3)){
                Z2m = x3;
            }else{
                Z2m = getMin(x1+"", x2+"", x3+"");
            }
        }
        Zl = Z2m;
        Zv = Z1m;
    }
    
    public double getMax(String x1, String x2, String x3){
        String[] x1_ = x1.split(" ");
        String[] x2_ = x2.split(" ");
        String[] x3_ = x3.split(" ");
        
        double x1d = Double.parseDouble(x1_[0]);
        double x2d = Double.parseDouble(x2_[0]);
        double x3d = Double.parseDouble(x3_[0]);
        
        if(x1d > x2d && x1d > x3d){
            return x1d;
        }else if(x2d > x3d){
            return x2d;
        }else{
            return x3d;
        }
    }
    
    public double getMin(String x1, String x2, String x3){
        String[] x1_ = x1.split(" ");
        String[] x2_ = x2.split(" ");
        String[] x3_ = x3.split(" ");
        
        double x1d = Double.parseDouble(x1_[0]);
        double x2d = Double.parseDouble(x2_[0]);
        double x3d = Double.parseDouble(x3_[0]);
        
        if(x1d < x2d && x1d < x3d){
            return x1d;
        }else if(x2d < x3d){
            return x2d;
        }else{
            return x3d;
        }
    }
    
    public double getZl() {
        return Zl;
    }

    public void setZl(double Zl) {
        this.Zl = Zl;
    }

    public double getZv() {
        return Zv;
    }

    public void setZv(double Zv) {
        this.Zv = Zv;
    }

    public double getZ1m() {
        return Z1m;
    }

    public void setZ1m(double Z1m) {
        this.Z1m = Z1m;
    }

    public double getZ2m() {
        return Z2m;
    }

    public void setZ2m(double Z2m) {
        this.Z2m = Z2m;
    }
}