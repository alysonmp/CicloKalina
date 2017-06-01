/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.Control.Ciclo2;

import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlBomba {
    private double S4, H4, S5, H5, T5;

    public ControlBomba(double Beff, double  P1, double  T1, double  Pconop, double  Tconop, double  Pref, double  Tref, int ii, Session session) {        
        ControlConstantes constantes = new ControlConstantes(Tconop, Pconop, ii, session);
        ControlZeta zeta = new ControlZeta(constantes.getBeta(), constantes.getEps(), constantes.getDelta());
        ControlH_Sistema hSistema = new ControlH_Sistema(Tconop, Pconop, Pref, Tref, ii, session);
        ControlS_Sistema sSistema = new ControlS_Sistema(Tconop, Pconop, Pref, Tref, ii, session);
        
        double P4 = Pconop;
        double P5 = P1;
        
        S4 = sSistema.getSL();
        H4= hSistema.getHL();
        double v4 = (zeta.getZl()*constantes.getR()*Tconop)/Pconop;
        H5 = (v4*(P5-P4)/Beff)+H4;
        
        double Test = Tconop ;
        double erro=1;
        double DT=10;
        double H,Burbuja;
        
        while (erro>0.0005){
            hSistema = new ControlH_Sistema(Test, P1, Pref, Tref, ii, session);
            H = hSistema.getHL();
            erro = Math.abs((H5-H)/H5);
            Burbuja = H5-H;
            if(erro>0.0005 && Burbuja<0){
                Test=Test-DT;
                DT=DT/2;
                if (DT<0.0005)
                    DT=0.0005456321;
            }else if(erro>0.0005 && Burbuja>0){
                    Test=Test+DT;
                    DT=DT/2;
                    if(DT<0.0005)
                        DT=0.0003975313;
                    
            }
        }
        
        T5=Test;
        sSistema = new ControlS_Sistema(T5, P1, Pref, Tref, ii,session); 
        S5= sSistema.getSL();
        
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

    public double getS5() {
        return S5;
    }

    public void setS5(double S5) {
        this.S5 = S5;
    }

    public double getH5() {
        return H5;
    }

    public void setH5(double H5) {
        this.H5 = H5;
    }

    public double getT5() {
        return T5;
    }

    public void setT5(double T5) {
        this.T5 = T5;
    }
    
    
}
