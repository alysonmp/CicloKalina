/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

import javax.swing.JOptionPane;

/**
 *
 * @author alysonmp
 */
public class ControlCompequi {
    
    private double zi, Dzi, zj, teste, x, yi;
    private double erro;
    
    public ControlCompequi(double P, double T){
       
        zi = 0.5;
        erro = 1;
        Dzi = 0.25;
        
        ControlConequi conequi = new ControlConequi();
        while(erro > 0.0001){
            zj = 1-zi;
            conequi.realizaCalculo(T, P, zi);
            
            teste = conequi.get(ki*zi)+(kj*zj);
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
}