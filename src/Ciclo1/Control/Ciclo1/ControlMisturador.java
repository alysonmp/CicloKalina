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
public class ControlMisturador {
 
    private double P7, x7, H7, T7,xx7, xi7, yi7;
    
    public ControlMisturador(double Tbol, double Torv, double T6, double zi, double T4, double Pcon, double VF, double H4, double H6, double Pref, double Tref, Session session){
        P7 = Pcon;
        x7 = zi;
        
        H7 = (VF*H4)+(H6*(1-VF));
        T7 = (((T6-T4)/(H6-H4))*H7)+(T4-((T6-T4)/(H6-H4))*H4);
        
        if(T7 <= Tbol){
            xx7=0;
        }else{
            if(T7 >= Torv){
                xx7=1;
            }else{
                ControlCompequi compequi = new ControlCompequi(P7, T7, session);
                xi7 = compequi.getX();
                yi7 = compequi.getYi();
                
                ControlFlash flash = new ControlFlash(P7, T7, x7, session);
                xx7 = flash.getVF();
            }
        }
    }   

    public double getH7() {
        return H7;
    }

    public void setH7(double H7) {
        this.H7 = H7;
    }

    public double getT7() {
        return T7;
    }

    public void setT7(double T7) {
        this.T7 = T7;
    }

    public double getXx7() {
        return xx7;
    }

    public void setXx7(double xx7) {
        this.xx7 = xx7;
    }

    public double getXi7() {
        return xi7;
    }

    public void setXi7(double xi7) {
        this.xi7 = xi7;
    }

    public double getYi7() {
        return yi7;
    }

    public void setYi7(double yi7) {
        this.yi7 = yi7;
    }
}
