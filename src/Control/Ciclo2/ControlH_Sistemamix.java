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
public class ControlH_Sistemamix {
    
    private double zj;
    
    public ControlH_Sistemamix(double T3, double P1, double Pref, double Tref, double zi){
        zj = 1-zi;
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
