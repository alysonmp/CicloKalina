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
public class ControlBomba {
    
    private double T9, P9, P10, H9, S9;
    
    public ControlBomba(double Beff, double P1, double Pcon, double Tcon, double Pref, double Tref, double zi, Session session){
        T9 = Tcon;
        P9 = Pcon;
        P10 = P1;
        
        ControlH_Sistemamix HSistemamix = new ControlH_Sistemamix(T9, P9, Pref, Tref, zi, session);
        H9 = HSistemamix.getHL();
        
        ControlS_SistemaMix SSistemamix = new ControlS_SistemaMix(T9, P9, Pref, Tref, zi, session);
        S9 = SSistemamix.getSL();
        
        ControlIsoentropiaBomba isoBomba = new ControlIsoentropiaBomba();
    }
    
}

function [S9, H9, S10, H10, T10, P10] = Bomba(Beff, P1, Pcon, Tcon, Pref, Tref, zi)
T9=Tcon;
P9=Pcon;
P10=P1;
[HL9, HV9] = H_sistemamix(T9, P9, Pref, Tref, zi) ;
H9=HL9;
[SL9, SV9] = S_sistemamix(T9, P9, Pref, Tref, zi);
S9=SL9;
[T10s, S10] = isoentropiabomba(Beff, P10, Pref, Tref, S9, H9, zi, T9);
[HL10, HV10] = H_sistemamix(T10s, P10, Pref, Tref, zi);
H10s=HL10;
H10=((H10s-H9)/Beff)+H9;
T10=T10s;
end