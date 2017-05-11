/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

/**
 *
 * @author alysonmp
 */
public class ControlH_Depmix {
    
    public ControlH_Depmix(double T, double P, double zi){
        ControlConstantesMix constantesMix = new ControlConstantesMix(T, P, zi);
        
        ControlDadT_Mix dadt_mis = new ControlDadT_Mix(T, P, zi);
    }
    
}

function [HDL, HDV] = H_Depmix(T, P, zi)

[R, am, bm, cm, Am, Bm, Cm, Nm, Mm, Qm, zj, kij, kji] = constantesmix(T, P, zi);
 
[da_dT_mix] =dadT_mix(T, P, zi);
[Zl, Zv, Z] =Zeta(Am, Bm, Cm);


 HDL=(R*T*(Zl-1)-((T*da_dT_mix)-am)*((1/(2*Nm))*(log((Zl+Mm)/(Zl+Qm)))))*100000;
 HDV=(R*T*(Zv-1)-((T*da_dT_mix)-am)*((1/(2*Nm))*(log((Zv+Mm)/(Zv+Qm)))))*100000;

end
