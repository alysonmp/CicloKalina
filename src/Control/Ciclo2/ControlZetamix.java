/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

import org.apache.commons.math.analysis.solvers.LaguerreSolver;
import org.apache.commons.math.complex.Complex;


/**
 *
 * @author alysonmp
 */
public class ControlZetamix {
    
    double ter1, ter2, ter3, ter4, Zlm, Zvm, Zm;
    
    public ControlZetamix(double A, double B, double C){
        ter1 = 1;
        ter2 = (C-1);
        ter3 = ((-2*B*C)-(Math.pow(B, 2))-B-C+A);
        ter4 = (Math.pow(B, 2)*C)+(B*C)-(A*B);
        
        double[] coeff = new double[] {ter1, ter2, ter3, ter4};
        
        int real = 1;
        
        LaguerreSolver solver = new LaguerreSolver();
        try{
            Complex[] result = solver.solveAll(coeff, 0);
            for(int i = 0; i < result.length; i++){
                if(!result[0].equals(Double.parseDouble(result[0]+""))){
                    real = 0;
                    break;
                }
            }
        }catch(Exception e){
            
        }
        
        if(real == 1){
            
        }
    }

    public double getZlm() {
        return Zlm;
    }

    public void setZlm(double Zlm) {
        this.Zlm = Zlm;
    }

    public double getZvm() {
        return Zvm;
    }

    public void setZvm(double Zvm) {
        this.Zvm = Zvm;
    }

    public double getZm() {
        return Zm;
    }

    public void setZm(double Zm) {
        this.Zm = Zm;
    }
}
function [Zl, Zv, Z] =Zetamix(A, B, C)
if real>0 
    
    if Z(1,1)>Z(2,1) & Z(1,1)>Z(3,1);
        Z1m=Z(1,1);
            else if Z(2,1)>Z(3,1)
            Z1m=Z(2,1);
            else
            Z1m=Z(3,1);
            end
    end
    if Z(1,1)<Z(2,1) & Z(1,1)<Z(3,1);
        Z2m=Z(1,1);
            else if Z(2,1)<Z(3,1)
            Z2m=Z(2,1);
            else
            Z2m=Z(3,1);
            end
    end
else
  
    if Z(1,1)>Z(2,1) & Z(1,1)>Z(3,1) & isreal(Z(1,1))>0;
        Z1m=Z(1,1);
            else if Z(2,1)>Z(3,1) & isreal(Z(2,1))>0;
            Z1m=Z(2,1);
                else if  Z(3,1)>Z(2,1) & isreal(Z(3,1))>0;
            Z1m=Z(3,1);
                else
                    Z1m=max(Z);
                end
               end 
    end
    if Z(1,1)<Z(2,1) & Z(1,1)<Z(3,1) & isreal(Z(1,1))>0;
        Z2m=Z(1,1);
            else if Z(2,1)<Z(3,1) & isreal(Z(2,1))>0;
            Z2m=Z(2,1);
                else if isreal(Z(3,1))>0;
            Z2m=Z(3,1);
                else  
                    Z2m=min(Z);
                end
            
    end
    
    end
    
      
end
Zl=Z2m;
Zv=Z1m;

real1=isreal(Zl);
real2=isreal(Zv);
if real1==0
    Zl=abs(Zl)
end
if real2==0
    Zv=abs(Zv)
end


end