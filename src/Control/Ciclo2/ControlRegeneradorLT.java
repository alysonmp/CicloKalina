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
public class ControlRegeneradorLT {

    public ControlRegeneradorLT(double TbolA, double TorvA, double TbolB, double TorvB, double P1, double H7, double H10, double T7, double T10, double Pcon, double Pref, double Tref, double zi, double effLT){
        
    }
}


function [P7, P8, P11, H8, H11, T11, T8, xx8, xi8, yi8, xx11] = RegeneradorLT(TbolA, TorvA, TbolB, TorvB, P1, H7, H10, T7, T10, Pcon, Pref, Tref, zi, effLT)
clc
P7=Pcon;
P8=Pcon;
P11=P1;
P10=P1;
P12=P1;
it=0;
Tbol11=TbolA;
Torv11=TorvA;
T11=T10+(effLT*(T7-T10));

if T11+5>T7
    jaljkfsjklfs*jasdkljdsa
end
if T11<=Tbol11
        xx11=0;
        [HL11, HV11] = H_sistemamix(T11, P11, Pref, Tref, zi);
        H11=HL11;  
        xx11=0;
elseif T11>=Torv11
        xx11=1;
        [HL11, HV11] = H_sistemamix(T11, P11, Pref, Tref, zi);
        H11=HV11; 
          xx11=1;
else
        [xi11, yi11]=compequi(P11, T11);
        [HL, HV11] = H_sistemamix(T11, P11, Pref, Tref, yi11);
        [HL11, HV] = H_sistemamix(T11, P11, Pref, Tref, xi11);
        [xx11] = flash(P11, T11, zi);
        H11=(HV11*xx11)+(HL11*(1-xx11));
end
H8=-(H11-H10)+H7;

erro1=1;

Tbol8=TbolB;
Torv8=TorvB;
Test=T7;
DT=abs((Tbol8-T7)/3);
it=0;
 while erro1>= 0.001
     it=it+1
        if it>1000
            
            hshshsh*ksksks
           
        end
     if Test>=Torv8
            [HL8, HV8] = H_sistemamix(Test, P8, Pref, Tref, zi);
            H=HV8;
            erro1=abs((H8-H)/H8);
            Burbuja=H8-H;
            if erro1>0.001 & Burbuja<0
                Test=Test-DT;
                DT=DT/1.1;
                if DT<0.005;
                    DT=3;
                end
            elseif erro1>0.001 & Burbuja>0;
                    Test=Test+DT;
                    DT=DT/1.1;
                    if DT<0.005;
                        DT=0.00333695;
                    end
                end
         T8=Test;
        
     elseif Test<=Tbol8
            [HL8, HV8] = H_sistemamix(Test, P8, Pref, Tref, zi);
            H=HL8;
            erro1=abs((H8-H)/H8);
            Burbuja=H8-H;
            if erro1>0.001 & Burbuja<0
                Test=Test-DT;
                DT=DT/1.1;
                if DT<0.005;
                    DT=3;
                end
            elseif erro1>0.001 & Burbuja>0;
                    Test=Test+DT;
                    DT=DT/1.1;
                    if DT<0.005;
                        DT=0.00333695;
                    end
                end
           T8=Test;
     else
             [xi8, yi8]=compequi(P8, Test);
             [HL, HV8] = H_sistemamix(Test, P8, Pref, Tref, yi8);
             [HL8, HV] = H_sistemamix(Test, P8, Pref, Tref, xi8);
             [xx8] = flash(P8, Test, zi);
             H=(HV8*xx8)+(HL8*(1-xx8));
             erro1=abs((H8-H)/H8);
             Burbuja=H8-H;
             if erro1>0.001 & Burbuja<0
                Test=Test-DT;
                DT=DT/1.1;
                if DT<0.005;
                    DT=3;
                end
             elseif erro1>0.001 & Burbuja>0;
                    Test=Test+DT;
                    DT=DT/1.1;
                    if DT<0.005;
                        DT=0.00333695;
                    end
                end
            T8=Test;
     end  
 end
 
 if T8<T10+5
    jaljkfsjklfs*jasdkljdsa
 end

 if T11<T10+4
     jsjsjsjs*jjsjs
 end

end
