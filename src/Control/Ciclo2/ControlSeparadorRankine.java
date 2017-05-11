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
public class ControlSeparadorRankine {
    
    double x, yi, H3, H2, H1, VF, T3, T2;
    
    public ControlSeparadorRankine(double P1, double T1, double zi, double Pref, double Tref){
        T2 = T1;
        T3 = T1;
        
        ControlCompequi compequi = new ControlCompequi(P1, T1);
    }
    
}

function [x, yi, H3, H2, H1, VF, T3, T2] = separador(P1, T1, zi, Pref, Tref)

T2=T1;
T3=T1;

[x, yi]=compequi(P1, T1);

[HL3, HV3] = H_sistemamix(T3, P1, Pref, Tref, x);
H3=HL3;
[HL2, HV2] = H_sistemamix(T2, P1, Pref, Tref, yi);
H2=HV2;
[VF] = flash(P1, T1, zi);

H1=(VF*H2)+((1-VF)*H3);