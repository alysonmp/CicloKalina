/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ControlRegeneradorLT {

    private double P7, P8, P11, P10, P12, it, Tbol11, Torv11, T11, xx11, H11, xi11, yi11, HL, HV11, 
                   HV, HL11, H8, erro1, Tbol8, Torv8, Test, DT, H, burbuja, T8, xi8, yi8, HV8, HL8, xx8;
    
    public ControlRegeneradorLT(double TbolA, double TorvA, double TbolB, double TorvB, double P1, double H7, double H10, double T7, double T10, double Pcon, double Pref, double Tref, double zi, double effLT, Session session){
        
        P7 = Pcon;
        P8 = Pcon;
        P11 = P1;
        P10 = P1;
        P12 = P1;
        it = 0;
        Tbol11 = TbolA;
        Torv11 = TorvA;
        T11 = T10+(effLT*(T7-T10));
        
        if(T11+5 > T7){
            //jaljkfsjklfs*jasdkljdsa
        }
        
        if(T11 <= Tbol11){
            xx11=0;
            ControlH_Sistemamix HSistemamix = new ControlH_Sistemamix(T11, P11, Pref, Tref, zi, session);
            H11 = HSistemamix.getHL();
        }else{
            if(T11 >= Torv11){
                xx11=1;
                ControlH_Sistemamix HSistemamix = new ControlH_Sistemamix(T11, P11, Pref, Tref, zi, session);
                H11 = HSistemamix.getHL();
            }else{
                ControlCompequi compequi = new ControlCompequi(P11, T11, session);
                xi11 = compequi.getX();
                yi11 = compequi.getYi();
                
                ControlH_Sistemamix HSistemamix = new ControlH_Sistemamix(T11, P11, Pref, Tref, yi11, session);
                HL = HSistemamix.getHL();
                HV11 = HSistemamix.getHV();
                
                HSistemamix = new ControlH_Sistemamix(T11, P11, Pref, Tref, xi11, session);
                HL11 = HSistemamix.getHL();
                HV = HSistemamix.getHV();
                
                ControlFlash flash = new ControlFlash(P11, T11, zi, session);
                xx11 = flash.getVF();
                H11=(HV11*xx11)+(HL11*(1-xx11));
            }
        }
        H8 = -(H11-H10)+H7;
        
        erro1 = 1;
        Tbol8 = TbolB;
        Torv8 = TorvB;
        Test = T7;
        DT = Math.abs((Tbol8-T7)/3);
        it = 0;
        
        while(erro1 >= 0.001){
            it = it+1;
            if(it > 1000){
                //hshshsh*ksksks
            }
            if(Test >= Torv8){
                ControlH_Sistemamix HSistemamix = new ControlH_Sistemamix(Test, P8, Pref, Tref, zi, session);
                H = HSistemamix.getHL();
                erro1 = Math.abs((H8-H)/H8);
                burbuja = H8-H;
                if (erro1 > 0.001 && burbuja < 0){
                    Test = Test-DT;
                    DT = DT/1.1;
                    if(DT < 0.005){
                        DT = 3;
                    }else{
                        if(erro1 > 0.001 && burbuja > 0){
                            Test = Test+DT;
                            DT = DT/1.1;
                            if(DT < 0.005){
                                DT = 0.00333695;
                            }
                        }
                        T8 = Test;
                    }
                }else{
                    if(Test <= Tbol8){
                        HSistemamix = new ControlH_Sistemamix(Test, P8, Pref, Tref, zi, session);
                        H = HSistemamix.getHL();
                        erro1 = Math.abs((H8-H)/H8);
                        burbuja = H8-H;
                        if(erro1 > 0.001 && burbuja < 0){
                            Test = Test-DT;
                            DT = DT/1.1;
                            if(DT < 0.005){
                                DT = 3;
                            }
                        }else{
                            if(erro1 > 0.001 && burbuja > 0){
                                Test = Test+DT;
                                DT = DT/1.1;
                                if(DT < 0.005){
                                    DT = 0.00333695;
                                }
                            }
                        }
                        T8 = Test;
                    }
                    else{
                        ControlCompequi compequi = new ControlCompequi(P8, Test, session);
                        xi8 = compequi.getX();
                        yi8 = compequi.getYi();
                        
                        HSistemamix = new ControlH_Sistemamix(Test, P8, Pref, Tref, yi8, session);
                        HL = HSistemamix.getHL();
                        HV8 = HSistemamix.getHV();
                        
                        HSistemamix = new ControlH_Sistemamix(Test, P8, Pref, Tref, xi8, session);
                        HL8 = HSistemamix.getHL();
                        HV = HSistemamix.getHV();
                        
                        ControlFlash flash = new ControlFlash(P8, Test, zi, session);
                        xx8 = flash.getVF();
                        
                        H = (HV8*xx8)+(HL8*(1-xx8));
                        erro1 = Math.abs((H8-H)/H8);
                        burbuja=H8-H;
                        if(erro1 > 0.001 && burbuja < 0){
                           Test = Test-DT;
                           DT = DT/1.1;
                           if(DT < 0.005){
                               DT = 3;
                           }
                        }else{
                            if(erro1 > 0.001 && burbuja > 0){
                               Test = Test+DT;
                               DT = DT/1.1;
                               if(DT < 0.005){
                                   DT = 0.00333695;
                               }
                            }
                            T8 = Test;
                        }
                    }
                }
            }
        }
        
        if(T8 < T10+5){
            //jaljkfsjklfs*jasdkljdsa
        }

        if(T11 < T10+4){
             //jsjsjsjs*jjsjs
        }
    }

    public double getP7() {
        return P7;
    }

    public void setP7(double P7) {
        this.P7 = P7;
    }

    public double getP8() {
        return P8;
    }

    public void setP8(double P8) {
        this.P8 = P8;
    }

    public double getP11() {
        return P11;
    }

    public void setP11(double P11) {
        this.P11 = P11;
    }

    public double getT11() {
        return T11;
    }

    public void setT11(double T11) {
        this.T11 = T11;
    }

    public double getXx11() {
        return xx11;
    }

    public void setXx11(double xx11) {
        this.xx11 = xx11;
    }

    public double getH11() {
        return H11;
    }

    public void setH11(double H11) {
        this.H11 = H11;
    }

    public double getH8() {
        return H8;
    }

    public void setH8(double H8) {
        this.H8 = H8;
    }

    public double getT8() {
        return T8;
    }

    public void setT8(double T8) {
        this.T8 = T8;
    }

    public double getXi8() {
        return xi8;
    }

    public void setXi8(double xi8) {
        this.xi8 = xi8;
    }

    public double getYi8() {
        return yi8;
    }

    public void setYi8(double yi8) {
        this.yi8 = yi8;
    }

    public double getXx8() {
        return xx8;
    }

    public void setXx8(double xx8) {
        this.xx8 = xx8;
    }
}