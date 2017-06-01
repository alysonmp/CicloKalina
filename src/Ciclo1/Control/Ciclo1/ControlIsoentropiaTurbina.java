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
public class ControlIsoentropiaTurbina {
    
    private double erro, Test, DT, it, S4, burbuja, S4s, SLs, SV4s, SL4s, SVs, Test1, T4s, HL4, HV4, H4s,
            xx4, H4, xi4s, yi4s, HL, HV, xx4s, erro1, H, T4, SL4, SV4, xi4, yi4, SL, SV;
    
    public ControlIsoentropiaTurbina(double Teff, double P4, double Pref, double Tref, double S2, double H2, double yi, double T2, Session session){
        
        ControlTorvalho torvalho = new ControlTorvalho(P4, yi, session);
        ControlTBolha bolha = new ControlTBolha(P4, yi, session);
        
        erro = 1;
        Test = T2;
        DT = Math.abs((T2-(Tref+40))/2);
        it = 0;
        while(erro>= 0.001){
            it += 1;
            if(it > 1000){
               // eheheh*kekeke
            }
            if(Test >= torvalho.getTorv()){
                //[SL4, SV4] = S_sistemamix(Test, P4, Pref, Tref, yi);
                ControlS_SistemaMix SsistemaMix = new ControlS_SistemaMix(Test, P4, Pref, Tref, yi, session);
                S4 = SsistemaMix.getSV();
                erro = Math.abs((S4-S2)/S4);
                burbuja = S4-S2;
                if(erro > 0.001 && burbuja < 0){
                    Test=Test+DT;
                    DT=DT/1.1;
                    if(DT<0.005){
                        DT=3;
                    }
                }else{
                    if(erro > 0.001 && burbuja > 0){
                        Test = Test-DT;
                        DT=DT/1.1;
                        if(DT < 0.005){
                            DT = 0.002933254;
                        }
                    }
                }
            }else{
                if(Test <= bolha.getTbol()){
                    ControlS_SistemaMix SsistemaMix = new ControlS_SistemaMix(Test, P4, Pref, Tref, yi, session);
                    S4 = SsistemaMix.getSL();
                    erro = Math.abs((S4-S2)/S4);
                    burbuja=S4-S2;
                    if(erro > 0.001 && burbuja < 0){
                        Test = Test+DT;
                        DT = DT/1.1;
                        if(DT < 0.005){
                            DT = 3;
                        }
                    }else{
                        if(erro > 0.001 && burbuja > 0){
                            Test = Test-DT;
                            DT = DT/1.1;
                            if(DT < 0.005){
                                DT = 0.002933254;
                            }
                        }
                    }
                }else{
                    ControlCompequi compequi = new ControlCompequi(P4, Test, session);
                    ControlS_SistemaMix sistemaMix = new ControlS_SistemaMix(Test, P4, Pref, Tref, compequi.getYi(), session);
                    SLs = sistemaMix.getSL();
                    SV4s = sistemaMix.getSV();
                    
                    ControlS_SistemaMix sistemaMix2 = new ControlS_SistemaMix(Test, P4, Pref, Tref, compequi.getX(), session);       
                    SL4s = sistemaMix2.getSL();
                    SVs = sistemaMix2.getSV();
                            
                    ControlFlash flash = new ControlFlash(P4, Test, yi, session);
                    
                    //S4s=(SV4s*xx4s)+(SL4s*(1-xx4s))
                    S4s = (SV4s*flash.getVF())+(SL4s*(1-flash.getVF()));
                    
                    erro = Math.abs((S4s-S2)/S4s);
                    burbuja = S4s-S2;
                    
                    if(erro > 0.001 && burbuja < 0){
                        Test = Test+DT;
                        DT = DT/1.1;
                        if(DT < 0.005){
                            DT=3;
                        }
                    }else{
                        if(erro > 0.001 && burbuja > 0){
                            Test = Test-DT;
                            DT = DT/1.1;
                            if(DT < 0.005){
                                DT=0.002933254;
                            }
                        }
                    }
                }
            }
        }
        
        Test1 = Test;
        it = 0;
        if(Test >= torvalho.getTorv()){
            T4s = Test1;
            ControlH_Sistemamix HsistemaMix = new ControlH_Sistemamix(T4s, P4, Pref, Tref, yi, session);
            HV4 = HsistemaMix.getHV();
            HL4 = HsistemaMix.getHL();
            H4s = HV4;
            
            xx4 = 1;
            H4 = (-(H2-H4s)*Teff)+H2;
        }else{
            if(Test <= bolha.getTbol()){
                xx4 = 0;
                T4s = Test1;
                ControlH_Sistemamix HsistemaMix = new ControlH_Sistemamix(T4s, P4, Pref, Tref, yi, session);
                HV4 = HsistemaMix.getHV();
                HL4 = HsistemaMix.getHL();
                H4s = HL4;
                H4 = (-(H2-H4s)*Teff)+H2;
            }else{
                T4s = Test1;
                ControlCompequi compequi = new ControlCompequi(P4, T4s, session);
                xi4s = compequi.getX();
                yi4s = compequi.getYi();
                
                ControlH_Sistemamix HsistemaMix = new ControlH_Sistemamix(T4s, P4, Pref, Tref, yi4s, session);
                HL = HsistemaMix.getHL();
                HV4 = HsistemaMix.getHV();
                
                ControlH_Sistemamix HsistemaMix2 = new ControlH_Sistemamix(T4s, P4, Pref, Tref, xi4s, session);
                HL4 = HsistemaMix.getHL();
                HV = HsistemaMix.getHV();
                
                ControlFlash flash = new ControlFlash(P4, Test, yi, session);
                xx4s = flash.getVF();
                H4s = (HV4*xx4s)+(HL4*(1-xx4s));
                H4 = (-(H2-H4s)*Teff)+H2;
            }
        }
        
        erro1 = 1;
        DT = 8;
        
        while(erro1 >= 0.001){
            it = it+1;
            if(it > 1000){
                //eheheh*kekeke
            }
            if(Test >= torvalho.getTorv()){
                ControlH_Sistemamix HsistemaMix = new ControlH_Sistemamix(Test, P4, Pref, Tref, yi, session);
                HL4 = HsistemaMix.getHL();
                HV4 = HsistemaMix.getHV();
                
                H = HV4;
                erro1 = Math.abs((H4-H)/H4);
                burbuja=H4-H;
                if(erro1 > 0.001 && burbuja < 0){
                    Test=Test-DT;
                    DT=DT/1.1;
                    if(DT < 0.005){
                        DT=0.0043978953;
                    }
                }else{
                    if(erro1 > 0.001 && burbuja > 0){
                        Test = Test+DT;
                        DT=DT/1.1;
                        if(DT < 0.005){
                            DT=0.00333695;
                        }
                    }
                }
                T4 = Test;
                ControlS_SistemaMix SsistemaMix = new ControlS_SistemaMix(T4, P4, Pref, Tref, yi, session);
                SL4 = SsistemaMix.getSL();
                SV4 = SsistemaMix.getSV();

                S4=SV4;
                
            }else{
                if(Test <= bolha.getTbol()){
                    ControlS_SistemaMix SsistemaMix = new ControlS_SistemaMix(Test, P4, Pref, Tref, yi, session);
                    H = SsistemaMix.getSL();
                    erro1 = Math.abs((H4-H)/H4);
                    burbuja = H4-H;
                    if(erro1 > 0.001 && burbuja < 0){
                        Test = Test-DT;
                        DT = DT/1.1;
                        if(DT < 0.005){
                            DT=0.0043978953;
                        }
                    }else{
                        if(erro1 > 0.001 && burbuja > 0){
                            Test = Test+DT;
                            DT = DT/1.1;
                            if(DT < 0.005){
                                DT=0.00333695;
                            }
                        }
                    }
                    
                    T4=Test;
                    SsistemaMix = new ControlS_SistemaMix(T4, P4, Pref, Tref, yi, session);
                    S4 = SsistemaMix.getSL();
                }else{
                    ControlCompequi compequi = new ControlCompequi(P4, Test, session);
                    xi4 = compequi.getX();
                    yi4 = compequi.getYi();
                    
                    ControlH_Sistemamix HSistemaMix = new ControlH_Sistemamix(Test, P4, Pref, Tref, yi4, session);
                    HL = HSistemaMix.getHL();
                    HV4 = HSistemaMix.getHV();
                    
                    ControlH_Sistemamix HSistemaMix2 = new ControlH_Sistemamix(Test, P4, Pref, Tref, xi4, session);
                    HL4 = HSistemaMix.getHL();
                    HV = HSistemaMix.getHV();
                    
                    ControlFlash flash = new ControlFlash(P4, Test, yi, session);
                    xx4 = flash.getVF();
                    
                    H = (HV4*xx4)+(HL4*(1-xx4));
                    erro1 = Math.abs((H4-H)/H4);
                    burbuja = H4-H;
                    
                    if(erro1 > 0.001 && burbuja < 0){
                        Test = Test-DT;
                        DT = DT/1.1;
                        if(DT<0.005){
                            DT = 0.0043978953;
                        }
                    }else{
                        if(erro1 > 0.001 && burbuja > 0){
                            Test = Test+DT;
                            DT = DT/1.1;
                            if(DT<0.005){
                                DT=0.00333695;
                            }
                        }
                        
                        T4=Test;
                        ControlS_SistemaMix SSistemaMix = new ControlS_SistemaMix(T4, P4, Pref, Tref, yi4, session);
                        SL = SSistemaMix.getSL();
                        SV4 = SSistemaMix.getSV();

                        ControlS_SistemaMix SSistemaMix2 = new ControlS_SistemaMix(T4, P4, Pref, Tref, xi4, session);
                        SL4 = SSistemaMix2.getSL();
                        SV = SSistemaMix2.getSV();

                        S4=(SV4*xx4)+(SL4*(1-xx4));
                    }
                }
            }
        }
    } 

    public double getH4s() {
        return H4s;
    }

    public void setH4s(double H4s) {
        this.H4s = H4s;
    }

    public double getT4() {
        return T4;
    }

    public void setT4(double T4) {
        this.T4 = T4;
    }

    public double getS4() {
        return S4;
    }

    public void setS4(double S4) {
        this.S4 = S4;
    }

    public double getH4() {
        return H4;
    }

    public void setH4(double H4) {
        this.H4 = H4;
    }

    public double getXx4() {
        return xx4;
    }

    public void setXx4(double xx4) {
        this.xx4 = xx4;
    }

    public double getXi4() {
        return xi4;
    }

    public void setXi4(double xi4) {
        this.xi4 = xi4;
    }

    public double getYi4() {
        return yi4;
    }

    public void setYi4(double yi4) {
        this.yi4 = yi4;
    }
    
}