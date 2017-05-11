package Control.Ciclo2;

public class ControlZeta {

    ControlCubica cubica;
    Pdevapor pdevapor = new Pdevapor();
    
    private double Zl;
    private double Zv;

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
    
    public void calculaZeta(int linha, double T, double P, double Beta, double Eps, double Delta) {

        double ter1 = 1;
        double ter2 = Beta;
        double ter3 = Eps;
        double ter4 = Delta;

        double Z1m = 0, Z2m = 0;
        cubica = new ControlCubica();
        cubica.solve(ter1, ter2, ter3, ter4);

        double x1 = cubica.getX1();
        double x2 = cubica.getX2();
        double x3 = cubica.getX3();
        
        if (Double.isNaN(x1) || Double.isNaN(x2) || Double.isNaN(x3)) {
            double Ps2 = pdevapor.calculaPs2(T, linha, mysql.recuperaCriticas(linha));
            if(Ps2 > P){
                Z2m = 0;
                Z1m = x1;
            }
            else{
                Z1m = 0;
                Z2m = x1;
            }            
        } 
        else {
            if (x1 > x2 && x1 > x3) {
                Z1m = x1;
            } else if (x2 > x3) {
                Z1m = x2;
            } else {
                Z1m = x3;
            }

            if (x1 < x2 && x1 < x3) {
                Z2m = x1;
            } else if (x2 < x3) {
                Z2m = x2;
            } else {
                Z2m = x3;
            }
        }

        Zl = Z2m;
        Zv = Z1m;
    }
}
