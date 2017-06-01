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
public class ControlValvula {
    
    private double P6, P5, x6, x5, T6, Tbol6, yi, Tbol5, HL6, HV6, H6, H5, Test, DT, erro, it, HL5, 
                   HV5, H, burbuja, xi5, HL, xx5, yi5, HV, T5;
    
    public ControlValvula(double SUBT, double P1, double x3, double Pcon, double Pref, double Tref, Session session){
        P6 = Pcon;
        P5 = P1;
        x6 = x3;
        x5 = x3;
        
        ControlTBolha tBolha = new ControlTBolha(P6, x6, session);
        Tbol6 = tBolha.getTbol();
        yi = tBolha.getYi();
                
        tBolha = new ControlTBolha(P5, x6, session);
        Tbol5 = tBolha.getTbol();
        yi = tBolha.getYi();
        
        T6 = Tbol6-SUBT;
        
        ControlH_Sistemamix HSistemamix = new ControlH_Sistemamix(T6, P6, Pref, Tref, x6, session);
        HL6 = HSistemamix.getHL();
        HV6 = HSistemamix.getHV();
        
        H6 = HL6;
        H5 = H6;
        Test = T6;
        DT = 1;
        erro = 1;
        it = 0;
        
        while(erro > 0.001){
            it = it+1;
            if(it > 1000){
                //kkkkk=kkkkk*PPPPPP
                //%break
            }
            if(Test <= Tbol5){
                HSistemamix = new ControlH_Sistemamix(Test, P5, Pref, Tref, x5, session);
                HL5 = HSistemamix.getHL();
                HV5 = HSistemamix.getHV();
                H = HL5;
                erro = Math.abs((H5-H)/H5);
                burbuja=H5-H;
                if(erro > 0.001 && burbuja < 0){
                    Test = Test-DT;
                    DT = DT/2;
                    if(DT < 0.00025){
                        DT = 0.000135397;
                    }
                }else{
                    if(erro > 0.001 && burbuja > 0){
                        Test = Test+DT;
                        DT = DT/2;
                        if(DT < 0.00025){
                            DT = 0.00011311139;
                        }
                    }
                }
                xx5 = 0;
            }else{
                ControlCompequi compequi = new ControlCompequi(P5, Test, session);
                xi5 = compequi.getX();
                yi5 = compequi.getYi();
            
                ControlFlash flash = new ControlFlash(P5, Test, x5, session);
                xx5 = flash.getVF();
                
                HSistemamix = new ControlH_Sistemamix(Test, P5, Pref, Tref, yi5, session);
                HL = HSistemamix.getHL();
                HV5 = HSistemamix.getHV();
                
                HSistemamix = new ControlH_Sistemamix(Test, P5, Pref, Tref, yi5, session);
                HL5 = HSistemamix.getHL();
                HV = HSistemamix.getHV();
                
                H = (HV5*xx5)+(HL6*(1-xx5));
                erro = Math.abs((H5-H)/H5);
                burbuja = H5-H;
                if(erro > 0.001 && burbuja < 0){
                    Test = Test-DT;
                    DT = DT/2;
                    if(DT < 0.00025){
                        DT = 0.000135397;
                    }
                }else{
                    if(erro > 0.001 && burbuja > 0){
                        Test = Test+DT;
                        DT = DT/2;
                        if(DT < 0.00025){
                            DT = 0.000117311139;
                        }
                    }
                }
            }
            T5 = Test;
        }
    }

    public double getP6() {
        return P6;
    }

    public void setP6(double P6) {
        this.P6 = P6;
    }

    public double getX6() {
        return x6;
    }

    public void setX6(double x6) {
        this.x6 = x6;
    }

    public double getT6() {
        return T6;
    }

    public void setT6(double T6) {
        this.T6 = T6;
    }

    public double getH6() {
        return H6;
    }

    public void setH6(double H6) {
        this.H6 = H6;
    }

    public double getH5() {
        return H5;
    }

    public void setH5(double H5) {
        this.H5 = H5;
    }

    public double getXx5() {
        return xx5;
    }

    public void setXx5(double xx5) {
        this.xx5 = xx5;
    }

    public double getT5() {
        return T5;
    }

    public void setT5(double T5) {
        this.T5 = T5;
    }
}