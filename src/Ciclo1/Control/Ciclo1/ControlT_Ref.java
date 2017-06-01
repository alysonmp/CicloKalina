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
 * @author alysonmp
 */
public class ControlT_Ref {
    
    double P, Te, erro, DT, burbuja, Tref;
    private ControlPdeVapor pdevapor;
    
    public ControlT_Ref(double P, double x, Session session){
        this.P = P;
        
        if(x == 0){
            Te = 647.1-206; //%K
            erro = 1;
            DT = 80;
            while(erro > 0.0001){
                pdevapor = new ControlPdeVapor(Te, x, session);
                burbuja = pdevapor.getPsj()-P;
                erro = Math.abs((pdevapor.getPsj()-P)/pdevapor.getPsj());
                if(erro > 0.0001 && burbuja > 0){
                    Te = Te-DT;
                    DT = DT/2;
                    if(DT < 0.005){
                        DT = 0.0047953543;
                    }
                }else{
                    if(erro > 0.0001 && burbuja < 0){
                        Te = Te+DT;
                        DT = DT/2;
                        if(DT<0.005){
                            DT=0.00393845211111;
                        }
                    }
                }
            }
        }else{
            Te = 405.4-100;
            erro = 1;
            DT = 80;
            while(erro > 0.0001){
                pdevapor = new ControlPdeVapor(Te, x, session);
                burbuja = pdevapor.getPsi() - P;
                erro = Math.abs((pdevapor.getPsi()-P)/pdevapor.getPsi());
                if(erro > 0.0001 && burbuja > 0){
                    Te = Te-DT;
                    DT = DT/2;
                    if(DT<0.005){
                        DT=0.0047953543;
                    }
                }else{
                    if(erro > 0.0001 && burbuja < 0){
                        Te=Te+DT;
                        DT=DT/2;
                        if(DT<0.005){
                            DT=0.00393845211111;
                        }
                    }
                }
            }
        }
        Tref = Te;
    }

    public double getTref() {
        return Tref;
    }

    public void setTref(double Tref) {
        this.Tref = Tref;
    }
}