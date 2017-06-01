/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlEconomizador {

    private double T12, H12, QTf, Tfout;
    private Session session;
    
    public ControlEconomizador(double Tf, double H1, double P1, double m, double DDT12, double Tbol12,double zi,double Pref,double Tref,int compressor,Session session) {
        this.session = session;
        
        T12 = Tbol12 - DDT12;
        
        ControlH_Sistemamix sistemamix= new ControlH_Sistemamix(T12,P1,Pref,Tref,zi,this.session);
        H12 = sistemamix.getHL();
        
        QTf = H1 - H12;   
        ControlTSaida tsaida = new ControlTSaida(compressor,Tf,QTf,this.session);
        Tfout = tsaida.getTfout();
        
        int it = 0;
        double PINCH = 0.0;
        while(T12 + 5 > Tfout){
                PINCH += 0.1;
                it++;
                if(it > 10000)
                    break;
                //T1s = T1 - SUP;
                //Tf2 = T1s + PINCH;
        }
        
    }
    
    
}
