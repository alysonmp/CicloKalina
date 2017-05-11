/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ControlH_Sistemamix {
    
    private double zj, HDrefLj, HDrefVj, HDrefLi, HDrefVi;
    private Session session;
    
    public ControlH_Sistemamix(double T, double P, double Pref, double Tref, double zi, ControlCompequi compequi, Session session){
        this.session = session;
        zj = 1-zi;
        
        ControlH_Dep hdep = new ControlH_Dep(Tref, Pref, 0, this.session);
        HDrefLj = hdep.getHDL();
        HDrefVj = hdep.getHDV();
        
        hdep = new ControlH_Dep(Tref, Pref, 1, this.session);
        HDrefLi = hdep.getHDL();
        HDrefVi = hdep.getHDV();
        
        ControlH_ideal_Gas_Mix hideal = new ControlH_ideal_Gas_Mix(T, Tref, zi, this.session);
    }
    
}

function [HL, HV] = H_sistemamix(T, P, Pref, Tref, zi) 
zj=1-zi;
[HDL, HDV] = H_Dep(Tref, Pref, 0);
HDrefLj=HDL;
HDrefVj=HDV;

[HDL, HDV] = H_Dep(Tref, Pref, 1);
HDrefLi=HDL;
HDrefVi=HDV;
 
[Higmix] = H_Ideal_gas_mix(T, Tref, zi);

[HDL, HDV] = H_Depmix(T, P, zi);

 HL=-(zi*HDrefLi+zj*HDrefLj)+Higmix+HDL;
 HV=-(zi*HDrefLi+zj*HDrefLj)+Higmix+HDV;
end
