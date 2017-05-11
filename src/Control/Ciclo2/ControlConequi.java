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
    
    public ControlConequi(double ki, double kj){
        
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
    
}


function [Ki, Kj]=conequi(T, P, zi);

[phil_i, phiv_i, phil_j, phiv_j] = fugmix(T, P, zi);

Ki=phil_i/phiv_i;
Kj=phil_j/phiv_j;

end