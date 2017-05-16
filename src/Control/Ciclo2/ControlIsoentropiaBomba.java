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
public class ControlIsoentropiaBomba {
    
    private double Test, erro, DT, it, S, burbuja, erro1;
    
    public ControlIsoentropiaBomba(double Beff, double P10, double Pref, double Tref, double S9, double H9, double zi, double T9, Session session){
        Test=T9; 
        erro=1;
        DT=0.6;
        it=0;
        
        while(erro >= 0.0001){
            it = it+1;
            if(it > 100){
               // hshshsh*ksksks
            }
            ControlS_SistemaMix SSistemaMix = new ControlS_SistemaMix(Test, P10, Pref, Tref, zi, session);
            S = SSistemaMix.getSL();
            erro = Math.abs((S9-S)/S9);
            burbuja = S9-S;
            if(erro > 0.0001 && burbuja < 0){
                Test = Test-DT;
                DT = DT/2;
                if(DT < 0.005){
                    DT = 0.004987569731;
                }
            }else{
                if(erro > 0.0001 && burbuja > 0){
                    Test = Test+DT;
                    DT = DT/2;
                    if(DT<0.005){
                        DT = 0.002933254;
                    }
                }
            }
        }
        
        erro1 = 1;
        DT = 2;
        it = 0;
    }
    
}


function [T10, S10] = isoentropiabomba(Beff, P10, Pref, Tref, S9, H9, zi, T9)

Test=T9; 
erro=1;
DT=0.6;
it=0;
    while erro>= 0.0001
        it=it+1
        if it>100
            hshshsh*ksksks
        end
       [SL, SV] = S_sistemamix(Test, P10, Pref, Tref, zi);
        S=SL;
        erro=abs((S9-S)/S9);
        Burbuja=S9-S;
        if erro>0.0001 & Burbuja<0
            Test=Test-DT;
            DT=DT/2;
            if DT<0.005;
                DT=0.004987569731;
            }
        elseif erro>0.0001 & Burbuja>0;
                Test=Test+DT;
                DT=DT/2;
                if DT<0.005;
                    DT=0.002933254;
                end
            end
        end
    
% T10=Test;
% [SL10, SV10] = S_sistemamix(T10, P10, Pref, Tref, zi);
% S10=SL10;
erro1=1;
 DT=2;
 it=0;
 while erro1>= 0.001
     xx10=0;
     T10s=Test;
         [HL10, HV10] = H_sistemamix(T10s, P10, Pref, Tref, zi);
         H10s=HL10;
         H10=((H10s-H9)/Beff)+H9;
         while erro1>0.001
             it=it+1
        if it>2000
            hshshsh*ksksks
        end
           [HL10, HV10] = H_sistemamix(Test, P10, Pref, Tref, zi);
            H=HL10;
            erro1=abs((H10-H)/H10);
            Burbuja=H10-H;
            if erro1>0.0001 & Burbuja<0
                Test=Test-DT;
                DT=DT/2;
                if DT<0.005;
                    DT=0.0043978953;
                end
            elseif erro1>0.001 & Burbuja>0;
                    Test=Test+DT;
                    DT=DT/2;
                    if DT<0.005;
                        DT=0.00333695;
                    end
                end
            end
         
 end
T10=Test;
[SL10, SV10] = S_sistemamix(T10, P10, Pref, Tref, zi);
S10=SL10;
[HL10, HV10] = H_sistemamix(T10, P10, Pref, Tref, zi);
H10=HL10;
end
