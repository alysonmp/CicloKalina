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
public class ControlCompequi {
    
    private double zi, Dzi, zj, teste, x, yi;
    private double erro;
    private ControlConequi conequi;
    
    public ControlCompequi(double P, double T, Session session){
       
        zi = 0.5;
        erro = 1;
        Dzi = 0.25;
        
        conequi = new ControlConequi(session);
        while(erro > 0.0001){
            zj = 1-zi;
            conequi.realizaCalculo(T, P, zi);
            
            teste = (conequi.getKi()*zi)+(conequi.getKj()*zj);
            erro = Math.abs((1-teste)/1);
            if(erro > 0.0001 & teste < 1){
                zi = zi+Dzi;
                Dzi = Dzi/2;
            }else{ 
                if(erro > 0.0001 & teste > 1){
                    zi = zi-Dzi;
                    Dzi = Dzi/2;
                }
            }
            
            if(zj < 0.000001){
                JOptionPane.showMessageDialog(null, "Liquido subenfriado");
            }
            
            if(zi<0.000001){
                JOptionPane.showMessageDialog(null, "Vapor superaquecido");
            }
        }
        
        x = zi;
        yi = conequi.getKi()*zi;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public ControlConequi getConequi() {
        return conequi;
    }

    public void setConequi(ControlConequi conequi) {
        this.conequi = conequi;
    }

    public double getYi() {
        return yi;
    }

    public void setYi(double yi) {
        this.yi = yi;
    }
}