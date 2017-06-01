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
public class ControlSeparadorRankine {
    
    double x, yi, H3, H2, H1, T3, T2;
    private Session session;
    
    public ControlSeparadorRankine(double P1, double T1, double zi, double Pref, double Tref, Session session){
        this.session = session;
        
        T2 = T1;
        T3 = T1;
        
        ControlCompequi compequi = new ControlCompequi(P1, T1, session);
        
        ControlH_Sistemamix h_sistemamix = new ControlH_Sistemamix(T3, P1, Pref, Tref, compequi.getX(), session);
        H3 = h_sistemamix.getHL();
        ControlH_Sistemamix h_sistemamix2 = new ControlH_Sistemamix(T2, P1, Pref, Tref, compequi.getYi(), session);
        H2 = h_sistemamix2.getHV();
        
        ControlFlash flash = new ControlFlash(P1, T1, zi, session);
        
        H1 = (flash.getVF()*H2)+((1-flash.getVF())*H3);
    }   
}