/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlH_Dep {

    private double HDL,HDV;
    
    public ControlH_Dep(double T, double P, double ii,Session session) {
        ControlConstantes constantes = new ControlConstantes(T, P, ii, session);
        ControlZeta zeta = new ControlZeta(constantes.getBeta(), constantes.getEps(), constantes.getDelta());
        
        da_dT=(-0.457235*(R^2)*Tc/Pc)*(1+K0*(1-(Tr^0.5))+K1*(1-Tr)*(0.7-Tr))*((K0/(Tr^0.5))+(K1*(3.4-4*Tr)));
        [Ps2] = Pdevapor(T, ii);
        dif=abs((Ps2-P)/P);
        if dif >= 0.0001 & Ps2<P;
            %disp('Esta liquido') ;
            Z=Zl;
             HDL=-((T*(da_dT)-a)/(2*b*(2^0.5)))*log((Zl+B*(1-(2^0.5)))/(Zl+B*(1+(2^0.5))))+ (Zl-1)*R*T;
             HDV=0;
        else if dif >= 0.0001 & Ps2>P;
                %disp('Esta como vapor') ;
                Z=Zv;
                HDL=0;
                HDV=-((T*(da_dT)-a)/(2*b*(2^0.5)))*log((Zv+B*(1-(2^0.5)))/(Zv+B*(1+(2^0.5))))+ (Zv-1)*R*T;
            else dif<0.0001 ;
                %disp('Esta em zona de mistura') ;
                HDL=-((T*(da_dT)-a)/(2*b*(2^0.5)))*log((Zl+B*(1-(2^0.5)))/(Zl+B*(1+(2^0.5))))+ (Zl-1)*R*T;
                HDV=-((T*(da_dT)-a)/(2*b*(2^0.5)))*log((Zv+B*(1-(2^0.5)))/(Zv+B*(1+(2^0.5))))+ (Zv-1)*R*T;
            end
        end
        dif;
        Ps2;
        HDL;
        HDV;
    }
    
}
