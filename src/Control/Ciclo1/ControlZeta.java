/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

import Control.Ciclo2.ControlCubica;

/**
 *
 * @author leonardo
 */
public class ControlZeta {
    
    private ControlCubica cubica;
    private double Zl, Zv, Z;
    private double ter1, ter2, ter3, ter4, Z1m, Z2m;

    public ControlZeta(double beta, double eps, double delta) {          
        ter1 = 1;
        ter2 = beta;
        ter3 = eps;
        ter4 = delta;
        
        cubica = new ControlCubica();
        cubica.solve(ter1,ter2,ter3,ter4);
        
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
                Z1m = 0;
            }
            
            if((!Double.isNaN(x1) && Double.isNaN(x2) && Double.isNaN(x3)) || (!Double.isNaN(x1) && !Double.isNaN(x2) && Double.isNaN(x3) && x1 < x2) || (x1 < x2 && x1 < x3 && !Double.isNaN(x1))){
                Z2m = x1;
            }else if((!Double.isNaN(x2) && Double.isNaN(x3)) || (x2 < x3 && !Double.isNaN(x2))){
                Z2m = x2;
            }else if(!Double.isNaN(x3)){
                Z2m = x3;
            }else{
                Z2m = 0;
            }
        }
        Zl=Z2m;
        Zv=Z1m;
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

    public double getZ() {
        return Z;
    }

    public void setZ(double Z) {
        this.Z = Z;
    }
    
    
    
}
