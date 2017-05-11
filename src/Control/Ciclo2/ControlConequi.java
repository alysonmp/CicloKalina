/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

/**
 *
 * @author alysonmp
 */
public class ControlConequi {
    
    private double ki, kj;
    private ControlFugmix fugmix;
    
    public ControlConequi(){
    }
    
    public void realizaCalculo(double T, double P, double zi){
        fugmix = new ControlFugmix(T, P, zi);
        
        ki = fugmix.getPhil_i()/fugmix.getPhiv_i();
        kj = fugmix.getPhil_j()/fugmix.getPhiv_j();
    }
    
    public double getKi() {
        return ki;
    }

    public void setKi(double ki) {
        this.ki = ki;
    }

    public double getKj() {
        return kj;
    }

    public void setKj(double kj) {
        this.kj = kj;
    }

    public ControlFugmix getFugmix() {
        return fugmix;
    }

    public void setFugmix(ControlFugmix fugmix) {
        this.fugmix = fugmix;
    }
    
    
}