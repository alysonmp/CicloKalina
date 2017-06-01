/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlEvaporador {
    
    private double m, QTf, Tfout, Qsen, Qlat;

    public ControlEvaporador(double TbolA, double T12, double H12, double Tf, double P1, double zi, double Pref, double Tref, double H1, double PP, int compressor, Session session) {
        
        double Tf2 = TbolA + PP;
        
        ControlH_Sistemamix sistemamix = new ControlH_Sistemamix(TbolA, P1, Pref, Tref, zi, session);
        ControlCalor calor = new ControlCalor(compressor, Tf, Tf2, session);
        ControlTSaida tsaida = new ControlTSaida(compressor, Tf, QTf, session);
        
        double EntEVP = H1 - sistemamix.getHL();
        
        if(Tf2 > Tf){
            JOptionPane.showMessageDialog(null,"Wrong number of input arguments","Error",-1);
        }
        
        double m = calor.getQfon1()/EntEVP;
        QTf = m*(H1-H12);
        
        //if vazio
        //if(Tfout < T12+5)
        
        Qsen = m*(sistemamix.getHL()-H12);
        Qlat = m*(H1 -sistemamix.getHL());
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getQTf() {
        return QTf;
    }

    public void setQTf(double QTf) {
        this.QTf = QTf;
    }

    public double getTfout() {
        return Tfout;
    }

    public void setTfout(double Tfout) {
        this.Tfout = Tfout;
    }

    public double getQsen() {
        return Qsen;
    }

    public void setQsen(double Qsen) {
        this.Qsen = Qsen;
    }

    public double getQlat() {
        return Qlat;
    }

    public void setQlat(double Qlat) {
        this.Qlat = Qlat;
    }
    
    
}
