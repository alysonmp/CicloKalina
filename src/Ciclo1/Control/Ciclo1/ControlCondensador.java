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
public class ControlCondensador {
    
    private double P9, T9, H9;
    
    public ControlCondensador(double TbolB, double T8, double P8, double H8, double Pref, double Tref, double zi, Session session){
        P9 = P8;
        T9 = TbolB;
        ControlH_Sistemamix HSistemamix = new ControlH_Sistemamix(T9, P9, Pref, Tref, zi, session);
        H9 = HSistemamix.getHL();
    }
}