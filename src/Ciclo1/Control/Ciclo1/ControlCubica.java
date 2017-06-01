package Ciclo1.Control.Ciclo1;

import javax.swing.JOptionPane;

public class ControlCubica {

    private double x1;
    private double x2;
    private double x3;
    private int nRoots;
    private double D;
    private static final double TWO_PI = 2.0 * Math.PI;
    private static final double FOUR_PI = 4.0 * Math.PI;

    public double getD(){
        return D;
    }
    
    public void setD(double D){
        this.D = D;
    }
    
    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getX3() {
        return x3;
    }

    public void setX3(double x3) {
        this.x3 = x3;
    }

    public ControlCubica() {
    }
    
    public void solve(double a,
            double b,
            double c,
            double d) {

        
        
        /*PolynomialFunction p = new PolynomialFunction(new double[]{a, b, c, d});
        LaguerreSolver solver = new LaguerreSolver();
        
        try {
            Complex[] com = solver.solveAll(new double[]{a, b, c, d}, 0);
            for(int i = 0; i < com.length; i++){
                System.out.println(com[i].getReal());
                System.out.println(com[i].getImaginary());
            }
        } catch (ConvergenceException ex) {
            Logger.getLogger(ControlCubica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FunctionEvaluationException ex) {
            Logger.getLogger(ControlCubica.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        // Verify preconditions.
        if (a == 0.0) {
            JOptionPane.showMessageDialog(null, "nao e cubica");
        }

        // Normalize coefficients.
        double denom = a;
        a = b / denom;
        b = c / denom;
        c = d / denom;

        // Commence solution.
        double a_over_3 = a / 3.0;
        double Q = (3 * b - a * a) / 9.0;
        double Q_CUBE = Q * Q * Q;
        double R = (9 * a * b - 27 * c - 2 * a * a * a) / 54.0;
        double R_SQR = R * R;
        D = Q_CUBE + R_SQR;
        //System.out.println(D);
        if (D < 0.0) {
            // Three unequal real roots.
            nRoots = 3;
            double theta = Math.acos(R / Math.sqrt(-Q_CUBE));
            double SQRT_Q = Math.sqrt(-Q);
            x1 = 2.0 * SQRT_Q * Math.cos(theta / 3.0) - a_over_3;
            x2 = 2.0 * SQRT_Q * Math.cos((theta + TWO_PI) / 3.0) - a_over_3;
            x3 = 2.0 * SQRT_Q * Math.cos((theta + FOUR_PI) / 3.0) - a_over_3;
        } else if (D > 0.0) {
            // One real root.
            nRoots = 1;
            double SQRT_D = Math.sqrt(D);
            double S = Math.cbrt(R + SQRT_D);
            double T = Math.cbrt(R - SQRT_D);
            x1 = (S + T) - a_over_3;
            x2 = Double.NaN;
            x3 = Double.NaN;
        } else {
            // Three real roots, at least two equal.
            nRoots = 3;
            double CBRT_R = Math.cbrt(R);
            x1 = 2 * CBRT_R - a_over_3;
            x2 = x3 = CBRT_R - a_over_3;
        }
    }
}
